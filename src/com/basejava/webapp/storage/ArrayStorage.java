package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes.
 */
public class ArrayStorage {

    private static final int STORAGE_SIZE = 10000;
    private final Resume[] storage = new Resume[STORAGE_SIZE];
    private int resumeCount = 0;

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    public Resume get(String uuid) {
        for (Resume resume : getAll()) {
            if (uuid.equals(resume.toString())) {
                return resume;
            }
        }
        return null;
    }

    public int size() {
        return resumeCount;
    }

    public boolean save(Resume r) {
        if (isExist(r.uuid) || isFull()) return false;
        storage[resumeCount++] = r;
        return true;
    }

    public boolean delete(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (uuid.equals(storage[i].toString())) {
                resumeCount--;
                storage[i] = storage[resumeCount];
                storage[resumeCount] = null;
                return true;
            }
        }
        return false;
    }

    public void clear() {
        Arrays.fill(storage, 0, resumeCount, null);
        resumeCount = 0;
    }

    public boolean update(Resume r) {
        if (!isExist(r.uuid)) return false;
        r.uuid = "uuid4";
        return true;
    }

    private boolean isFull() {
        return resumeCount > STORAGE_SIZE;
    }

    private boolean isExist(String uuid) {
        for (Resume resume : getAll()) {
            if (uuid.equals(resume.toString())) return true;
        }
        return false;
    }
}