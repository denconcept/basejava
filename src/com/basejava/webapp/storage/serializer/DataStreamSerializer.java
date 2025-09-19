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
            writeContacts(dos, resume);
            writeSections(dos, resume);
        }
    }

    private static void writeContacts(DataOutputStream dos, Resume resume) throws IOException {
        Map<ContactType, String> contacts = resume.getContacts();
        dos.writeInt(contacts.size());
        writeWithException(contacts.entrySet(), entry -> {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        });
    }

    private static void writeSections(DataOutputStream dos, Resume resume) throws IOException {
        Map<SectionType, Section> sections = resume.getSections();
        dos.writeInt(sections.size());
        writeWithException(sections.entrySet(), entry -> {
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

    private static void writeTextSections(DataOutputStream dos, Section section) throws IOException {
        dos.writeUTF(((TextSection) section).getDescription());
    }

    private static void writeListSections(DataOutputStream dos, Section section) throws IOException {
        List<String> items = ((ListSection) section).getDescription();
        dos.writeInt(items.size());
        writeWithException(items, dos::writeUTF);
    }

    private static void writeCompanySections(DataOutputStream dos, Section section) throws IOException {
        List<Company> companies = ((CompanySection) section).getCompanies();
        dos.writeInt(companies.size());
        writeWithException(companies, company -> {
            writeLink(dos, company);
            writePeriods(dos, company);
        });
    }

    private static void writeLink(DataOutputStream dos, Company company) throws IOException {
        Link link = company.getHomePage();
        dos.writeUTF(link.getTitle());
        dos.writeUTF(link.getUrl());
    }

    private static void writePeriods(DataOutputStream dos, Company company) throws IOException {
        List<Company.Period> periods = company.getPeriods();
        dos.writeInt(periods.size());
        writeWithException(periods, period -> {
            dos.writeUTF(period.getStartDate().toString());
            dos.writeUTF(period.getEndDate().toString());
            dos.writeUTF(period.getTitle());
            dos.writeUTF(period.getDescription());
        });
    }

    private static <T> void writeWithException(Collection<T> collection, ConsumerWithException<T> action)
            throws IOException {
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
            readContacts(dis, resume);
            return readSections(dis, resume);
        }
    }

    private static void readContacts(DataInputStream dis, Resume resume) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
    }

    private static Resume readSections(DataInputStream dis, Resume resume) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            String key = dis.readUTF();
            switch (key) {
                case "OBJECTIVE", "PERSONAL" -> readTextSections(dis, resume, key);
                case "ACHIEVEMENT", "QUALIFICATIONS" -> readListSections(dis, resume, key);
                case "EXPERIENCE", "EDUCATION" -> readCompanySections(dis, resume, key);
                default -> throw new IllegalStateException("reading error");
            }
        }
        return resume;
    }

    private static void readTextSections(DataInputStream dis, Resume resume, String key) throws IOException {
        resume.addSection(SectionType.valueOf(key), new TextSection(dis.readUTF()));
    }

    private static void readListSections(DataInputStream dis, Resume resume, String key) throws IOException {
        int size = dis.readInt();
        List<String> items = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            items.add(dis.readUTF());
        }
        resume.addSection(SectionType.valueOf(key), new ListSection(items));
    }

    private static void readCompanySections(DataInputStream dis, Resume resume, String key)
            throws IOException {
        int size = dis.readInt();
        CompanySection companySection = new CompanySection();
        for (int i = 0; i < size; i++) {
            String title = dis.readUTF();
            String url = dis.readUTF();
            Company company = new Company(title, url);
            readPeriods(dis, company);
            companySection.addCompany(company);
            resume.addSection(SectionType.valueOf(key), companySection);
        }
    }

    private static void readPeriods(DataInputStream dis, Company company) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            LocalDate startDate = LocalDate.parse(dis.readUTF());
            LocalDate endDate = LocalDate.parse(dis.readUTF());
            String subTitle = dis.readUTF();
            String description = dis.readUTF();
            company.setPeriod(
                    new Company.Period(startDate, endDate, subTitle, description));
        }
    }
}
