package com.basejava.webapp.storage;

import com.basejava.storage.FileStorage;
import java.io.File;

class ObjectFileStorageTest extends AbstractStorageTest {
    
    protected ObjectFileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), OBJECT_STREAM_SERIALIZER));
    }
}