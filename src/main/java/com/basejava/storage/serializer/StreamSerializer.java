package com.basejava.storage.serializer;

import com.basejava.model.Resume;
import java.io.IOException;
import java.nio.file.Path;

public interface StreamSerializer {
    
    void doWrite(Path searchKey, Resume resume) throws IOException;

    Resume doRead(Path searchKey) throws IOException, ClassNotFoundException;
}