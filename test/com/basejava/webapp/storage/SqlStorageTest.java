package com.basejava.webapp.storage;

import com.basejava.webapp.Config;

class SqlStorageTest extends AbstractStorageTest {

    protected SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }
}
