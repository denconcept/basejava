package com.basejava.webapp.storage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.basejava.webapp.model.Resume;

public class MapUuidStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void doSave(Object searchKey, Resume r) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        storage.replace((String) searchKey, r);
    }

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }
}