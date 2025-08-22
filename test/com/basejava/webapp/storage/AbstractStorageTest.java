package com.basejava.webapp.storage;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serializer.JsonStreamSerializer;
import com.basejava.webapp.storage.serializer.ObjectStreamSerializer;
import com.basejava.webapp.storage.serializer.StreamSerializer;
import com.basejava.webapp.storage.serializer.XmlStreamSerializer;

import static com.basejava.webapp.ResumeTestData.createResume;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractStorageTest {

    protected static final String STORAGE_DIR =
            "/Users/denis/Java/BaseJava/basejava/storage";
    protected static final StreamSerializer OBJECT_STREAM_SERIALIZER = new ObjectStreamSerializer();
    protected static final StreamSerializer XML_STREAM_SERIALIZER = new XmlStreamSerializer();
    protected static final StreamSerializer JSON_STREAM_SERIALIZER = new JsonStreamSerializer();
    protected final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    protected static final Resume RESUME_1;
    protected static final Resume RESUME_2;
    protected static final Resume RESUME_3;
    protected static final Resume RESUME_4;

    static {
        RESUME_1 = createResume(UUID_1, "Name1");
        RESUME_2 = createResume(UUID_2, "Name2");
        RESUME_3 = createResume(UUID_3, "Name4");
        RESUME_4 = createResume(UUID_4, "Name4");
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        assertGet(RESUME_1);
        storage.save(RESUME_2);
        assertGet(RESUME_2);
        storage.save(RESUME_3);
        assertGet(RESUME_3);
    }

    @org.junit.jupiter.api.Test
    void getAllSorted() {
        List<Resume> resumes = List.of(RESUME_1, RESUME_2, RESUME_3);
        assertGetAll(resumes);
    }

    @org.junit.jupiter.api.Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @org.junit.jupiter.api.Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> assertGet(RESUME_4));
    }

    @org.junit.jupiter.api.Test
    void size() {
        assertSize(3);
    }

    @org.junit.jupiter.api.Test
    void save() {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @org.junit.jupiter.api.Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @org.junit.jupiter.api.Test
    void delete() {
        storage.delete(RESUME_3.getUuid());
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> assertGet(RESUME_3));
    }

    @org.junit.jupiter.api.Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(RESUME_4.getUuid()));
    }

    @org.junit.jupiter.api.Test
    void update() {
        Resume newResume = new Resume(UUID_3, "New Resume");
        storage.update(newResume);
        assertEquals(newResume, storage.get(RESUME_3.getUuid()));
    }

    @org.junit.jupiter.api.Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_4));
    }

    @org.junit.jupiter.api.Test
    void clear() {
        storage.clear();
        assertSize(0);
        List<Resume> resumes = new ArrayList<>();
        assertGetAll(resumes);
    }

    private void assertGetAll(List<Resume> resumes) {
        assertEquals(resumes, storage.getAllSorted());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}