package com.basejava.webapp.storage.serializer;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.util.JsonParser;

public class JsonStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Path searchKey, Resume resume) throws IOException {
        try (Writer writer = Files.newBufferedWriter(searchKey)) {
            JsonParser.write(resume, writer);
        }
    }

    @Override
    public Resume doRead(Path searchKey) throws IOException {
        try (Reader reader = Files.newBufferedReader(searchKey, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}

