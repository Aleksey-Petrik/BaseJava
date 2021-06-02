package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> storage = new HashMap<>();

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
    protected Object findIndex(String uuid) {
        for (Map.Entry<String, Resume> stringResumeEntry : storage.entrySet()) {
            if (uuid.equals(stringResumeEntry.getKey())) {
                return uuid;
            }
        }
        return null;
    }
}
