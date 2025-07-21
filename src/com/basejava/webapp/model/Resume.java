package com.basejava.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;
    public final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);
    public final Map<Contacts, String> contacts = new EnumMap<>(Contacts.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
        sections.put(SectionType.PERSONAL, new TextSection());
        sections.put(SectionType.OBJECTIVE, new TextSection());
        sections.put(SectionType.ACHIEVEMENT, new ListSection());
        sections.put(SectionType.QUALIFICATIONS, new ListSection());
        sections.put(SectionType.EXPERIENCE, new StageSection());
        sections.put(SectionType.EDUCATION, new StageSection());
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }

    public void addContact(Contacts type, String value) {
        contacts.put(type, value);
    }

    public String getContact(Contacts type) {
        return contacts.get(type);
    }
}