package com.urise.webapp.model;

public class OrganizationLink {
    private String name;
    private String url;

    public OrganizationLink(String title, String url) {
        this.name = title;
        this.url = url;
    }

    public String getTitle() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
