package com.basejava.webapp.storage;

import java.util.LinkedHashMap;
import java.util.Map;

import com.basejava.webapp.model.Resume;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume extract(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void add(Object searchKey, Resume r) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected void remove(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected void set(Object searchKey, Resume r) {
        storage.replace((String) searchKey, r);
    }

    @Override
    protected void reset() {
        storage.clear();
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        if (storage.containsKey(uuid)) {
            return uuid;
        }
        return null;
    }
}