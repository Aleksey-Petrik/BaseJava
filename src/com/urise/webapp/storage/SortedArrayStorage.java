package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based storage for Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR_LAMBDA = (r1, r2) -> r1.getUuid().compareTo(r2.getUuid());
    private static final Comparator<Resume> RESUME_COMPARATOR_2 = Comparator.comparing(Resume::getUuid);
    private static final Comparator<Resume> RESUME_COMPARATOR = new ResumeComparator();

    private static class ResumeComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume r1, Resume r2) {
            return r1.getUuid().compareTo(r2.getUuid());
        }
    }

    protected Integer findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "Only For Search");
        return Arrays.binarySearch(storage, 0, size, searchKey, Resume.Comparators.UUID);
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
