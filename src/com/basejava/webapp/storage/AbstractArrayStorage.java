package com.basejava.webapp.storage;

import java.util.Arrays;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public Resume get(Resume r) {
        int index = getIndex(r.getUuid());
        notExist(r, index);
        return storage[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public final void save(Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        int index = getIndex(r.getUuid());
        exist(r, index);
        insert(r, index);
        size++;
        printMsg(r.getUuid() + " saved");
    }

    @Override
    public final void delete(Resume r) {
        int index = getIndex(r.getUuid());
        notExist(r, index);
        size--;
        fillGap(index);
        storage[size] = null;
        printMsg("\n" + r.getUuid() + " deleted");
    }

    @Override
    public final void update(Resume r) {
        int index = getIndex(r.getUuid());
        notExist(r, index);
        storage[index] = r;
        printMsg("\n" + r.getUuid() + " update successful");
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        printMsg("\nStorage was cleared");
    }

    protected abstract void insert(Resume r, int index);

    protected abstract void fillGap(int index);

    protected abstract int getIndex(String uuid);
}