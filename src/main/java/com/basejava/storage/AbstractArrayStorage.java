package com.basejava.storage;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    
    public static final int STORAGE_LIMIT = 10000;
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
    protected final void doSave(Integer searchKey, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insert(searchKey, resume);
        size++;
    }

    @Override
    protected final void doDelete(Integer searchKey) {
        size--;
        fillGap(searchKey);
        storage[size] = null;
    }

    @Override
    protected final void doUpdate(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
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

    protected abstract void insert(int index, Resume resume);

    protected abstract void fillGap(int index);
}