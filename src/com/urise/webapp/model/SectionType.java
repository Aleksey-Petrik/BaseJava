package com.urise.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public static String getType(SectionType type) {
        String typeSections = "";
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                typeSections = "TEXT";
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                typeSections = "LIST";
                break;
            case EXPERIENCE:
            case EDUCATION:
                typeSections = "MULTI";
        }
        return typeSections;
    }

    public String getTitle() {
        return title;
    }
}
