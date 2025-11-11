package com.basejava.webapp.storage;

import com.basejava.webapp.Config;

class SqlStorageTest extends AbstractStorageTest {

    protected SqlStorageTest() {
        super(new SqlStorage(
                Config.getInstance().getDbUrl(),
                Config.getInstance().getDbUser(),
                Config.getInstance().getDbPassword()
        ));
    }
}
