package com.basejava.webapp.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : getListFiles()) {
            Resume resume;
            resume = doGet(file);
            resumes.add(resume);
        }
        return resumes;
    }

    @Override
    protected Resume doGet(File searchKey) {
        try {
            return doRead(searchKey);
        } catch (IOException e) {
            throw new StorageException("File read error ", searchKey.getName(), e);
        }
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected void doSave(File searchKey, Resume resume) {
        try {
            searchKey.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file ", searchKey.getName(), e);
        }
        doUpdate(searchKey, resume);
    }

    @Override
    protected void doDelete(File searchKey) {
        if (!searchKey.delete()) {
            throw new StorageException("File delete error ", searchKey.getName());
        }
    }

    @Override
    protected void doUpdate(File searchKey, Resume resume) {
        try {
            doWrite(searchKey, resume);
        } catch (IOException e) {
            throw new StorageException("File write error ", searchKey.getName(), e);
        }
    }

    @Override
    protected void doClear() {
        for (File file : getListFiles()) {
            doDelete(file);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File searchKey) {
        return searchKey.exists();
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new IllegalStateException("Directory read error " + directory.getAbsolutePath());
        }
        return list.length;
    }

    protected abstract Resume doRead(File searchKey) throws IOException;

    protected abstract void doWrite(File searchKey, Resume resume) throws IOException;

    private File[] getListFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IllegalStateException("Directory read error " + directory.getAbsolutePath());
        }
        return files;
    }
}
