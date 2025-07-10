package com.basejava.webapp.storage;

import java.util.ArrayList;
import java.util.List;

import com.basejava.webapp.model.Resume;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected void doSave(Object searchKey, Resume r) {
        storage.add(r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((int) searchKey);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        storage.set((Integer) searchKey, r);
    }

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return null;
    }
}