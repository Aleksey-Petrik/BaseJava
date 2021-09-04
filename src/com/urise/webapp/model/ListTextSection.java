package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListTextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private final List<String> contents = new ArrayList<>();

    public ListTextSection() {
    }

    public void addContent(String content) {
        contents.add(content);
    }

    public List<String> getList() {
        return contents;
    }

    @Override
    public String getContents() {
        StringBuilder sb = new StringBuilder();
        contents.forEach(content -> sb.append(content).append("\n"));
        return sb.toString();
    }

    @Override
    public String getHtmlContents() {
        StringBuilder sb = new StringBuilder();
        contents.forEach(content -> sb.append(content).append("<br>"));
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListTextSection that = (ListTextSection) o;
        return Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contents);
    }

    @Override
    public String toString() {
        return contents.toString();
    }
}
