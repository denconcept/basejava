package com.basejava.webapp.util;

import java.sql.SQLException;

@FunctionalInterface
public interface SqlFunction<T, R> {

    R accept(T t) throws SQLException;
}
