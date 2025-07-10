package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @org.junit.jupiter.api.Test
    void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("Name" + i));
            }
        } catch (StorageException e) {
            fail("Переполнение произошло раньше времени!");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("Overflow")));
    }
}
