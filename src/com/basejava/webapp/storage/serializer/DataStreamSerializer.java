package com.basejava.webapp.storage.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.basejava.webapp.model.Company;
import com.basejava.webapp.model.CompanySection;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Link;
import com.basejava.webapp.model.ListSection;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.Section;
import com.basejava.webapp.model.SectionType;
import com.basejava.webapp.model.TextSection;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Path searchKey, Resume resume) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(Files.newOutputStream(searchKey))) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeWithException(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, Section> sections = resume.getSections();
            writeWithException(dos, sections.entrySet(), entry -> {
                SectionType key = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(key.name());
                switch (key) {
                    case OBJECTIVE, PERSONAL -> writeTextSections(dos, section);
                    case ACHIEVEMENT, QUALIFICATIONS -> writeListSections(dos, section);
                    case EXPERIENCE, EDUCATION -> writeCompanySections(dos, section);
                    default -> throw new IllegalStateException("writing error");
                }
            });
        }
    }

    private void writeTextSections(DataOutputStream dos, Section section) throws IOException {
        dos.writeUTF(((TextSection) section).getDescription());
    }

    private void writeListSections(DataOutputStream dos, Section section) throws IOException {
        List<String> items = ((ListSection) section).getDescription();
        writeWithException(dos, items, dos::writeUTF);
    }

    private void writeCompanySections(DataOutputStream dos, Section section) throws IOException {
        List<Company> companies = ((CompanySection) section).getCompanies();
        writeWithException(dos, companies, company -> {
            writeLink(dos, company);
            writePeriods(dos, company);
        });
    }

    private void writeLink(DataOutputStream dos, Company company) throws IOException {
        Link link = company.getHomePage();
        dos.writeUTF(link.getTitle());
        dos.writeUTF(link.getUrl());
    }

    private void writePeriods(DataOutputStream dos, Company company) throws IOException {
        List<Company.Period> periods = company.getPeriods();
        writeWithException(dos, periods, period -> {
            dos.writeUTF(period.getStartDate().toString());
            dos.writeUTF(period.getEndDate().toString());
            dos.writeUTF(period.getTitle());
            dos.writeUTF(period.getDescription());
        });
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection,
                                        ConsumerWithException<T> action) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            action.accept(t);
        }
    }

    @Override
    public Resume doRead(Path searchKey) throws IOException {
        try (DataInputStream dis = new DataInputStream(Files.newInputStream(searchKey))) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readWithException(dis, () ->
                    resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readWithException(dis, () -> {
                String key = dis.readUTF();
                switch (key) {
                    case "OBJECTIVE", "PERSONAL" -> readTextSections(dis, resume, key);
                    case "ACHIEVEMENT", "QUALIFICATIONS" -> readListSections(dis, resume, key);
                    case "EXPERIENCE", "EDUCATION" -> readCompanySections(dis, resume, key);
                    default -> throw new IllegalStateException("reading error");
                }
            });
            return resume;
        }
    }

    private void readTextSections(DataInputStream dis, Resume resume, String key) throws IOException {
        resume.addSection(SectionType.valueOf(key), new TextSection(dis.readUTF()));
    }

    private void readListSections(DataInputStream dis, Resume resume, String key) throws IOException {
        List<String> items = new ArrayList<>();
        readWithException(dis, () -> items.add(dis.readUTF()));
        resume.addSection(SectionType.valueOf(key), new ListSection(items));
    }

    private void readCompanySections(DataInputStream dis, Resume resume, String key)
            throws IOException {
        CompanySection companySection = new CompanySection();
        readWithException(dis, () -> {
            String title = dis.readUTF();
            String url = dis.readUTF();
            Company company = new Company(title, url);
            readPeriods(dis, company);
            companySection.addCompany(company);
            resume.addSection(SectionType.valueOf(key), companySection);
        });
    }

    private void readPeriods(DataInputStream dis, Company company) throws IOException {
        readWithException(dis, () -> {
            LocalDate startDate = LocalDate.parse(dis.readUTF());
            LocalDate endDate = LocalDate.parse(dis.readUTF());
            String subTitle = dis.readUTF();
            String description = dis.readUTF();
            company.setPeriod(
                    new Company.Period(startDate, endDate, subTitle, description));
        });
    }

    private void readWithException(DataInputStream dis,
                                       ConsumerWithExceptionNoArg action) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            action.accept();
        }
    }
}