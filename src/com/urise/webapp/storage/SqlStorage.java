package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactsType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.sqlExecute("DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            try (PreparedStatement ps = connection.prepareStatement(""
                    + "INSERT INTO contact (type, value, resume_uuid)"
                    + "     VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactsType, String> contact : resume.getContacts().entrySet()) {
                    ps.setString(1, contact.getKey().name());
                    ps.setString(2, contact.getValue());
                    ps.setString(3, resume.getUuid());
                    ps.addBatch();//Добавление операций на исполнение
                }
                ps.executeBatch();//Выполнение всех накопленных команд
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.sqlExecute(""
                + "    SELECT * "
                + "      FROM resume r"
                + " LEFT JOIN contact c"
                + "        ON r.uuid = c.resume_uuid"
                + "     WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("");
            }
            Resume resume = new Resume(rs.getString("uuid"),
                    rs.getString("full_name"));
            do {
                String type = rs.getString("type");
                String value = rs.getString("value");
                if (!rs.wasNull()) { //если смогли считать, значит есть ветка контактов
                    ContactsType contactsType = ContactsType.valueOf(type);
                    resume.addContact(contactsType, value);
                }
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.sqlExecute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException("");
            }
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.sqlExecute("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException("");
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.sqlExecute(""
                + "   SELECT * "
                + "     FROM resume r "
                + "LEFT JOIN contact c ON r.uuid = c.resume_uuid "
                + " ORDER BY r.full_name, r.uuid", ps -> {
            List<Resume> resumes = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            Resume resume = null;
            String uuid = "null";
            while (rs.next()) {
                String fullNameDB = rs.getString("full_name");
                String uuidDB = rs.getString("uuid");
                if (!rs.wasNull()) {
                    if (!uuid.equals(uuidDB)) {
                        resume = new Resume(uuidDB, fullNameDB);
                        resumes.add(resume);
                        uuid = uuidDB;
                    }
                    String type = rs.getString("type");
                    String value = rs.getString("value");
                    if (!rs.wasNull()) {
                        ContactsType contactsType = ContactsType.valueOf(type);
                        resume.addContact(contactsType, value);
                    }
                }
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.sqlExecute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
