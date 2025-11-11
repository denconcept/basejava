package com.basejava.webapp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.sql.ConnectionFactory;

public final class SqlHelper {

    private SqlHelper() {}

    public static <R> R get(ConnectionFactory cf, String sql,
                                SqlFunction<PreparedStatement, R> action) {
        try (Connection conn = cf.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            return action.accept(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}