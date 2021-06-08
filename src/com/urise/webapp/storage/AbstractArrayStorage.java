package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void saveResume(Resume r, Integer key) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException(r.getUuid(), "Превышен лимит записей!!!");
        }
        putResume(r, key);
        size++;
    }

    @Override
    protected Resume getResume(Integer key) {
        return storage[key];
    }

    @Override
    protected void deleteResume(Integer key) {
        moveArray(key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void updateResume(Resume r, Integer key) {
        storage[key] = r;
    }

    @Override
    public List<Resume> getListResume() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public int size() {
        return size;
    }

    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    protected abstract void putResume(Resume r, int index);

    protected abstract Integer findSearchKey(String uuid);

    protected abstract void moveArray(int index);

}
