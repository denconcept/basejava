package com.basejava.sql;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlHelper {
    
    private final ConnectionFactory connection;

    public SqlHelper(ConnectionFactory connection) {
        this.connection = connection;
    }

    public void execute(String sql) {
        execute(sql, PreparedStatement::execute);
    }

    public <T> T execute(String sql, SqlExecutor<T> executor) {
        try (Connection conn = connection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }
    
    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
    
    public void fillResumes(String sql, List<Resume> resumes, ResultSetConsumer rsc) {
        execute(sql, ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("resume_uuid");
                String type = rs.getString("type");
                String value = rs.getString("value");
                for (Resume resume : resumes) {
                    if (resume.getUuid().equals(uuid) && value != null) {
                        rsc.accept(resume, type, value);
                        break;
                    }
                }
            }
            return null;
        });
    }
    
    public void fillResume(String sql, Resume resume, ResultSetConsumer rsc) {
        execute(sql, ps -> {
            ps.setString(1, resume.getUuid());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String type = rs.getString("type");
                String value = rs.getString("value");
                rsc.accept(resume, type, value);
            }
            return null;
        });
    }
}
