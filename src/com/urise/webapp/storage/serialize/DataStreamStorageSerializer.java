package com.urise.webapp.storage.serialize;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class DataStreamStorageSerializer implements StreamSerialize {

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream ois = new DataInputStream(is)) {
            //PersonalData
            String uuid = ois.readUTF();
            String fullName = ois.readUTF();
            Resume resume = new Resume(uuid, fullName);
            //Contacts
            int sizeSection = ois.readInt();
            for (int i = 0; i < sizeSection; i++) {
                resume.addContact(ContactsType.valueOf(ois.readUTF()), ois.readUTF());
            }
            sizeSection = ois.readInt();
            for (int i = 0; i < sizeSection; i++) {
                AbstractSection sectionObject = null;

                SectionType sectionType = SectionType.valueOf(ois.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> sectionObject = new TextSection(ois.readUTF());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListTextSection listTextSection = new ListTextSection();
                        int size = ois.readInt();
                        for (int j = 0; j < size; j++) {
                            listTextSection.addContent(ois.readUTF());
                        }
                        sectionObject = listTextSection;
                    }
                    case EXPERIENCE, EDUCATION -> {
                        ListOrganizationSection organizations = new ListOrganizationSection();
                        int size = ois.readInt();
                        for (int k = 0; k < size; k++) {
                            Organization organization = new Organization(new Link(ois.readUTF(), ois.readUTF()));
                            int sizePeriods = ois.readInt();
                            for (int j = 0; j < sizePeriods; j++) {
                                organization.addPeriod(LocalDate.parse(ois.readUTF()),
                                        LocalDate.parse(ois.readUTF()), ois.readUTF());
                            }
                            organizations.addOrganization(organization);
                        }
                        sectionObject = organizations;
                    }
                }
                resume.addSection(sectionType, sectionObject);
            }
            return resume;
        }
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            //PersonalData
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            //Contacts
            Map<ContactsType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            r.getContacts().forEach((k, v) -> {
                try {
                    dos.writeUTF(k.toString());
                    dos.writeUTF(v);
                } catch (IOException e) {
                    throw new StorageException(null, "DataStream error write file-resume", e);
                }
            });
            //Sections
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            sections.forEach((k, v) -> {
                try {
                    dos.writeUTF(k.name());
                    switch (k) {
                        case PERSONAL, OBJECTIVE -> dos.writeUTF(v.getContents());
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            List<String> contentList = ((ListTextSection) v).getList();
                            dos.writeInt(contentList.size());
                            contentList.forEach(content -> {
                                try {
                                    dos.writeUTF(content);
                                } catch (IOException e) {
                                    throw new StorageException(null, "DataStream error write file-resume", e);
                                }
                            });
                        }
                        case EDUCATION, EXPERIENCE -> {
                            List<Organization> organizationList = ((ListOrganizationSection) v).getList();
                            dos.writeInt(organizationList.size());
                            organizationList.forEach(org -> {
                                try {
                                    dos.writeUTF(org.getOrganization().getName());
                                    dos.writeUTF(org.getOrganization().getUrl());
                                    List<Organization.Period> periods = org.getPeriods();
                                    dos.writeInt(periods.size());
                                    periods.forEach(period -> {
                                        try {
                                            dos.writeUTF(period.getDateBegin().toString());
                                            dos.writeUTF(period.getDateEnd().toString());
                                            dos.writeUTF(period.getContent());
                                        } catch (IOException e) {
                                            throw new StorageException(null, "DataStream error write file-resume", e);
                                        }
                                    });
                                } catch (IOException e) {
                                    throw new StorageException(null, "DataStream error write file-resume", e);
                                }
                            });
                        }
                    }
                } catch (IOException e) {
                    throw new StorageException(null, "DataStream error write file-resume", e);
                }
            });
        }
    }
}
