package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size < storage.length) {
            if (find(r.getUuid()) < 0) storage[size++] = r;
            else System.out.println("ERROR: Резюме с id-" + r.getUuid() + " уже есть в базе!");
        } else System.out.println("ERROR: Превышен лимит записей!!!");
    }

    public Resume get(String uuid) {
        int i = find(uuid);
        if (i >= 0) return storage[i];
        else System.out.println("ERROR: Резюме с id-" + uuid + " не найдено!");
        return null;
    }

    public void delete(String uuid) {
        int i = find(uuid);
        if (i >= 0){
            size--;
            if (i != size) {
                storage[i] = storage[size];
                storage[size] = null;
            } else storage[i] = null;
        }
        else System.out.println("ERROR: Резюме с id-" + uuid + " не найдено!");
    }

    public void update(Resume r) {
        int i = find(r.getUuid());
        if (i >= 0) storage[i] = r;
        else System.out.println("ERROR: Резюме с id-" + r.getUuid() + " не найдено!");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0,size);
    }

    public int size() {
        return size;
    }

    private int find(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) return i;
        }
        return -1;
    }
}
