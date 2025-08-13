package com.basejava.webapp.storage;

class ObjectStreamStorageTest extends AbstractStorageTest {

    protected ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR, OBJECT_STREAM_STRATEGY));
    }
}