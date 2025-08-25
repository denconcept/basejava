package com.basejava.webapp.storage;

class DataPathStorageTest extends AbstractStorageTest {

    protected DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, DATA_STREAM_SERIALIZER));
    }
}