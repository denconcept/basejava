package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import java.util.Arrays;

public class ArrayStorage {

    private static final int STORAGE_SIZE = 10000;
    private final Resume[] storage = new Resume[STORAGE_SIZE];
    private int resumeCount = 0;

    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (isMissing(index)) {
            return null;
        }
        return storage[index];
    }

    public int size() {
        return resumeCount;
    }

    public void save(Resume r) {
        if (resumeCount > STORAGE_SIZE) {
            System.out.println("\nError: storage is full");
            return;
        }
        if (getIndex(r.uuid) >= 0) {
            System.out.println("\nError: the resume already exists");
            return;
        }
        storage[resumeCount++] = r;
        System.out.println(r.uuid + " saved");
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (isMissing(index)) {
            return;
        }
        resumeCount--;
        storage[index] = storage[resumeCount];
        storage[resumeCount] = null;
        System.out.println("\n" + uuid + " deleted");
    }

    public void clear() {
        Arrays.fill(storage, 0, resumeCount, null);
        resumeCount = 0;
        System.out.println("\nStorage was cleared");
    }

    public void update(Resume r) {
        int index = getIndex(r.uuid);
        if (isMissing(index)) {
            return;
        }
        storage[index] = r;
        System.out.println("\n" + r.uuid + " update successful");
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isMissing(int index) {
        if (index < 0) {
            System.out.println("\nError: Resume doesn't exist");
            return true;
        }
        return false;
    }
}