package com.urise.webapp.model;

public enum ContactsType {
    TEL_NUMBER("Номер телефона:"),
    SKYPE("Skype:"),
    EMAIL("Email-почта:"),
    LINKED_IN("Профиль LinkedIn:"),
    GITHUB("Профиль GitHub:"),
    STACKOVERFLOW("Профиль Stackoverflow:"),
    HOME_SITE("Домашняя страница:");

    private String title;

    ContactsType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
