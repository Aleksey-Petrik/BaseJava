package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.*;

public abstract class AbstractStorage<SK> implements Storage {
    private final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    {
        Handler handler = new ConsoleHandler();
        handler.setFormatter(new XMLFormatter());
        LOG.addHandler(handler);
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        saveResume(r, getSearchKeyIfNotExist(r.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return getResume(getSearchKeyIfExist(uuid));
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        deleteResume(getSearchKeyIfExist(uuid));
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        updateResume(r, getSearchKeyIfExist(r.getUuid()));
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> resumes = getListResume();
        Collections.sort(resumes);
        return resumes;
    }

    private SK getSearchKeyIfNotExist(String uuid) {
        SK key = findSearchKey(uuid);
        if (isExist(key)) {
            LOG.warning(uuid + " - резюме с таким id уже есть в базе!");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private SK getSearchKeyIfExist(String uuid) {
        SK key = findSearchKey(uuid);
        if (!isExist(key)) {
            LOG.warning(uuid + " - резюме с таким id нет в базе!");
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
