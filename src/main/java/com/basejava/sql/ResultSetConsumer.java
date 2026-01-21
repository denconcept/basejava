package com.basejava.sql;

import com.basejava.model.Resume;
import java.sql.SQLException;

public interface ResultSetConsumer {
    
    void accept(Resume resume, String type, String value) throws SQLException;
}
