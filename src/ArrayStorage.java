import java.util.Arrays;

/**
 * Array based storage for Resumes.
 */
public class ArrayStorage {

    private static final int STORAGE_SIZE = 10000;
    private final Resume[] storage = new Resume[STORAGE_SIZE];
    private int resumeCount = 0;

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    public Resume get(String uuid) {
        for (Resume resume : getAll()) {
            if (uuid.equals(resume.toString())) {
                return resume;
            }
        }
        return null;
    }

    public void save(Resume r) {
        storage[resumeCount++] = r;
    }

    public void delete(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (uuid.equals(storage[i].toString())) {
                resumeCount--;
                if (i != resumeCount) {
                    System.arraycopy(storage, i + 1, storage, i, resumeCount - i);
                }
                storage[resumeCount] = null;
                break;
            }
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, resumeCount, null);
        resumeCount = 0;
    }

    public int size() {
        return STORAGE_SIZE - resumeCount;
    }
}