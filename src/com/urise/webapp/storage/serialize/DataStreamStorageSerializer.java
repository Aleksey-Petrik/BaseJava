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
            int size = ois.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactsType.valueOf(ois.readUTF()), ois.readUTF());
            }

            while (ois.available() > 0) {
                AbstractSection sectionObject = null;

                SectionType sectionType = SectionType.valueOf(ois.readUTF());
                size = ois.readInt();

                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> sectionObject = new TextSection(ois.readUTF());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListTextSection listTextSection = new ListTextSection();
                        for (int i = 0; i < size; i++) {
                            listTextSection.addContent(ois.readUTF());
                        }
                        sectionObject = listTextSection;
                    }
                    case EXPERIENCE, EDUCATION -> {
                        ListOrganizationSection organizations = new ListOrganizationSection();
                        for (int i = 0; i < size; i++) {
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

            r.getSections().forEach((k, v) -> {
                try {
                    dos.writeUTF(k.toString());
                    List<?> sectionList = v.getList();
                    dos.writeInt(sectionList.size());
                    sectionList.forEach(content -> {
                        try {
                            SectionType sectionType = SectionType.valueOf(k.toString());
                            if (sectionType == SectionType.EDUCATION ||
                                    sectionType == SectionType.EXPERIENCE) {
                                Organization organization = (Organization) content;

                                dos.writeUTF(organization.getOrganization().getName());
                                dos.writeUTF(organization.getOrganization().getUrl());

                                List<Organization.Period> periods = organization.getPeriods();

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
                            } else {
                                dos.writeUTF(content.toString());
                            }
                        } catch (IOException e) {
                            throw new StorageException(null, "DataStream error write file-resume", e);
                        }
                    });
                } catch (IOException e) {
                    throw new StorageException(null, "DataStream error wrute file-resume", e);
                }
            });
        }
    }
}
