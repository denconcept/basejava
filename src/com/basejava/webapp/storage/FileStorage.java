package com.basejava.webapp.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serializer.StreamSerializer;

public class FileStorage extends AbstractStorage<File> {

    private final File directory;
    private final StreamSerializer streamSerializer;

    protected FileStorage(File directory, StreamSerializer streamSerializer) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.streamSerializer = streamSerializer;
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : getListFiles()) {
            Resume resume = doGet(file);
            resumes.add(resume);
        }
        return resumes;
    }

    @Override
    protected Resume doGet(File searchKey) {
        try {
            return streamSerializer.doRead(searchKey.toPath());
        } catch (IOException | ClassNotFoundException e) {
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
            streamSerializer.doWrite(searchKey.toPath(), resume);
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
        return getListFiles().length;
    }

    private File[] getListFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new IllegalStateException("Directory read error " + directory.getAbsolutePath());
        }
        return files;
    }
}
