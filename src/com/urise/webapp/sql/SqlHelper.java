package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

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
            if ("23505".equals(e.getSQLState())) {
                throw new ExistStorageException("Resume exists in DB!");
            } else {
                throw new StorageException(e);
            }
        }
    }

    public <T> T transactionExecute(SqlTransaction<T> execute) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T res = execute.execute(connection);
                connection.commit();
                return res;
            } catch (SQLException e) {
                if ("23505".equals(e.getSQLState())) {
                    connection.rollback();
                    throw new ExistStorageException("Resume exists in DB!");
                } else {
                    throw new StorageException(e);
                }
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

}
