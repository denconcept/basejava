package com.basejava.webapp.storage;

class MapStorageTest extends AbstractArrayStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

    @Override
    public void saveStorageOverflow() {
        throw new UnsupportedOperationException("unsupported method");
    }
}