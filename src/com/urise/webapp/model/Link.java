package com.urise.webapp.model;

import java.util.Objects;

public class Link {
    private String name;
    private String url;

    public Link(String name, String url) {
        Objects.requireNonNull(name, "name org mast not be null!");
        Objects.requireNonNull(url, "url org mast not be null!");
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!name.equals(link.name)) return false;
        return url.equals(link.url);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name +
                " " +
                url + "\n";
    }
}
