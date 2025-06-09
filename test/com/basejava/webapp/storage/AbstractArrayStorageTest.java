package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractArrayStorageTest {

    private final Storage storage;

    protected final Resume r1 = new Resume("uuid1");
    protected final Resume r2 = new Resume("uuid2");
    protected final Resume r3 = new Resume("uuid3");
    protected final Resume r4 = new Resume("uuid4");

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(r1);
        assertGet(r1);
        storage.save(r2);
        assertGet(r2);
        storage.save(r3);
        assertGet(r3);
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        Resume[] expected = {r1, r2, r3};
        assertGetAll(expected);
    }

    @org.junit.jupiter.api.Test
    void get() {
        assertGet(r3);
    }

    @org.junit.jupiter.api.Test
    void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> assertGet(r4));
    }

    @org.junit.jupiter.api.Test
    void size() {
        assertSize(3);
    }

    @org.junit.jupiter.api.Test
    void save() {
        storage.save(r4);
        assertGet(r4);
        assertSize(4);
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
        Assertions.assertThrows(NotExistStorageException.class, () -> assertGet(r3));
        assertSize(2);
    }

    @org.junit.jupiter.api.Test
    void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.delete(r4.getUuid()));
    }

    @org.junit.jupiter.api.Test
    void update() {
        storage.update(r3);
        Assertions.assertSame(r3, storage.get(r3.getUuid()));
    }

    @org.junit.jupiter.api.Test
    void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () ->
                storage.update(r4));
    }

    @org.junit.jupiter.api.Test
    void clear() {
        storage.clear();
        assertSize(0);
        Resume[] resumes = {};
        assertGetAll(resumes);
    }

    private void assertGetAll(Resume[] resumes) {
        Assertions.assertArrayEquals(resumes, storage.getAll());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }
}