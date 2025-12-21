package com.basejava.storage.serializer;

import com.basejava.exception.StorageException;
import com.basejava.model.Resume;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ObjectStreamSerializer implements StreamSerializer {
    
    @Override
    public void doWrite(Path searchKey, Resume resume) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(searchKey))) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume doRead(Path searchKey) {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(searchKey))) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new StorageException("Error read resume", e);
        }
    }
}
