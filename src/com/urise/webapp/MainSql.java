package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.util.Config;

import java.util.UUID;

public class MainSql {
    public static void main(String[] args) {

        Resume resume1 = new Resume(UUID.randomUUID().toString(), "ALEX1");
        Resume resume2 = new Resume(UUID.randomUUID().toString(), "ALEX2");
        Resume resume3 = new Resume(UUID.randomUUID().toString(), "ALEX3");
        Resume resume4 = new Resume(UUID.randomUUID().toString(), "ALEX4");
        SqlStorage sqlStorage = new SqlStorage(Config.get().getDbUrl(),
                Config.get().getDbUser(),
                Config.get().getDbPassword());

        sqlStorage.clear();
        sqlStorage.save(resume1);
        sqlStorage.save(resume2);
        System.out.println(sqlStorage.size());
        sqlStorage.save(resume3);
        sqlStorage.save(resume4);
        System.out.println(sqlStorage.size());
    }
}
