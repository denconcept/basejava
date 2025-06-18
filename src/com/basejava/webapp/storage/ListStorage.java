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
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume extract(int index) {
        return storage.get(index);
    }

    @Override
    public void add(Resume r, int index) {
        storage.add(r);
    }

    @Override
    public void remove(int index) {
        storage.remove(index);
    }

    @Override
    public void set(Resume r, int index) {
        storage.set(index, r);
    }

    @Override
    public void reset() {
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