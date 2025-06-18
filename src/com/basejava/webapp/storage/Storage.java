package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public interface Storage {

    Resume[] getAll();

    Resume get(String uuid);

    int size();

    void save(Resume r);

    void delete(String uuid);

    void update(Resume r);

    void clear();
}