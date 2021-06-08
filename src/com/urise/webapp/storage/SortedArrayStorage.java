package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    protected Integer findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "Only For Search");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
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
