/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; storage[i] != null; i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        storage[size()] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; storage[i] != null; i++) {
            if (uuid.equals(storage[i].uuid)) return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; storage[i] != null; i++) {
            if (uuid.equals(storage[i].uuid)) {
                if (i != size() - 1) {
                    storage[i] = storage[size() - 1];
                    storage[size() - 1] = null;
                } else storage[i] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size()];
        for (int i = 0; storage[i] != null; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    int size() {
        int counter;
        for (counter = 0; storage[counter] != null; counter++) {
        }
        return counter;
    }
}
