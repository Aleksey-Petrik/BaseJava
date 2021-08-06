package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.util.Config;

import java.util.List;
import java.util.UUID;

public class MainSql {
    public static void main(String[] args) {
/*
        Resume resume1 = new Resume(UUID.randomUUID().toString(), "ALEX1");
        Resume resume2 = new Resume(UUID.randomUUID().toString(), "ALEX2");
        Resume resume3 = new Resume(UUID.randomUUID().toString(), "ALEX3");
        Resume resume4 = new Resume(UUID.randomUUID().toString(), "ALEX4");

 */
        SqlStorage sqlStorage = new SqlStorage(Config.get().getDbUrl(),
                Config.get().getDbUser(),
                Config.get().getDbPassword());

        List<Resume> resumes = sqlStorage.getAllSorted();
        System.out.println(resumes.toString());

        //Resume resume3 = ResumeTestData.createFullResume(UUID.randomUUID().toString(), "Julie E. McKay", ResumeTestData.TypeFillData.CONTACTS);
        //sqlStorage.save(resume3);

        Resume resume2 = sqlStorage.get("2d1e9322-2373-41b8-a8af-dc1b08069e8a");
        System.out.println(resume2.getFullName());
        System.out.println(resume2.getContacts().toString());

        Resume resume = sqlStorage.get("252dfe75-ee71-41a0-b430-a876eeacfe6f");
        System.out.println(resume.getFullName());
        System.out.println(resume.getContacts().toString());

/*
        sqlStorage.clear();
        sqlStorage.save(resume1);
        sqlStorage.save(resume2);
        System.out.println(sqlStorage.size());
        sqlStorage.save(resume3);
        sqlStorage.save(resume4);
        System.out.println(sqlStorage.size());
 */
    }
}
