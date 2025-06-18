package com.basejava.webapp.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public class ListStorage extends AbstractStorage {

    ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get(index);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void save(Resume r) {
        if (storage.contains(r)) {
            throw new ExistStorageException(r.getUuid());
        }
        storage.add(r);
        System.out.println(r.getUuid() + " saved");
    }

    @Override
    public void delete(String uuid) {
        Iterator<Resume> iterator = storage.iterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (Objects.equals(r.getUuid(), uuid)) {
                iterator.remove();
                System.out.println("\n" + uuid + " deleted");
                return;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void update(Resume r) {
        int index = storage.indexOf(r);
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }
        storage.set(index, r);
        System.out.println("\n" + r.getUuid() + " update successful");
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }
}