package com.basejava.webapp.storage.serializer;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.basejava.webapp.model.Company;
import com.basejava.webapp.model.CompanySection;
import com.basejava.webapp.model.Link;
import com.basejava.webapp.model.ListSection;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.TextSection;
import com.basejava.webapp.util.XmlParser;

public class XmlStreamSerializer implements StreamSerializer {

    private final XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(Resume.class,
                Company.class, Company.Period.class, CompanySection.class,
                TextSection.class, ListSection.class, Link.class);
    }

    @Override
    public Resume doRead(Path searchKey) throws IOException {
        try (Reader reader = Files.newBufferedReader(searchKey, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }

    @Override
    public void doWrite(Path searchKey, Resume resume) throws IOException {
        try (Writer writer = Files.newBufferedWriter(searchKey)) {
            xmlParser.marshall(resume, writer);
        }
    }
}
