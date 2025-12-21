package com.basejava.webapp.storage;

import com.basejava.storage.PathStorage;

class JsonPathStorageTest extends AbstractStorageTest {

    protected JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, JSON_STREAM_SERIALIZER));
    }
}