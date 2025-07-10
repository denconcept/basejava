package com.basejava.webapp.storage;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.basejava.webapp.model.Resume;

public class MapResumeStorage extends AbstractStorage {

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
        return (Resume) searchKey;
    }

    @Override
    protected void doSave(Object searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        storage.replace(((Resume) searchKey).getUuid(), r);
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
        return storage.get(uuid);
    }
}