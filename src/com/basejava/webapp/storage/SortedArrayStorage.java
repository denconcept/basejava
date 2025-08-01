package com.basejava.webapp.storage;

import java.util.Arrays;
import java.util.Comparator;

import com.basejava.webapp.model.Resume;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected void insert(int index, Resume resume) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        storage[insertionIndex] = resume;
    }

    @Override
    protected void fillGap(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "Name1");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    }
}