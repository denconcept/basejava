package com.basejava.webapp.storage;

class ListStorageTest extends AbstractStorageTest {

    protected ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    public void saveStorageOverflow() {
        throw new UnsupportedOperationException("unsupported method");
    }
}