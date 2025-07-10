package com.basejava.webapp.storage;

import java.util.Comparator;
import java.util.List;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = doCopyAll();
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(searchKey, r);
        System.out.println(r.getUuid() + " saved");
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
        System.out.println("\n" + uuid + " deleted");
    }

    @Override
    public void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
        System.out.println("\n" + r.getUuid() + " update successful");
    }

    @Override
    public void clear() {
        doClear();
        System.out.println("\nStorage was cleared");
    }

    protected abstract List<Resume> doCopyAll();

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doSave(Object searchKey, Resume r);

    protected abstract void doDelete(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume r);

    protected abstract void doClear();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}