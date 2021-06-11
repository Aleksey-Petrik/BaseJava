package com.urise.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private Map<ContactsType, Url> contacts = new EnumMap<>(ContactsType.class);
    private Map<SectionType, AbstractSection> contents = new EnumMap<>(SectionType.class);

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

    public Map<ContactsType, Url> getContacts() {
        return contacts;
    }

    public void setContacts(Map<ContactsType, Url> contacts) {
        this.contacts = contacts;
    }

    public Map<SectionType, AbstractSection> getContents() {
        return contents;
    }

    public void setContents(Map<SectionType, AbstractSection> contents) {
        this.contents = contents;
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
