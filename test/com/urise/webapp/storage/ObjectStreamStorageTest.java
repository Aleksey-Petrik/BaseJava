package com.urise.webapp.storage;

import com.urise.webapp.storage.serialize.ObjectStreamStorageSerializer;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorageSerializer()));
    }

}