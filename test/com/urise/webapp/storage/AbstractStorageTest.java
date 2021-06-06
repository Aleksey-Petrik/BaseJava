package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected Storage storageTest;

    protected static final String UUID_1 = "uuid_1";
    protected static final String UUID_2 = "uuid_2";
    protected static final String UUID_3 = "uuid_3";
    protected static final String UUID_4 = "uuid_4";
    protected static final String UUID_5 = "uuid_5";

    protected static final Resume RESUME_1 = new Resume(UUID_1, "Julie E. McKay");
    protected static final Resume RESUME_2 = new Resume(UUID_2, "Elaine B. Wise");
    protected static final Resume RESUME_3 = new Resume(UUID_3, "Luis J. Clanton");
    protected static final Resume RESUME_4 = new Resume(UUID_4, "James S. Carroll");
    protected static final Resume RESUME_5 = new Resume(UUID_5, "Elaine B. Wise");

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
        Resume resumeActual = storageTest.get(UUID_3);
        Assert.assertEquals(RESUME_3, resumeActual);
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
        storageTest.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> actualResumes = storageTest.getAllSorted();
        List<Resume> expectedResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3, RESUME_5);
        expectedResumes.sort(Resume.Comparators.FULL_NAMES);
        Assert.assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storageTest.size());
        Assert.assertEquals(storageTest.getAllSorted().size(), storageTest.size());
    }
}