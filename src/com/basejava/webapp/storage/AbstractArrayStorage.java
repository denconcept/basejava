package com.basejava.webapp.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

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
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected final void doSave(Integer searchKey, Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insert(searchKey, r);
        size++;
    }

    @Override
    protected final void doDelete(Integer searchKey) {
        size--;
        fillGap(searchKey);
        storage[size] = null;
    }

    @Override
    protected final void doUpdate(Integer searchKey, Resume r) {
        storage[searchKey] = r;
    }

    @Override
    protected void doClear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract void insert(int index, Resume r);

    protected abstract void fillGap(int index);
}