package com.urise.webapp.storage;

import com.urise.webapp.storage.serialize.ObjectStreamStorageSerialize;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorageSerialize()));
    }

}