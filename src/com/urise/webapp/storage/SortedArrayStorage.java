package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (size < STORAGE_LIMIT) {
            int index = findIndex(r.getUuid());
            if (index < 0) {
                index = Math.abs(index + 1);
                Resume currentResume = r;
                while (currentResume != null) {
                    Resume bufferResume = storage[index];
                    storage[index] = currentResume;
                    currentResume = bufferResume;
                    index++;
                }
                size++;
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
            for (int i = index; i < size; i++) {
                storage[i] = storage[i + 1];
            }
            size--;
        } else {
            System.out.println("ERROR: Резюме с id-" + uuid + " не найдено!");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
