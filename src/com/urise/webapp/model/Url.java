package com.urise.webapp.model;

public class Url {
    private final String TITLE;
    private final String URL;

    public Url(String title, String url) {
        this.TITLE = title;
        this.URL = url;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getURL() {
        return URL;
    }
}
