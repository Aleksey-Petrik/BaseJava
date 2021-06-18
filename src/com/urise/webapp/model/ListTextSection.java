package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListTextSection extends AbstractSection {

    private List<String> contents = new ArrayList<>();

    @Override
    public String getContents() {
        StringBuilder sb = new StringBuilder();
        contents.forEach(content -> sb.append(content).append("\n"));
        return sb.toString();
    }

    public void addContent(String content) {
        contents.add(content);
    }

    public List<String> getListContent() {
        return contents;
    }

    @Override
    public String toString() {
        return contents.toString();
    }
}
