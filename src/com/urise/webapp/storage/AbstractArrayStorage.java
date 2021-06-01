package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected void saveResume(Resume r, int index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException(r.getUuid(), "Превышен лимит записей!!!");
        }
        putResume(r, index);
        size++;
    }

    protected Resume getResume(int index) {
        return storage[index];
    }

    protected void deleteResume(int index) {
        moveArray(index);
        storage[size - 1] = null;
        size--;
    }

    protected void updateResume(Resume r, int index) {
        storage[index] = r;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    protected abstract void putResume(Resume r, int index);

    protected abstract int findIndex(String uuid);

    protected abstract void moveArray(int index);

}
