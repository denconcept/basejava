package com.basejava.storage;

import com.basejava.model.Resume;
import java.util.List;

public interface Storage {
    
    void save(Resume r);
    
    Resume get(String uuid);
    
    List<Resume> getAllSorted();
    
    int size();

    void update(Resume r);

    void delete(String uuid);

    void clear();
}