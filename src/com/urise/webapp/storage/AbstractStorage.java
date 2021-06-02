package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void save(Resume r) {
        Object key = findIndex(r.getUuid());
        if (!isExist(key)) {
            saveResume(r, key);
        } else {
            throw new ExistStorageException(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        Object key = findIndex(uuid);
        if (isExist(key)) {
            return getResume(key);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        Object key = findIndex(uuid);
        if (isExist(key)) {
            deleteResume(key);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume r) {
        Object key = findIndex(r.getUuid());
        if (isExist(key)) {
            updateResume(r, key);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    private boolean isExist(Object obj) {
        if (obj instanceof Integer) {
            return (int) obj >= 0;
        } else return obj instanceof String;
    }

    protected abstract void updateResume(Resume r, Object key);

    protected abstract void deleteResume(Object key);

    protected abstract Resume getResume(Object key);

    protected abstract void saveResume(Resume r, Object key);

    protected abstract Object findIndex(String uuid);

}
