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
            Resume resume = doRead(file);
            resumes.add(resume);
        }
        return resumes;
    }

    @Override
    protected Resume doGet(File searchKey) {
        return doRead(searchKey);
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected void doSave(File searchKey, Resume resume) {
        try {
            searchKey.createNewFile();
            doWrite(searchKey, resume);
        } catch (IOException e) {
            throw new StorageException("IO error", searchKey.getName(), e);
        }
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected void doDelete(File searchKey) {
        searchKey.delete();
    }

    @Override
    protected void doUpdate(File searchKey, Resume resume) {
        try {
            doWrite(searchKey, resume);
        } catch (IOException e) {
            throw new StorageException("IO error", searchKey.getName(), e);
        }
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected void doClear() {
        for (File file : getListFiles()) {
            file.delete();
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
        return getListFiles().length;
    }

    protected abstract Resume doRead(File searchKey);

    protected abstract void doWrite(File searchKey, Resume resume) throws IOException;

    private File[] getListFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IllegalStateException("Cannot read directory: " + directory.getAbsolutePath());
        }
        return files;
    }
}
