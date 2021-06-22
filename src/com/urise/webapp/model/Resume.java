package com.urise.webapp.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    // Unique identifier
    private final String uuid;
    private final String fullName;

    private Map<ContactsType, String> contacts = new EnumMap<>(ContactsType.class);
    private Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);//autogenerate
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "UUID mast not be null!");
        Objects.requireNonNull(fullName, "fullName mast not be null!");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContact(ContactsType type) {
        return contacts.get(type);
    }

    public Map<ContactsType, String> getContacts() {
        return contacts;
    }

    public void setContacts(Map<ContactsType, String> contacts) {
        this.contacts = contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public void setSections(Map<SectionType, AbstractSection> sections) {
        this.sections = sections;
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    public void addContact(ContactsType type, String contact) {
        contacts.put(type, contact);
    }

    public void addSection(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }

    @Override
    public String toString() {
        return "ID - " + uuid + " Full name - " + fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    @Override
    public int compareTo(Resume o) {
        int compareResult = fullName.compareTo(o.fullName);
        return compareResult == 0 ? uuid.compareTo(o.uuid) : compareResult;
    }

}
