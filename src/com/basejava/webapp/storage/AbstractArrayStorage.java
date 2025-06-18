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
    public int size() {
        return size;
    }

    @Override
    protected Resume extract(int index) {
        return storage[index];
    }

    @Override
    public final void add(Resume r, int index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insert(r, index);
        size++;
    }

    @Override
    public final void remove(int index) {
        size--;
        fillGap(index);
        storage[size] = null;
    }

    @Override
    public final void set(Resume r, int index) {
        storage[index] = r;
    }

    @Override
    public void reset() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected abstract void insert(Resume r, int index);

    protected abstract void fillGap(int index);
}