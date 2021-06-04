package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void saveResume(Resume r, Object key) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException(r.getUuid(), "Превышен лимит записей!!!");
        }
        putResume(r, (int) key);
        size++;
    }

    @Override
    protected Resume getResume(Object key) {
        return storage[(int) key];
    }

    @Override
    protected void deleteResume(Object key) {
        moveArray((int) key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void updateResume(Resume r, Object key) {
        storage[(int) key] = r;
    }

    @Override
    public List<Resume> convertStorageInList() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public int size() {
        return size;
    }

    protected boolean isExist(Object key) {
        return (int) key >= 0;
    }

    protected abstract void putResume(Resume r, int index);

    protected abstract Integer findSearchKey(String uuid);

    protected abstract void moveArray(int index);

}
