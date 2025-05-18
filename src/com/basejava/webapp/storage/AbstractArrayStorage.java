package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (isMissing(index, uuid)) {
            return null;
        }
        return storage[index];
    }

    public final int size() {
        return size;
    }

    public final void save(Resume r) {
        if (size > STORAGE_LIMIT) {
            System.out.println("\nStorage overflow");
            return;
        }
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.println("Resume " + r.getUuid() + " already exist");
            return;
        }
        insert(r, index);
        System.out.println(r.getUuid() + " saved");
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (isMissing(index, uuid)) {
            return;
        }
        remove(index);
        System.out.println("\n" + uuid + " deleted");
    }

    public final void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (isMissing(index, r.getUuid())) {
            return;
        }
        storage[index] = r;
        System.out.println("\n" + r.getUuid() + " update successful");
    }

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("\nStorage was cleared");
    }

    protected abstract void insert(Resume r, int index);

    protected abstract void remove(int index);

    protected abstract int getIndex(String uuid);

    private boolean isMissing(int index, String uuid) {
        if (index < 0) {
            System.out.println("\nResume " + uuid + " not exist");
            return true;
        }
        return false;
    }
}