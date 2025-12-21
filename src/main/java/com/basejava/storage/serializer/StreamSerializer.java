package com.basejava.storage.serializer;

import java.io.IOException;
import java.nio.file.Path;

import com.basejava.model.Resume;

public interface StreamSerializer {

    void doWrite(Path searchKey, Resume resume) throws IOException;

    Resume doRead(Path searchKey) throws IOException, ClassNotFoundException;
}