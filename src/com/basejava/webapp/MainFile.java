package com.basejava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "/Users/denis/Java/BaseJava/basejava/.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File dir = new File("src/com/basejava/webapp");
        System.out.println(dir.isDirectory());
        for (String name : Objects.requireNonNull(dir.list())) {
            System.out.println(name);
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // HW_8
        System.out.println("\n");
        File root = new File("/Users/denis/Java/BaseJava/basejava");
        walk(root);
    }

    public static void walk(File file) {
        if (file.isDirectory()) {
            System.out.println("Directory: " + file.getName());
            for (File childFile : Objects.requireNonNull(file.listFiles())) {
                walk(childFile);
            }
        } else {
            System.out.println("File: " + file.getName());
        }
    }
}