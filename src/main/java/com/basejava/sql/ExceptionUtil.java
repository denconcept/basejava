package com.basejava.sql;

import java.sql.SQLException;

import org.postgresql.util.PSQLException;

import com.basejava.exception.ExistStorageException;
import com.basejava.exception.StorageException;

public class ExceptionUtil {

    private ExceptionUtil() {}

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            if (e.getSQLState().equals("23505")) {
                return new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}
