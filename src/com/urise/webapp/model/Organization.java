package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private Url organization;
    private List<PeriodContent> periodContentList = new ArrayList<>();

    public Organization(Url organization) {
        this.organization = organization;
    }

    public Url getOrganization() {
        return organization;
    }

    public List<PeriodContent> getPeriodContentList() {
        return periodContentList;
    }

    public void addContent(String monthBegin, String monthEnd, String content) {
        periodContentList.add(new PeriodContent(monthBegin, monthEnd, content));
    }
}
