package com.basejava.webapp.storage;

class ObjectPathStorageTest extends AbstractStorageTest {

    protected ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, OBJECT_STREAM_SERIALIZER));
    }
}