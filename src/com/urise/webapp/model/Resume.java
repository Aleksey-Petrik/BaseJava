package com.urise.webapp.model;

import java.util.Comparator;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);//autogenerate
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
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

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public int compareTo(Resume o) {
        int compareResult = fullName.compareTo(o.fullName);
        return compareResult == 0 ? uuid.compareTo(o.uuid) : compareResult;
    }

    public static class Comparators {
        public static Comparator<Resume> FULL_NAMES = Resume::compareTo;

        public static Comparator<Resume> UUID = Comparator.comparing(Resume::getUuid);
    }

}
