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
    protected void saveResume(Resume r, int index) {
        storage.add(r);
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected void updateResume(Resume r, int index) {
        storage.set(index, r);
    }

    @Override
    protected void deleteResume(int index) {
        storage.remove(index);
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

    protected int findIndex(String uuid) {
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
