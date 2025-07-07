package com.basejava.webapp.storage;

class ListStorageTest extends AbstractStorageTest {

    protected ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    public void saveOverflow() {
        throw new UnsupportedOperationException("unsupported method");
    }
}