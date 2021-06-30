package com.urise.webapp.storage.serialize;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;

public class DataStreamStorageSerializer implements StreamSerialize {
    private final String PREFIX_SECTION = "SECTION";
    private final String SEPARATOR = "::";

    private void write(String line, DataOutputStream dos) {
        try {
            dos.writeUTF(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isSection(String line) {
        return PREFIX_SECTION.equals(line);
    }

    private boolean checkSectionType(String line) {
        for (SectionType section : SectionType.values()) {
            if (section.toString().equals(line)) {
                return true;
            }
        }
        return false;
    }

    private String[] split(String line, String separator) {
        return line.split(separator);
    }

    @Override
    public Resume doRead(InputStream is) {
        try (DataInputStream ois = new DataInputStream(is)) {

            Resume resume = null;
            String sectionName = null;
            ListOrganizationSection organizations;

            while (ois.available() > 0) {
                String[] lines = split(ois.readUTF(), SEPARATOR);

                if (isSection(lines[0])) {
                    sectionName = lines[1];
                    continue;
                }

                if (sectionName.equals("PersonalData")) {
                    resume = new Resume(lines[1], lines[2]);
                    continue;
                }

                if (resume != null) {
                    if (sectionName.equals("Contacts")) {
                        resume.addContact(ContactsType.valueOf(lines[0]), lines[1]);
                        continue;
                    }

                    if (checkSectionType(sectionName)) {
                        SectionType sectionType = SectionType.valueOf(sectionName);

                        if ("TEXT".equals(SectionType.getType(sectionType))) {
                            resume.addSection(sectionType, new TextSection(lines[0]));
                            continue;
                        }

                        if ("LIST".equals(SectionType.getType(sectionType))) {
                            ListTextSection listTextSection = new ListTextSection();
                            for (String line : lines) {
                                if (!line.isEmpty()) {
                                    listTextSection.addContent(line);
                                }
                            }
                            resume.addSection(sectionType, listTextSection);
                            continue;
                        }

                        if ("MULTI".equals(SectionType.getType(sectionType))) {
                            organizations = new ListOrganizationSection();
                            for (String line : lines) {
                                if (!line.isEmpty()) {
                                    String[] lineData = split(line, "--");
                                    Organization organization = new Organization(new Link(lineData[0], lineData[1]));
                                    for (int i = 2; i < lineData.length; i += 2) {
                                        String[] period = split(lineData[i], "-");
                                        organization.addPeriod(
                                                DateUtil.parse(period[0]),
                                                DateUtil.parse(period[1]),
                                                lineData[i + 1]);
                                    }
                                    organizations.addOrganization(organization);
                                }
                            }
                            resume.addSection(sectionType, organizations);
                        }
                    }
                }
            }
            return resume;
        } catch (IOException e) {
            throw new StorageException(null, "DataStream error read file-resume", e);
        }
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            write(PREFIX_SECTION + SEPARATOR + "PersonalData", dos);
            write(SEPARATOR + r.getUuid() + SEPARATOR + r.getFullName(), dos);

            write(PREFIX_SECTION + SEPARATOR + "Contacts", dos);
            r.getContacts().forEach((k, v) -> write(k.toString() + SEPARATOR + v, dos));

            r.getSections().forEach((k, v) -> {
                write(PREFIX_SECTION + SEPARATOR + k.toString(), dos);
                write(v.getContents(SEPARATOR), dos);
            });
        }
    }
}
