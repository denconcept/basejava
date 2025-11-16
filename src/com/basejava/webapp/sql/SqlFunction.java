package com.basejava.webapp.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface SqlFunction<T, R> {

    R execute(T t) throws SQLException;
}
