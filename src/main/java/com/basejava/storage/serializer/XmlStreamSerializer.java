package com.basejava.storage.serializer;

import com.basejava.model.Company;
import com.basejava.model.CompanySection;
import com.basejava.model.Link;
import com.basejava.model.ListSection;
import com.basejava.model.Resume;
import com.basejava.model.TextSection;
import com.basejava.util.XmlParser;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class XmlStreamSerializer implements StreamSerializer {
    
    private final XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(Resume.class,
                Company.class, Company.Period.class, CompanySection.class,
                TextSection.class, ListSection.class, Link.class);
    }

    @Override
    public void doWrite(Path searchKey, Resume resume) throws IOException {
        try (Writer writer = Files.newBufferedWriter(searchKey)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume doRead(Path searchKey) throws IOException {
        try (Reader reader = Files.newBufferedReader(searchKey, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
