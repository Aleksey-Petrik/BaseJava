package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void updateResume(Resume r, Object key) {
        storage.put((String) key, r);
    }

    @Override
    protected void deleteResume(Object key) {
        storage.remove((String) key);
    }

    @Override
    protected Resume getResume(Object key) {
        return storage.get((String) key);
    }

    @Override
    protected void saveResume(Resume r, Object key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Object findSearchKey(String uuid) {
        Resume resume = storage.get(uuid);
        return resume != null ? uuid : null;
    }

    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }

}
