package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.Config;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected Storage storageTest;

    protected static final String UUID_1 = UUID.randomUUID().toString();
    protected static final String UUID_2 = UUID.randomUUID().toString();
    protected static final String UUID_3 = UUID.randomUUID().toString();
    protected static final String UUID_4 = UUID.randomUUID().toString();
    protected static final String UUID_5 = UUID.randomUUID().toString();
    /*
        protected static final Resume RESUME_1 = ResumeTestData.createFullResume(UUID_1, "Julie E. McKay", ResumeTestData.TypeFillData.ALL_SECTIONS);
        protected static final Resume RESUME_2 = ResumeTestData.createFullResume(UUID_2, "Elaine B. Wise", ResumeTestData.TypeFillData.EXCEPT_STUDY_WORK);
        protected static final Resume RESUME_3 = ResumeTestData.createFullResume(UUID_3, "Luis J. Clanton", ResumeTestData.TypeFillData.PERSONAL_QUALITIES);
        protected static final Resume RESUME_4 = ResumeTestData.createFullResume(UUID_4, "James S. Carroll", ResumeTestData.TypeFillData.CONTACTS);
        protected static final Resume RESUME_5 = ResumeTestData.createFullResume(UUID_5, "Elaine B. Wise", ResumeTestData.TypeFillData.ALL_SECTIONS);
     */
    protected static final Resume RESUME_1 = ResumeTestData.createFullResume(UUID_1, "Julie E. McKay", ResumeTestData.TypeFillData.EXCEPT_STUDY_WORK);
    protected static final Resume RESUME_2 = ResumeTestData.createFullResume(UUID_2, "Elaine B. Wise", ResumeTestData.TypeFillData.UUID_NAME);
    protected static final Resume RESUME_3 = ResumeTestData.createFullResume(UUID_3, "Luis J. Clanton", ResumeTestData.TypeFillData.CONTACTS);
    protected static final Resume RESUME_4 = ResumeTestData.createFullResume(UUID_4, "James S. Carroll", ResumeTestData.TypeFillData.EXCEPT_STUDY_WORK);
    protected static final Resume RESUME_5 = ResumeTestData.createFullResume(UUID_5, "Elaine B. Wise", ResumeTestData.TypeFillData.CONTACTS);

    public AbstractStorageTest(Storage storageTest) {
        this.storageTest = storageTest;
    }

    @Before
    public void setUp() {
        storageTest.clear();
        storageTest.save(RESUME_5);
        storageTest.save(RESUME_2);
        storageTest.save(RESUME_1);
        storageTest.save(RESUME_3);
    }

    @Test
    public void clear() {
        Assert.assertEquals(4, storageTest.getAllSorted().size());
        storageTest.clear();
        Assert.assertEquals(0, storageTest.getAllSorted().size());
        Assert.assertEquals(0, storageTest.size());
    }

    @Test
    public void save() {
        storageTest.save(RESUME_4);
        Resume resumeActual = storageTest.get(UUID_4);
        Assert.assertEquals(RESUME_4, resumeActual);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storageTest.save(RESUME_2);
    }

    @Test
    public void get() {
        Resume resumeActual = storageTest.get(UUID_1);
        Assert.assertEquals(RESUME_1, resumeActual);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storageTest.get(UUID_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storageTest.delete(UUID_2);
        Assert.assertEquals(3, storageTest.size());
        storageTest.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storageTest.delete(UUID_4);
    }

    @Test
    public void update() {
        storageTest.update(RESUME_3);
        Resume resumeActual = storageTest.get(UUID_3);
        Assert.assertEquals(RESUME_3, resumeActual);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storageTest.update(RESUME_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> actualResumes = storageTest.getAllSorted();
        List<Resume> expectedResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3, RESUME_5);
        expectedResumes.sort(Resume::compareTo);
        Assert.assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storageTest.size());
        Assert.assertEquals(storageTest.getAllSorted().size(), storageTest.size());
    }
}