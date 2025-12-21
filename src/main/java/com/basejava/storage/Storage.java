package com.basejava.storage;

import java.util.List;

import com.basejava.model.Resume;

public interface Storage {

    List<Resume> getAllSorted();

    Resume get(String uuid);

    int size();

    void save(Resume r);

    void delete(String uuid);

    void update(Resume r);

    void clear();
}