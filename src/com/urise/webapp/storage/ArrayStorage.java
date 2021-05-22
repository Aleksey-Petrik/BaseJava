package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (size < STORAGE_LIMIT) {
            if (findIndex(r.getUuid()) < 0) {
                storage[size++] = r;
            } else {
                System.out.println("ERROR: Резюме с id-" + r.getUuid() + " уже есть в базе!");
            }
        } else {
            System.out.println("ERROR: Превышен лимит записей!!!");
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            size--;
            if (index != size) {
                storage[index] = storage[size];
                storage[size] = null;
            } else {
                storage[index] = null;
            }
        } else {
            System.out.println("ERROR: Резюме с id-" + uuid + " не найдено!");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
