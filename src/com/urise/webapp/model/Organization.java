package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private OrganizationLink organization;
    private List<Period> periodList = new ArrayList<>();

    public Organization(OrganizationLink organization) {
        this.organization = organization;
    }

    public OrganizationLink getOrganization() {
        return organization;
    }

    public List<Period> getPeriodContentList() {
        return periodList;
    }

    public void addContent(String monthBegin, String monthEnd, String content) {
        periodList.add(new Period(monthBegin, monthEnd, content));
    }
}
