package com.basejava.storage;

import com.basejava.model.Resume;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    
    private final Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void doSave(Resume searchKey, Resume r) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume r) {
        storage.replace((searchKey).getUuid(), r);
    }

    @Override
    protected void doDelete(Resume searchKey) {
        storage.remove((searchKey).getUuid());
    }

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }
}