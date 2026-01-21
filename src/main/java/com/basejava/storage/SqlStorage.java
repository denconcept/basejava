package com.basejava.storage;

import com.basejava.exception.NotExistStorageException;
import com.basejava.model.ContactType;
import com.basejava.model.ListSection;
import com.basejava.model.Resume;
import com.basejava.model.Section;
import com.basejava.model.SectionType;
import com.basejava.model.TextSection;
import com.basejava.sql.SqlHelper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    /* @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute(
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "  ORDER BY full_name, uuid",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> map = new LinkedHashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        Resume resume = map.get(uuid);
                        if (resume == null) {
                            resume = new Resume(uuid, rs.getString("full_name"));
                            map.put(uuid, resume);
                        }
                        addContacts(rs, resume);
                    }
                    return new ArrayList<>(map.values());
                });
    }*/
    
    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = sqlHelper.execute("SELECT * FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> newResumes = new ArrayList<>();
            while (rs.next()) {
                newResumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return newResumes;
        });
        sqlHelper.fillResumes("SELECT * FROM contact", resumes,
                (resume, type, value) -> resume.addContact(ContactType.valueOf(type), value));
        sqlHelper.fillResumes("SELECT * FROM section WHERE type IN ('OBJECTIVE', 'PERSONAL')", resumes,
                (resume, type, value) -> resume.addSection(SectionType.valueOf(type),
                        new TextSection(value)));
        sqlHelper.fillResumes(
                "SELECT * FROM section WHERE type IN ('ACHIEVEMENT', 'QUALIFICATIONS')", resumes,
                (resume, type, value) -> resume.addSection(SectionType.valueOf(type),
                        new ListSection(List.of(value.split("\n")))));
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }
    
    @Override
    public Resume get(String uuid) {
        Resume resume = sqlHelper.execute("SELECT * FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
        sqlHelper.fillResume("SELECT * FROM contact WHERE resume_uuid = ?", resume,
                (resumeForFill, type, value) -> resumeForFill.addContact(ContactType.valueOf(type), value));
        sqlHelper.fillResume(
                "SELECT * FROM section WHERE resume_uuid = ? AND type IN ('OBJECTIVE', 'PERSONAL')", resume,
                (resumeForFill, type, value) -> resumeForFill.addSection(SectionType.valueOf(type),
                        new TextSection(value)));
        sqlHelper.fillResume(
                "SELECT * FROM section WHERE resume_uuid = ? AND type IN ('ACHIEVEMENT', 'QUALIFICATIONS')",
                resume, (resumeForFill, type, value) -> resumeForFill.addSection(SectionType.valueOf(type),
                        new ListSection(List.of(value.split("\n")))));
        return resume;
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(connection, resume);
            insertTextSections(connection, resume);
            insertListSections(connection, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }
    
    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(connection, resume);
            insertContacts(connection, resume);
            deleteTextSections(connection, resume);
            insertTextSections(connection, resume);
            deleteListSections(connection, resume);
            insertListSections(connection, resume);
            return null;
        });
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }
    
    private void insertContacts(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().toString());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }
    
    private void deleteContacts(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }
    
    private void insertTextSections(Connection connection, Resume resume)
            throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                if (e.getKey() == SectionType.OBJECTIVE || e.getKey() == SectionType.PERSONAL) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().toString());
                    ps.setString(3, ((TextSection) e.getValue()).getDescription());
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        }
    }
    
    private void deleteTextSections(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM section WHERE resume_uuid = ? AND type IN ('OBJECTIVE', 'PERSONAL')")) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }
    
    private void insertListSections(Connection connection, Resume resume)
            throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                if (e.getKey() == SectionType.ACHIEVEMENT || e.getKey() == SectionType.QUALIFICATIONS) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().toString());
                    ps.setString(3, String.join("\n", ((ListSection) e.getValue()).getDescription()));
                    ps.addBatch();
                }
            }
            ps.executeBatch();
        }
    }
    
    private void deleteListSections(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM section WHERE resume_uuid = ? AND type IN ('ACHIEVEMENT', 'QUALIFICATIONS')")) {
            ps.setString(1, resume.getUuid());
            ps.executeUpdate();
        }
    }
}
