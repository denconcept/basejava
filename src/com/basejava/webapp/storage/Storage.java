package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public interface Storage {

    Resume[] getAll();

    Resume get(Resume r);

    int size();

    void save(Resume r);

    void delete(Resume r);

    void update(Resume r);

    void clear();
}