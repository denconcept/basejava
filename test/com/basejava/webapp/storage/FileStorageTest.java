package com.basejava.webapp.storage;

import java.io.File;

class FileStorageTest extends AbstractStorageTest {

    protected FileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), OBJECT_STREAM_STRATEGY));
    }
}