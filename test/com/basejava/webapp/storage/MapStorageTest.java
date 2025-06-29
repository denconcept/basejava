package com.basejava.webapp.storage;

class MapStorageTest extends AbstractStorageTest {

    protected MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    protected void saveStorageOverflow() {
        throw new UnsupportedOperationException("unsupported method");
    }
}