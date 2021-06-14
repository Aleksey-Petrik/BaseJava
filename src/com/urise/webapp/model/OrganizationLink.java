package com.urise.webapp.model;

public class OrganizationLink {
    private String name;
    private String url;

    public OrganizationLink(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getTitle() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
