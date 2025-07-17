package com.basejava.webapp.storage;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage<SearchKeyT> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
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
        SearchKeyT searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void save(Resume r) {
        SearchKeyT searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(searchKey, r);
        System.out.println(r.getUuid() + " saved");
    }

    @Override
    public void delete(String uuid) {
        SearchKeyT searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
        System.out.println("\n" + uuid + " deleted");
    }

    @Override
    public void update(Resume r) {
        SearchKeyT searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
        System.out.println("\n" + r.getUuid() + " update successful");
    }

    @Override
    public void clear() {
        doClear();
        System.out.println("\nStorage was cleared");
    }

    protected abstract List<Resume> doCopyAll();

    protected abstract Resume doGet(SearchKeyT searchKey);

    protected abstract void doSave(SearchKeyT searchKey, Resume r);

    protected abstract void doDelete(SearchKeyT searchKey);

    protected abstract void doUpdate(SearchKeyT searchKey, Resume r);

    protected abstract void doClear();

    protected abstract SearchKeyT getSearchKey(String uuid);

    protected abstract boolean isExist(SearchKeyT searchKey);

    private SearchKeyT getExistingSearchKey(String uuid) {
        SearchKeyT searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SearchKeyT getNotExistingSearchKey(String uuid) {
        SearchKeyT searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}