package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public abstract Resume[] getAll();

    public abstract Resume get(Resume r);

    public abstract int size();

    public abstract void save(Resume r);

    public abstract void delete(Resume r);

    public abstract void update(Resume r);

    public abstract void clear();

    protected void exist(Resume r, int index) {
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }
    }

    protected void notExist(Resume r, int index) {
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    protected void printMsg(String msg) {
        System.out.println(msg);
    }
}