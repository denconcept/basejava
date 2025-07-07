package com.basejava.webapp.storage;

import java.util.List;

import com.basejava.webapp.model.Resume;

public interface Storage {

    List<Resume> getAllSorted();

    Resume get(String uuid);

    int size();

    void save(Resume r);

    void delete(String uuid);

    void update(Resume r);

    void clear();
}