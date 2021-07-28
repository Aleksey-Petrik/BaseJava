package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutable<T> {
    T executor(PreparedStatement ps) throws SQLException;
}
