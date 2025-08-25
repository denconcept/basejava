package com.basejava.webapp.storage.serializer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

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
