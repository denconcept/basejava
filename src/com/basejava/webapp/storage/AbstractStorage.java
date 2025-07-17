package com.basejava.webapp.storage;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage<SKT> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumes = doCopyAll();
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SKT searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        SKT searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(searchKey, r);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SKT searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        SKT searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }

    @Override
    public void clear() {
        doClear();
        System.out.println("\nStorage was cleared");
    }

    protected abstract List<Resume> doCopyAll();

    protected abstract Resume doGet(SKT searchKey);

    protected abstract void doSave(SKT searchKey, Resume r);

    protected abstract void doDelete(SKT searchKey);

    protected abstract void doUpdate(SKT searchKey, Resume r);

    protected abstract void doClear();

    protected abstract SKT getSearchKey(String uuid);

    protected abstract boolean isExist(SKT searchKey);

    private SKT getExistingSearchKey(String uuid) {
        SKT searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SKT getNotExistingSearchKey(String uuid) {
        SKT searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}