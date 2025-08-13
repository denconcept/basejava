package com.basejava.webapp.storage;

import java.io.File;
import java.io.IOException;

import com.basejava.webapp.model.Resume;

public class ObjectStreamStorage extends AbstractFileStorage {

    private final FileSaveStrategy strategy;

    protected ObjectStreamStorage(String directory, FileSaveStrategy strategy) {
        super(new File(directory));
        this.strategy = strategy;
    }

    @Override
    protected Resume doRead(File searchKey) throws IOException, ClassNotFoundException {
        return strategy.doRead(searchKey.toPath());
    }

    @Override
    protected void doWrite(File searchKey, Resume resume) throws IOException {
        strategy.doWrite(searchKey.toPath(), resume);
    }
}