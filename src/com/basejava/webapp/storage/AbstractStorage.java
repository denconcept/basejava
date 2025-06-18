package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public abstract Resume[] getAll();

    public abstract Resume get(String uuid);

    public abstract int size();

    public abstract void save(Resume r);

    public abstract void delete(String uuid);

    public abstract void update(Resume r);

    public abstract void clear();

    protected abstract int getIndex(String uuid);
}