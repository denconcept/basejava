package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractArrayStorageTest {

    private final Storage storage;

    final Resume r1 = new Resume("uuid1");
    final Resume r2 = new Resume("uuid2");
    final Resume r3 = new Resume("uuid3");
    final Resume r4 = new Resume("uuid4");

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        Resume[] expected = {r1, r2, r3};
        Assertions.assertArrayEquals(expected, storage.getAll());
    }

    @org.junit.jupiter.api.Test
    void get() {
        Assertions.assertEquals(r3, storage.get("uuid3"));
    }

    @org.junit.jupiter.api.Test
    void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.get("dummy"));
    }

    @org.junit.jupiter.api.Test
    void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @org.junit.jupiter.api.Test
    void save() {
        storage.save(r4);
        Assertions.assertEquals(r4, storage.get(r4.getUuid()));
        Assertions.assertEquals(4, storage.size());
    }

    @org.junit.jupiter.api.Test
    void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () ->
                storage.save(r1));
    }

    @org.junit.jupiter.api.Test
    void saveStorageOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < 10000; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            Assertions.fail("Переполнение произошло раньше времени!");
        }
        Assertions.assertThrows(StorageException.class, () ->
                storage.save(new Resume("overflowUuid")));
    }

    @org.junit.jupiter.api.Test
    void delete() {
        storage.delete(r3.getUuid());
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.get(r3.getUuid()));
        Assertions.assertEquals(2, storage.size());
    }

    @org.junit.jupiter.api.Test
    void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.delete(r4.getUuid()));
    }

    @org.junit.jupiter.api.Test
    void update() {
        storage.update(r3);
        Assertions.assertEquals(r3, storage.get(r3.getUuid()));
    }

    @org.junit.jupiter.api.Test
    void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.update(r4));
    }

    @org.junit.jupiter.api.Test
    void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }
}