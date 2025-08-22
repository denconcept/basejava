package com.basejava.webapp.storage;

class XmlPathStorageTest extends AbstractStorageTest {

    protected XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, XML_STREAM_SERIALIZER));
    }
}