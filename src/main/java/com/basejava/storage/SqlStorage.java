package com.basejava.storage;

import com.basejava.exception.NotExistStorageException;
import com.basejava.model.AbstractSection;
import com.basejava.model.ContactType;
import com.basejava.model.Resume;
import com.basejava.model.SectionType;
import com.basejava.sql.SqlHelper;
import com.basejava.util.JsonParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    """
                            INSERT
                              INTO resume (uuid, full_name)
                            VALUES (?, ?)
                            """)) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(connection, resume);
            insertSections(connection, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume;
            try (PreparedStatement ps = connection.prepareStatement(
                    """
                            SELECT *
                              FROM resume
                             WHERE uuid = ?
                            """)) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    """
                            SELECT *
                              FROM contact
                             WHERE resume_uuid = ?
                            """)) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, resume);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    """
                            SELECT *
                              FROM section
                             WHERE resume_uuid = ?
                            """)) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = connection.prepareStatement(
                    """
                              SELECT *
                                FROM resume
                            ORDER BY full_name, uuid
                            """)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    """
                            SELECT *
                              FROM contact
                            """)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addContact(rs, resume);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    """
                            SELECT *
                              FROM section
                            """)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addSection(rs, resume);
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute(
                """
                        SELECT
                         COUNT(*)
                          FROM resume
                        """,
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    return rs.next() ? rs.getInt(1) : 0;
                });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    """
                            UPDATE resume
                               SET full_name = ?
                             WHERE uuid = ?
                            """)) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(connection, resume);
            insertContacts(connection, resume);
            deleteSections(connection, resume);
            insertSections(connection, resume);
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute(
                """
                        DELETE
                          FROM resume
                         WHERE uuid = ?
                        """,
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public void clear() {
        sqlHelper.execute(
                """
                        DELETE
                          FROM resume
                        """);
    }

    private void insertContacts(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                """
                        INSERT
                          INTO contact (resume_uuid, type, value)
                        VALUES (?,?,?)
                        """)) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().toString());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                """
                        INSERT
                          INTO section (resume_uuid, type, content)
                        VALUES (?,?,?)
                        """)) {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().toString());
                AbstractSection section = e.getValue();
                ps.setString(3, JsonParser.write(section, AbstractSection.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        String content = rs.getString("content");
        if (content != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            resume.addSection(type, JsonParser.read(content, AbstractSection.class));
        }
    }

    private void deleteContacts(Connection connection, Resume resume) throws SQLException {
        deleteAttributes(connection, resume,
                """
                        DELETE
                          FROM contact
                         WHERE resume_uuid = ?
                        """);
    }

    private void deleteSections(Connection connection, Resume resume) throws SQLException {
        deleteAttributes(connection, resume,
                """
                        DELETE
                          FROM section
                         WHERE resume_uuid = ?
                        """);
    }

    private void deleteAttributes(Connection connection, Resume resume, String sql) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }
}
