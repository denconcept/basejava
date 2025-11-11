package com.basejava.webapp.storage;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.ConnectionFactory;
import com.basejava.webapp.util.SqlHelper;

public class SqlStorage implements Storage {

    public final ConnectionFactory connection;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connection = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public List<Resume> getAllSorted() {
        return SqlHelper.get(connection, "SELECT * FROM resume ORDER BY full_name;",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    List<Resume> resumes = new ArrayList<>();
                    while (rs.next()) {
                        Resume resume = new Resume(
                                rs.getString("uuid"),
                                rs.getString("full_name")
                        );
                        resumes.add(resume);
                    }
                    return resumes;
                });
    }

    @Override
    public Resume get(String uuid) {
        return SqlHelper.get(connection, "SELECT * FROM resume r WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

    @Override
    public int size() {
        return SqlHelper.get(connection, "SELECT COUNT(*) FROM resume",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                    return 0;
                });
    }

    @Override
    public void save(Resume resume) {
        int result = SqlHelper.get(connection,
                "INSERT INTO resume (uuid, full_name) VALUES (?, ?) ON CONFLICT (uuid) DO NOTHING",
                ps -> {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, resume.getFullName());
                    return ps.executeUpdate();
                });
        if (result == 0) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        int result = SqlHelper.get(connection, "DELETE FROM resume r WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    return ps.executeUpdate();
                });
        if (result == 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume resume) {
        int result = SqlHelper.get(connection, "UPDATE resume SET full_name = ? WHERE uuid = ?",
                ps -> {
                    ps.setString(1, resume.getFullName());
                    ps.setString(2, resume.getUuid());
                    return ps.executeUpdate();
                });
        if (result == 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void clear() {
        SqlHelper.get(connection, "DELETE FROM resume",
                ps -> {
                    ps.execute();
                    return null;
                });
    }
}
