package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    private Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getListResume() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void updateResume(Resume r, String key) {
        storage.put(key, r);
    }

    @Override
    protected void deleteResume(String key) {
        storage.remove(key);
    }

    @Override
    protected Resume getResume(String key) {
        return storage.get(key);
    }

    @Override
    protected void saveResume(Resume r, String key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected String findSearchKey(String uuid) {
        return storage.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected boolean isExist(String key) {
        return key != null;
    }

}
