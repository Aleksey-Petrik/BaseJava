package com.urise.webapp.storage;

import com.urise.webapp.storage.serialize.ObjectStreamStorageSerialize;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectStreamStorageSerialize()));
    }

}