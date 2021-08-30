package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
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
        return sqlHelper.sqlExecute(""
                + "   SELECT * "
                + "     FROM resume r "
                + "LEFT JOIN contact c ON r.uuid = c.resume_uuid "
                + " ORDER BY r.full_name, r.uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            Resume resume = null;
            String uuid = "null";
            while (rs.next()) {
                String fullNameDB = rs.getString("full_name");
                String uuidDB = rs.getString("uuid");
                if (!rs.wasNull()) {
                    if (!uuid.equals(uuidDB)) {//Если uuid равен ранее добавленному, значит добавляем для него контакты
                        resume = new Resume(uuidDB, fullNameDB);
                        resumes.add(resume);
                        uuid = uuidDB;
                    }
                    addContact(resume, rs);
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

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        String type = rs.getString("type");
        if (!rs.wasNull()) { //если смогли считать, значит есть ветка контактов
            ContactsType contactsType = ContactsType.valueOf(type);
            resume.addContact(contactsType, value);
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        String type = rs.getString("type");
        if (!rs.wasNull()) { //если смогли считать, значит есть ветка контактов
            AbstractSection abstractSection = null;
            SectionType sectionType = SectionType.valueOf(type);
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    abstractSection = new TextSection(value);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    abstractSection = new ListTextSection();
                    String[] contents = value.split("\n");
                    for (String content : contents) {
                        ((ListTextSection) abstractSection).addContent(content);
                    }
            }
            resume.addSection(sectionType, abstractSection);
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
            for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
                SectionType type = section.getKey();
                ps.setString(1, type.name());
                ps.setString(2, section.getValue().getContents());
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
