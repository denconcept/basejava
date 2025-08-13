package com.basejava.webapp.storage;

import java.io.IOException;
import java.nio.file.Path;

import com.basejava.webapp.model.Resume;

public class ObjectStreamPathStorage extends AbstractPathStorage {

    private final FileSaveStrategy strategy;

    protected ObjectStreamPathStorage(String directory, FileSaveStrategy strategy) {
        super(directory);
        this.strategy = strategy;
    }

    @Override
    protected Resume doRead(Path searchKey) throws IOException, ClassNotFoundException {
        return strategy.doRead(searchKey);
    }

    @Override
    protected void doWrite(Path searchKey, Resume resume) throws IOException {
        strategy.doWrite(searchKey, resume);
    }
}
