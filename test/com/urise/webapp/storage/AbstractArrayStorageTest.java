package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storageTest;

    public static final String UUID_1 = "uuid_1";
    public static final String UUID_2 = "uuid_2";
    public static final String UUID_3 = "uuid_3";
    public static final String UUID_4 = "uuid_4";
    public static final String UUID_5 = "uuid_5";

    @Before
    public void setUp() throws Exception {
        storageTest.clear();
        storageTest.save(new Resume(UUID_1));
        storageTest.save(new Resume(UUID_2));
        storageTest.save(new Resume(UUID_3));
    }

    public AbstractArrayStorageTest(Storage storageTest) {
        this.storageTest = storageTest;
    }

    @Test
    public void clear() throws Exception {
        storageTest.clear();
        Assert.assertEquals(0, storageTest.getAll().length);
        Assert.assertEquals(0, storageTest.size());
    }

    @Test
    public void save() throws Exception {
        Resume resumeExpected = new Resume("uuid_4");
        storageTest.save(resumeExpected);
        Resume resumeActual = storageTest.get(UUID_4);
        Assert.assertEquals(resumeExpected, resumeActual);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storageTest.save(new Resume(UUID_2));
    }

    @Test
    public void saveLimitExceeded() throws Exception {
        try {
            fillAllStorage(AbstractArrayStorage.STORAGE_LIMIT - 3);
        } catch (StorageException err) {
            Assert.fail("Произошло переполнение хранилища!");
        }
    }

    @Test(expected = StorageException.class)
    public void saveStorageException() throws Exception {
        fillAllStorage(AbstractArrayStorage.STORAGE_LIMIT);
    }

    @Test
    public void get() throws Exception {
        Resume resumeExpected = new Resume(UUID_3);
        Resume resumeActual = storageTest.get(UUID_3);
        Assert.assertEquals(resumeExpected, resumeActual);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storageTest.get(UUID_5);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storageTest.delete(UUID_2);
        Assert.assertEquals(2, storageTest.size());
        Resume resumeActual = storageTest.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storageTest.delete(UUID_5);
    }

    @Test
    public void update() throws Exception {
        Resume resumeExpected = new Resume(UUID_3);
        storageTest.update(resumeExpected);
        Resume resumeActual = storageTest.get(UUID_3);
        Assert.assertEquals(resumeExpected, resumeActual);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storageTest.delete(UUID_5);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumes = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Assert.assertArrayEquals(resumes, storageTest.getAll());
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storageTest.size());
        storageTest.save(new Resume(UUID_4));
        Assert.assertEquals(4, storageTest.size());
        Assert.assertEquals(storageTest.getAll().length, storageTest.size());
    }

    private void fillAllStorage(int length) throws Exception {
        for (int i = 0; i < length; i++) {
            storageTest.save(new Resume());
        }
    }
}