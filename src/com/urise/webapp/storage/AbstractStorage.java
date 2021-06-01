package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index < 0) {
            saveResume(r, index);
        } else {
            throw new ExistStorageException(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return getResume(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            updateResume(r, index);
        } else {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    protected abstract void updateResume(Resume r, int index);

    protected abstract void deleteResume(int index);

    protected abstract Resume getResume(int index);

    protected abstract void saveResume(Resume r, int index);

    protected abstract int findIndex(String uuid);

}
