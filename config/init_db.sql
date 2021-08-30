drop table IF EXISTS section;
drop table IF EXISTS contact;
drop table IF EXISTS resume;

create TABLE resume (uuid CHAR(36) NOT NULL PRIMARY KEY,
                      full_name TEXT NOT NULL);

create TABLE contact (id    SERIAL NOT NULL PRIMARY KEY,
                      type  TEXT   NOT NULL,
                      value TEXT   NOT NULL,
                      resume_uuid CHAR(36) NOT NULL REFERENCES resume(uuid) ON delete CASCADE);

CREATE TABLE section (id    SERIAL NOT NULL PRIMARY KEY,
                      type  TEXT NOT NULL,
                      value TEXT NOT NULL,
                      resume_uuid CHAR(36) NOT NULL REFERENCES resume(uuid) ON DELETE CASCADE);

create UNIQUE INDEX contact_uuid_type_index ON contact (resume_uuid, type);

create UNIQUE INDEX section_uuid_type_index ON section (resume_uuid, type);