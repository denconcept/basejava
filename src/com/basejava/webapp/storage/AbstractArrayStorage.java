package com.basejava.webapp.storage;

import java.util.Arrays;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
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
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
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
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }
        insert(r, index);
        size++;
        System.out.println(r.getUuid() + " saved");
    }

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        size--;
        fillGap(index);
        storage[size] = null;
        System.out.println("\n" + uuid + " deleted");
    }

    @Override
    public final void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }
        storage[index] = r;
        System.out.println("\n" + r.getUuid() + " update successful");
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("\nStorage was cleared");
    }

    protected abstract void insert(Resume r, int index);

    protected abstract void fillGap(int index);
}