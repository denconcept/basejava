package com.basejava.webapp.storage;

import com.basejava.storage.PathStorage;

class XmlPathStorageTest extends AbstractStorageTest {

    protected XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, XML_STREAM_SERIALIZER));
    }
}