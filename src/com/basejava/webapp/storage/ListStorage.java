package com.basejava.webapp.storage;

import java.util.ArrayList;

import com.basejava.webapp.model.Resume;

public class ListStorage extends AbstractStorage {

    ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    public Resume get(Resume r) {
        int index = storage.indexOf(r);
        notExist(r, index);
        return storage.get(index);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void save(Resume r) {
        int index = storage.indexOf(r);
        exist(r, index);
        storage.addLast(r);
        printMsg(r.getUuid() + " saved");
    }

    @Override
    public void delete(Resume r) {
        int index = storage.indexOf(r);
        notExist(r, index);
        storage.remove(r);
        printMsg("\n" + r.getUuid() + " deleted");
    }

    @Override
    public void update(Resume r) {
        int index = storage.indexOf(r);
        notExist(r, index);
        storage.set(index, r);
        printMsg("\n" + r.getUuid() + " update successful");
    }

    @Override
    public void clear() {
        storage.clear();
    }
}