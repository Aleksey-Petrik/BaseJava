package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> convertStorageInList() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void updateResume(Resume r, Object key) {
        Resume keyResume = (Resume) key;
        storage.put(keyResume.getUuid(), r);
    }

    @Override
    protected void deleteResume(Object key) {
        Resume resume = (Resume) key;
        storage.remove(resume.getUuid(), resume);
    }

    @Override
    protected Resume getResume(Object key) {
        return (Resume) key;
    }

    @Override
    protected void saveResume(Resume r, Object key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume findSearchKey(String key) {
        return storage.get(key);
    }

    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }

}
