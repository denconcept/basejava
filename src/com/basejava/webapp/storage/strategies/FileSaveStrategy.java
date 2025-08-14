package com.basejava.webapp.storage.strategies;

import java.io.IOException;
import java.nio.file.Path;

import com.basejava.webapp.model.Resume;

public interface FileSaveStrategy {

    Resume doRead(Path searchKey) throws IOException, ClassNotFoundException;

    void doWrite(Path searchKey, Resume resume) throws IOException;
}