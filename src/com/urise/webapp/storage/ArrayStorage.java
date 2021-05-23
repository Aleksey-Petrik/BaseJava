package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return size == 0 ? -1 : (size + 1) * -1;
    }

    protected void deleteNull(int index) {
        if (index != size - 1) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
        } else {
            storage[index] = null;
        }
    }

}
