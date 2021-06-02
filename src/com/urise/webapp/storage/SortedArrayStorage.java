package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    protected Object findIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void putResume(Resume r, int index) {
        index = Math.abs(index + 1);
        if (index < size) {
            System.arraycopy(storage, index, storage, index + 1, size - index);
        }
        storage[index] = r;
    }

    protected void moveArray(int index) {
        if ((size - 1) - index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, (size - 1) - index);
        }
    }

}
