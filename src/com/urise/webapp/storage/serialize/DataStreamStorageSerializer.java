package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamStorageSerializer implements StreamSerialize {

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            //PersonalData
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            //Contacts
            reader(dis, () -> resume.addContact(ContactsType.valueOf(dis.readUTF()), dis.readUTF()));
            //Sections
            reader(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                AbstractSection sectionObject = null;
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        sectionObject = new TextSection(dis.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListTextSection listTextSection = new ListTextSection();
                        reader(dis, () -> listTextSection.addContent(dis.readUTF()));
                        sectionObject = listTextSection;
                        break;
                    case EXPERIENCE:
                    case EDUCATION: {
                        ListOrganizationSection organizations = new ListOrganizationSection();
                        reader(dis, () -> {
                            Organization organization = new Organization(new Link(dis.readUTF(), dis.readUTF()));
                            reader(dis, () -> organization.addPeriod(LocalDate.parse(dis.readUTF()),
                                    LocalDate.parse(dis.readUTF()), dis.readUTF()));
                            organizations.addOrganization(organization);
                        });
                        sectionObject = organizations;
                    }
                }
                resume.addSection(sectionType, sectionObject);
            });
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
            writeCollections(dos, contacts.entrySet(), contact -> {
                dos.writeUTF(contact.getKey().name());
                dos.writeUTF(contact.getValue());
            });
            //Sections
            Map<SectionType, AbstractSection> sections = r.getSections();
            writeCollections(dos, sections.entrySet(), section -> {
                SectionType sectionType = section.getKey();
                dos.writeUTF(sectionType.name());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(section.getValue().getContents());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollections(dos, ((ListTextSection) section.getValue()).getList(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollections(dos, ((ListOrganizationSection) section.getValue()).getList(), org -> {
                            Link organization = org.getOrganization();
                            dos.writeUTF(organization.getName());
                            dos.writeUTF(organization.getUrl());

                            writeCollections(dos, org.getPeriods(), period -> {
                                dos.writeUTF(period.getDateBegin().toString());
                                dos.writeUTF(period.getDateEnd().toString());
                                dos.writeUTF(period.getContent());
                            });
                        });
                }
            });
        }
    }

    @FunctionalInterface
    private interface Readable {
        void reader() throws IOException;
    }

    @FunctionalInterface
    private interface Writable<T> {
        void write(T t) throws IOException;
    }

    private void reader(DataInputStream dis, Readable reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.reader();
        }
    }

    private <T> void writeCollections(DataOutputStream dos, Collection<T> collection, Writable<T> writable) throws IOException {
        dos.writeInt(collection.size());
        for (T elem : collection) {
            writable.write(elem);
        }
    }
}
