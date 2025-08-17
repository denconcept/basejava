package com.basejava.webapp.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serializer.StreamSerializer;

public class PathStorage extends AbstractStorage<Path> {

    private final Path directory;
    private final StreamSerializer streamSerializer;

    protected PathStorage(String dir, StreamSerializer streamSerializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.streamSerializer = streamSerializer;
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumes = new ArrayList<>();
        for (Path path : getListPaths()) {
            Resume resume = doGet(path);
            resumes.add(resume);
        }
        return resumes;
    }

    @Override
    protected Resume doGet(Path searchKey) {
        try {
            return streamSerializer.doRead(searchKey);
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("Path read error ", getFileName(searchKey), e);
        }
    }

    @Override
    protected void doSave(Path searchKey, Resume resume) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + searchKey,
                    searchKey.getFileName().toString(), e);
        }
        doUpdate(searchKey, resume);
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("Path delete error ", getFileName(searchKey), e);
        }
    }

    @Override
    protected void doUpdate(Path searchKey, Resume resume) {
        try {
            streamSerializer.doWrite(searchKey, resume);
        } catch (IOException e) {
            throw new StorageException("Path write error ", getFileName(searchKey), e);
        }
    }

    @Override
    protected void doClear() {
        getListPaths().forEach(this::doDelete);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(searchKey);
    }

    @Override
    public int size() {
        return getListPaths().size();
    }

    private List<Path> getListPaths() {
        try (Stream<Path> stream = Files.list(directory)) {
            return stream.toList();
        } catch (IOException e) {
            throw new StorageException("Directory read error " + directory.getFileName(), e);
        }
    }

    private String getFileName(Path searchKey) {
        return searchKey.getFileName().toString();
    }
}
