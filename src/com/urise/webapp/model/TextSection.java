package com.urise.webapp.model;

import java.io.Serial;

public class TextSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;

    private String content;

    public TextSection(String content) {
        this.content = content;
    }

    public String getContents() {
        return content;
    }
}
