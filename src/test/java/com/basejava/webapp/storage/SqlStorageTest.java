package com.basejava.webapp.storage;

import com.basejava.Config;

class SqlStorageTest extends AbstractStorageTest {
    
    protected SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }
}