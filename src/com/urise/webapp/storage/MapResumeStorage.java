package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected void updateResume(Resume r, Resume key) {
        storage.put(key.getUuid(), r);
    }

    @Override
    protected void deleteResume(Resume key) {
        storage.remove(key.getUuid(), key);
    }

    @Override
    protected Resume getResume(Resume key) {
        return key;
    }

    @Override
    protected void saveResume(Resume r, Resume key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume findSearchKey(String key) {
        return storage.get(key);
    }

    @Override
    protected boolean isExist(Resume key) {
        return key != null;
    }

}
