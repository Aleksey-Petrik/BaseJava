package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    protected void clearStorage() {
        storage.clear();
    }

    protected void saveResume(Resume r, int index) {
        storage.add(Math.abs(index + 1), r);
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

    protected Resume[] getStorage() {
        Resume[] res = new Resume[storage.size()];
        return storage.toArray(res);
    }

    protected int getSize() {
        return storage.size();
    }

    protected int findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Collections.binarySearch(storage, searchKey);
    }

}
