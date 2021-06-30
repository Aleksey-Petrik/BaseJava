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

    public static String getType(SectionType type){
        return switch (type) {
            case PERSONAL, OBJECTIVE -> "TEXT";
            case ACHIEVEMENT, QUALIFICATIONS -> "LIST";
            case EXPERIENCE, EDUCATION -> "MULTI";
        };
    }

    public String getTitle() {
        return title;
    }
}
