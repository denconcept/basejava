package com.basejava.storage;

import com.basejava.model.Resume;
import java.util.List;

public interface Storage {
    
    List<Resume> getAllSorted();

    Resume get(String uuid);

    int size();

    void save(Resume r);

    void delete(String uuid);

    void update(Resume r);

    void clear();
}