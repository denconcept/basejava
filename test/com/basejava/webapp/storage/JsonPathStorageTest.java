package com.basejava.webapp.storage;

class JsonPathStorageTest extends AbstractStorageTest {

    protected JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, JSON_STREAM_SERIALIZER));
    }
}