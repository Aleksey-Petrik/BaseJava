package com.urise.webapp.model;

public class Period {
    private String monthBegin;
    private String monthEnd;
    private String content;

    public Period(String monthBegin, String monthEnd, String content) {
        this.monthBegin = monthBegin;
        this.monthEnd = monthEnd;
        this.content = content;
    }

    public String getMonthBegin() {
        return monthBegin;
    }

    public String getMonthEnd() {
        return monthEnd;
    }

    public String getContent() {
        return content;
    }
}
