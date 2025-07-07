package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.MapStorage;
import com.basejava.webapp.storage.Storage;

public class MainTestArrayStorage {

    private static final Storage ARRAY_STORAGE = new MapStorage();

    public static void main(String[] args) {
        final Resume r1 = new Resume("uuid1", "Ann Ann");
        final Resume r2 = new Resume("uuid2", "Brian Brian");
        final Resume r3 = new Resume("uuid3", "Carter Carter");
        final Resume r4 = new Resume("uuid4", "Duke Duke");
        final Resume r5 = new Resume("uuid5", "Erik Erik");

        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r5);

        System.out.println("\nGet r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.update(r3);
        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}