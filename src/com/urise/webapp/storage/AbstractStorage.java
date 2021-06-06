package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    @Override
    public void save(Resume r) {
        saveResume(r, getSearchKeyIfNotExist(r.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return getResume(getSearchKeyIfExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        deleteResume(getSearchKeyIfExist(uuid));
    }

    @Override
    public void update(Resume r) {
        updateResume(r, getSearchKeyIfExist(r.getUuid()));
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = getListResume();
        Collections.sort(resumes);
        return resumes;
    }

    private Object getSearchKeyIfNotExist(String uuid) {
        Object key = findSearchKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private Object getSearchKeyIfExist(String uuid) {
        Object key = findSearchKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract List<Resume> getListResume();

    protected abstract void updateResume(Resume r, Object key);

    protected abstract void deleteResume(Object key);

    protected abstract Resume getResume(Object key);

    protected abstract void saveResume(Resume r, Object key);

    protected abstract Object findSearchKey(String key);

    protected abstract boolean isExist(Object key);

}
