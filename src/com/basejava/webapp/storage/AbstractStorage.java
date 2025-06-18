package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return extract(index);
    }

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }
        add(r, index);
        System.out.println(r.getUuid() + " saved");
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        remove(index);
        System.out.println("\n" + uuid + " deleted");
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }
        set(r, index);
        System.out.println("\n" + r.getUuid() + " update successful");
    }

    public void clear() {
        reset();
        System.out.println("\nStorage was cleared");
    }

    protected abstract Resume extract(int index);

    protected abstract void add(Resume r, int index);

    protected abstract void remove(int index);

    protected abstract void set(Resume r, int index);

    protected abstract void reset();

    protected abstract int getIndex(String uuid);
}