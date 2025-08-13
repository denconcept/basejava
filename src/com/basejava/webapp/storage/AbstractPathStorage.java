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

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
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
            return doRead(searchKey);
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("Path read error ", searchKey.getFileName().toString(), e);
        }
    }

    @Override
    protected void doSave(Path searchKey, Resume resume) {
        try {
            Files.createFile(searchKey);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file ", searchKey.getFileName().toString(), e);
        }
        doUpdate(searchKey, resume);
    }

    @Override
    protected void doDelete(Path searchKey) {
        try {
            Files.delete(searchKey);
        } catch (IOException e) {
            throw new StorageException("Path delete error ", searchKey.getFileName().toString());
        }
    }

    @Override
    protected void doUpdate(Path searchKey, Resume resume) {
        try {
            doWrite(searchKey, resume);
        } catch (IOException e) {
            throw new StorageException("Path write error ", searchKey.getFileName().toString(), e);
        }
    }

    @Override
    protected void doClear() {
        try (Stream<Path> stream = Files.list(directory)) {
            stream.forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null, e);
        }
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
        int count;
        try (Stream<Path> stream = Files.list(directory)) {
            count = (int) stream.count();
        } catch (IOException e) {
            throw new StorageException("Path size error", null, e);
        }
        return count;
    }

    protected abstract Resume doRead(Path searchKey) throws IOException, ClassNotFoundException;

    protected abstract void doWrite(Path searchKey, Resume resume) throws IOException;

    private Path[] getListPaths() {
        Path[] paths;
        try (Stream<Path> stream = Files.list(directory)) {
            paths = stream.toArray(Path[]::new);
        } catch (IOException e) {
            throw new IllegalStateException("Directory read error " + directory.getFileName());
        }
        return paths;
    }
}
