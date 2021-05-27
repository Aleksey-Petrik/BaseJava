package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Before;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private static final Storage storageTest = new SortedArrayStorage();

    @Before
    public void setUp() throws Exception {
        storageTest.clear();
        storageTest.save(new Resume(UUID_1));
        storageTest.save(new Resume(UUID_3));
        storageTest.save(new Resume(UUID_2));
    }

    public SortedArrayStorageTest() {
        super(storageTest);
    }
}