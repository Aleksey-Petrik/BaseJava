package com.urise.webapp.model;

public enum ContactsType {
    TEL_NUMBER("Номер телефона:") {
        @Override
        public String toHtml0(String value) {
            return "<a href='telephone:" + value + "'>" + value + "</a>";
        }
    },
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
    LINKED_IN("Профиль LinkedIn:") {
        @Override
        public String toHtml0(String value) {
            return "<a href='https://www.linkedin.com" + value + "'>" + value + "</a>";
        }
    },
    GITHUB("Профиль GitHub:") {
        @Override
        public String toHtml0(String value) {
            return "<a href='github." + value + "'>" + value + "</a>";
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow:") {
        @Override
        public String toHtml0(String value) {
            return "<a href='https://stackoverflow.com" + value + "'>" + value + "</a>";
        }
    },
    HOME_SITE("Домашняя страница:") {
        @Override
        public String toHtml0(String value) {
            return "<a href='http://gkislin.ru/" + value + "'>" + value + "</a>";
        }
    };

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
