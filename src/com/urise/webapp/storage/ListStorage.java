package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    public void clear() {
        storage.clear();
    }

    protected void saveResume(Resume r, int index) {
        storage.add(r);
    }

    protected Resume getResume(int index) {
        return storage.get(index);
    }

    protected void updateResume(Resume r, int index) {
        storage.set(index, r);
    }

    protected void deleteResume(int index) {
        storage.remove(index);
    }

    public Resume[] getAll() {
        Resume[] res = new Resume[storage.size()];
        return storage.toArray(res);
    }

    public int size() {
        return storage.size();
    }

    protected int findIndex(String uuid) {
        for (Resume resume : storage) {
            if (uuid.equals(resume.getUuid())) {
                return storage.indexOf(resume);
            }
        }
        return -1;
    }

}
