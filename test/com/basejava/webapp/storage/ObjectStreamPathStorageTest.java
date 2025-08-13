package com.basejava.webapp.storage;

class ObjectStreamPathStorageTest extends AbstractStorageTest {

    protected ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR, OBJECT_STREAM_STRATEGY));
    }
}