package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void sqlExecute(String sql) {
        sqlExecute(sql, PreparedStatement::execute);
    }

    public <T> T sqlExecute(String sql, SqlExecutable<T> executor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            return executor.executor(ps);
        } catch (SQLException e) {
            throw new ExistStorageException("Resume exists in DB!");
        }
    }
}
