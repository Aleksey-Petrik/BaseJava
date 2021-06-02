package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void save(Resume r) {
        saveResume(r, isExistForException(r.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return getResume(isNotExistForException(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteResume(isNotExistForException(uuid));
    }

    @Override
    public void update(Resume r) {
        updateResume(r, isNotExistForException(r.getUuid()));
    }

    private Object isExistForException(String uuid) {
        Object key = findSearchKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private Object isNotExistForException(String uuid) {
        Object key = findSearchKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract void updateResume(Resume r, Object key);

    protected abstract void deleteResume(Object key);

    protected abstract Resume getResume(Object key);

    protected abstract void saveResume(Resume r, Object key);

    protected abstract Object findSearchKey(String uuid);

    protected abstract boolean isExist(Object key);

}
