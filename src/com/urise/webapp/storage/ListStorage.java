package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void saveResume(Resume r, Object key) {
        storage.add(r);
    }

    @Override
    protected Resume getResume(Object key) {
        return storage.get((int) key);
    }

    @Override
    protected void updateResume(Resume r, Object key) {
        storage.set((int) key, r);
    }

    @Override
    protected void deleteResume(Object key) {
        storage.remove((int) key);
    }

    @Override
    public Resume[] getAll() {
        Resume[] res = new Resume[storage.size()];
        return storage.toArray(res);
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected Object findIndex(String uuid) {
        int counter = 0;
        for (Resume resume : storage) {
            if (uuid.equals(resume.getUuid())) {
                return counter;
            }
            counter++;
        }
        return -1;
    }

}
