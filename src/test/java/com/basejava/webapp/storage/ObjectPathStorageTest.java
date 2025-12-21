package com.basejava.webapp.storage;

import com.basejava.storage.PathStorage;

class ObjectPathStorageTest extends AbstractStorageTest {
    
    protected ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, OBJECT_STREAM_SERIALIZER));
    }
}