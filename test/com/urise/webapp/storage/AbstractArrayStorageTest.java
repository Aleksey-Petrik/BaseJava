package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storageTest) {
        super(storageTest);
    }

    @Test(expected = StorageException.class)
    public void saveLimitExceeded() throws StorageException {
        try {
            fillAllStorage(AbstractArrayStorage.STORAGE_LIMIT - storageTest.size());
        } catch (StorageException err) {
            Assert.fail("Произошло переполнение хранилища, раньше времени!");
        }
        storageTest.save(RESUME_4);
    }

    private void fillAllStorage(int length) {
        for (int i = 0; i < length; i++) {
            storageTest.save(new Resume());
        }
    }
}