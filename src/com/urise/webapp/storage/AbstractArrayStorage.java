package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size < STORAGE_LIMIT) {
            int index = findIndex(r.getUuid());
            if (index < 0) {
                putResume(r, index);
                size++;
            } else {
                System.out.println("ERROR: Резюме с id-" + r.getUuid() + " уже есть в базе!");
            }
        } else {
            System.out.println("ERROR: Превышен лимит записей!!!");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("ERROR: Резюме с id-" + uuid + " не найдено!");
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("ERROR: Резюме с id-" + uuid + " не найдено!");
        }
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.println("ERROR: Резюме с id-" + r.getUuid() + " не найдено!");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    protected abstract void putResume(Resume r, int index);

    protected abstract int findIndex(String uuid);

    protected abstract void deleteResume(int index);

}
