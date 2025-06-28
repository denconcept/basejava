package com.basejava.webapp.storage;

import java.util.Arrays;

import com.basejava.webapp.model.Resume;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(int index, Resume r) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        storage[insertionIndex] = r;
    }

    @Override
    protected void fillGap(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}