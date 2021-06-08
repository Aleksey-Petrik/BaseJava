package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void saveResume(Resume r, Integer key) {
        storage.add(r);
    }

    @Override
    protected Resume getResume(Integer key) {
        return storage.get(key);
    }

    @Override
    protected void updateResume(Resume r, Integer key) {
        storage.set(key, r);
    }

    @Override
    protected void deleteResume(Integer key) {
        storage.remove(key.intValue());
    }

    @Override
    public List<Resume> getListResume() {
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected Integer findSearchKey(String uuid) {
        int counter = 0;
        for (Resume resume : storage) {
            if (uuid.equals(resume.getUuid())) {
                return counter;
            }
            counter++;
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

}
