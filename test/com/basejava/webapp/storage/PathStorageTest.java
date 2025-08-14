package com.basejava.webapp.storage;

class PathStorageTest extends AbstractStorageTest {

    protected PathStorageTest() {
        super(new PathStorage(STORAGE_DIR, OBJECT_STREAM_STRATEGY));
    }
}