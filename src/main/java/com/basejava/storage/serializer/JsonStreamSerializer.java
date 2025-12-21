package com.basejava.storage.serializer;

import com.basejava.model.Resume;
import com.basejava.util.JsonParser;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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

