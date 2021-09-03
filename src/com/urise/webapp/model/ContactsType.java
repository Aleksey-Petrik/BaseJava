package com.urise.webapp.model;

public enum ContactsType {
    TEL_NUMBER("Номер телефона:"),
    SKYPE("Skype:") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    EMAIL("Email-почта:") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
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

    public String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return value != null ? toHtml0(value) : "";
    }
}
