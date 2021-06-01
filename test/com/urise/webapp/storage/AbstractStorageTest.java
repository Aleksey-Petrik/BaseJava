package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractStorageTest {
    protected Storage storageTest;

    protected static final String UUID_1 = "uuid_1";
    protected static final String UUID_2 = "uuid_2";
    protected static final String UUID_3 = "uuid_3";
    protected static final String UUID_4 = "uuid_4";

    protected static final Resume RESUME_1 = new Resume(UUID_1);
    protected static final Resume RESUME_2 = new Resume(UUID_2);
    protected static final Resume RESUME_3 = new Resume(UUID_3);
    protected static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractStorageTest(Storage storageTest) {
        this.storageTest = storageTest;
    }

    @Before
    public void setUp() {
        storageTest.clear();
        storageTest.save(RESUME_1);
        storageTest.save(RESUME_2);
        storageTest.save(RESUME_3);
    }

    @Test
    public void clear() {
        Assert.assertEquals(3, storageTest.getAll().length);
        storageTest.clear();
        Assert.assertEquals(0, storageTest.getAll().length);
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
        Assert.assertEquals(2, storageTest.size());
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
    public void getAll() {
        Resume[] resumes = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(resumes, storageTest.getAll());
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storageTest.size());
        Assert.assertEquals(storageTest.getAll().length, storageTest.size());
    }
}