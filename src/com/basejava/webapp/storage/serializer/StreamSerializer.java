package com.basejava.webapp.storage.serializer;

import java.io.IOException;
import java.nio.file.Path;

import com.basejava.webapp.model.Resume;

public interface StreamSerializer {

    void doWrite(Path searchKey, Resume resume) throws IOException;

    Resume doRead(Path searchKey) throws IOException, ClassNotFoundException;
}