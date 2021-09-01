package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        //Инициализация драйвера Базы Данных
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No driver found for PostgreSQL database", e);
        }
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
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid() + " the resume does not exist!");
                }
                //Чтобы каждый контакт не обновлять, просто удалим все, а после вернем старые/новые значения в секции
                deleteContents(connection, resume, "contact");
                insertContacts(resume, connection);
                deleteContents(connection, resume, "section");
                insertSections(resume, connection);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {

        return sqlHelper.transactionExecute(connection -> {
            Resume resume;
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid + " the resume does not exist!");
                }
                resume = new Resume(rs.getString("uuid"),
                        rs.getString("full_name"));
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(resume, rs);
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(resume, rs);
                }
            }

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
    public List<Resume> getAllSorted() {

        return sqlHelper.transactionExecute(connection -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String fullName = rs.getString("full_name");
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, fullName));
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(resumes.get(rs.getString("resume_uuid")), rs);
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(resumes.get(rs.getString("resume_uuid")), rs);
                }
            }

            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.sqlExecute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        String type = rs.getString("type");
        if (!rs.wasNull()) { //если смогли считать, значит есть ветка контактов
            ContactsType contactsType = ContactsType.valueOf(type);
            resume.addContact(contactsType, value);
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        String content = rs.getString("value");
        if (!rs.wasNull()) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            //десериализация из формата Json в секцию
            resume.addSection(type, JsonParser.read(content, AbstractSection.class));
        }
    }

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(""
                + "INSERT INTO contact (type, value, resume_uuid)"
                + "     VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactsType, String> contact : resume.getContacts().entrySet()) {
                ps.setString(1, contact.getKey().name());
                ps.setString(2, contact.getValue());
                ps.setString(3, resume.getUuid());
                ps.addBatch();//Добавление операции на исполнение
            }
            ps.executeBatch();//Выполнение всех накопленных команд
        }
    }

    private void insertSections(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(""
                + "INSERT INTO section (type, value, resume_uuid)"
                + "VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, AbstractSection> elementSection : resume.getSections().entrySet()) {
                SectionType type = elementSection.getKey();
                ps.setString(1, type.name());
                //сериализация всей секции в формат Json
                ps.setString(2, JsonParser.write(elementSection.getValue(), AbstractSection.class));
                ps.setString(3, resume.getUuid());
                ps.addBatch();//Добавление операции на исполнение
            }
            ps.executeBatch();
        }
    }

    private void deleteContents(Connection connection, Resume resume, String table) throws SQLException {
        try (PreparedStatement psDelete = connection.prepareStatement("DELETE FROM " + table + " WHERE resume_uuid = ?")) {
            psDelete.setString(1, resume.getUuid());
            psDelete.execute();
        }
    }
}
