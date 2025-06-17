package com.basejava.webapp.storage;

class ListStorageTest extends AbstractArrayStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    public void saveStorageOverflow() {
        throw new UnsupportedOperationException("Метод не поддерживается");
    }
}