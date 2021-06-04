package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storageTest) {
        super(storageTest);
    }

    @Test(expected = StorageException.class)
    public void saveLimitExceeded() throws StorageException {
        try {
            int len = AbstractArrayStorage.STORAGE_LIMIT - storageTest.size();
            for (int i = 0; i < len; i++) {
                storageTest.save(new Resume(UUID.randomUUID().toString()));
            }
        } catch (StorageException err) {
            Assert.fail("Произошло переполнение хранилища, раньше времени!");
        }
        storageTest.save(RESUME_4);
    }

}