package com.basejava.webapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.basejava.webapp.exception.StorageException;

public record SqlHelper(ConnectionFactory connection) {

    public <R> R execute(String sql, SqlFunction<PreparedStatement, R> action) {
        try (Connection conn = connection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            return action.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}