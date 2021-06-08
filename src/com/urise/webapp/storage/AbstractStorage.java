package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {
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

    private SK getSearchKeyIfNotExist(String uuid) {
        SK key = findSearchKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private SK getSearchKeyIfExist(String uuid) {
        SK key = findSearchKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract List<Resume> getListResume();

    protected abstract void updateResume(Resume r, SK key);

    protected abstract void deleteResume(SK key);

    protected abstract Resume getResume(SK key);

    protected abstract void saveResume(Resume r, SK key);

    protected abstract SK findSearchKey(String key);

    protected abstract boolean isExist(SK key);

}
