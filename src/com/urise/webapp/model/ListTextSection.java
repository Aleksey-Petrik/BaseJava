package com.urise.webapp.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class ListTextSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<String> contents = new ArrayList<>();

    public void addContent(String content) {
        contents.add(content);
    }

    public List<String> getListContent() {
        return contents;
    }

    @Override
    public String getContents() {
        StringBuilder sb = new StringBuilder();
        contents.forEach(content -> sb.append(content).append("\n"));
        return sb.toString();
    }

    @Override
    public String toString() {
        return contents.toString();
    }
}
