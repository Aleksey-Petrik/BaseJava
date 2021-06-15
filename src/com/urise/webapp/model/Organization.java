package com.urise.webapp.model;

public class Organization {
    private Link organization;
    private String monthBegin;
    private String monthEnd;
    private String content;

    public Organization(Link organization, String monthBegin, String monthEnd, String content) {
        this.organization = organization;
        this.monthBegin = monthBegin;
        this.monthEnd = monthEnd;
        this.content = content;
    }

    public Link getOrganization() {
        return organization;
    }

    public String getContent() {
        return content;
    }

    public String getMonthBegin() {
        return monthBegin;
    }

    public String getMonthEnd() {
        return monthEnd;
    }

    /* For lesson 8
    private List<Period> periodList = new ArrayList<>();
    public void addContent(String monthBegin, String monthEnd, String content) {
        periodList.add(new Period(monthBegin, monthEnd, content));
    }
    public Organization(Link organization) {
        this.organization = organization;
    }
    public List<Period> getPeriodContentList() {
        return periodList;
    }
     */
}
