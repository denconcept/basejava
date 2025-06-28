package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return extract(searchKey);
    }

    public void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        add(searchKey, r);
        System.out.println(r.getUuid() + " saved");
    }

    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        remove(searchKey);
        System.out.println("\n" + uuid + " deleted");
    }

    public void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        set(searchKey, r);
        System.out.println("\n" + r.getUuid() + " update successful");
    }

    public void clear() {
        reset();
        System.out.println("\nStorage was cleared");
    }

    protected abstract Resume extract(Object searchKey);

    protected abstract void add(Object searchKey, Resume r);

    protected abstract void remove(Object searchKey);

    protected abstract void set(Object searchKey, Resume r);

    protected abstract void reset();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        if (searchKey == null) {
            searchKey = uuid;
        }
        return searchKey;
    }
}