package com.basejava.webapp.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public List<Resume> doCopyAll() {
        Resume[] resumes = Arrays.copyOf(storage, size);
        return new ArrayList<>(Arrays.asList(resumes));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    @Override
    protected final void doSave(Object searchKey, Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insert((Integer) searchKey, r);
        size++;
    }

    @Override
    protected final void doDelete(Object searchKey) {
        size--;
        fillGap((Integer) searchKey);
        storage[size] = null;
    }

    @Override
    protected final void doUpdate(Object searchKey, Resume r) {
        storage[(Integer) searchKey] = r;
    }

    @Override
    protected void doClear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected abstract void insert(int index, Resume r);

    protected abstract void fillGap(int index);
}