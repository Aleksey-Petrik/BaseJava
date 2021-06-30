package com.urise.webapp.storage;

import com.urise.webapp.storage.serialize.DataStreamStorageSerializer;

public class DataStreamPathStorageTest extends AbstractStorageTest {

    public DataStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new DataStreamStorageSerializer()));
    }

}