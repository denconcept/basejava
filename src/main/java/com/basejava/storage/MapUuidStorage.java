package com.basejava.storage;

import com.basejava.model.Resume;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    
    private final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void doSave(String searchKey, Resume r) {
        storage.put(searchKey, r);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void doUpdate(String searchKey, Resume r) {
        storage.replace(searchKey, r);
    }

    @Override
    protected void doDelete(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey(searchKey);
    }
}