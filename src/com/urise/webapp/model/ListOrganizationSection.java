package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListOrganizationSection extends AbstractSection {
    private List<Organization> organizations = new ArrayList<>();

    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    @Override
    public String getContent() {
        StringBuilder sb = new StringBuilder();

        organizations.forEach(org -> sb.append(org.getOrganization().getTitle())
                .append(" ")
                .append(org.getOrganization().getUrl())
                .append("\n")
                .append(org.getMonthBegin())
                .append("-")
                .append(org.getMonthEnd())
                .append(" ")
                .append(org.getContent())
                .append("\n")
        );
        return sb.toString();
    }

    /* For Lesson 8
    @Override
    public String getContent() {
        StringBuilder sb = new StringBuilder();
        for (Organization org : organizations) {
            sb.append(org.getOrganization().getTitle()).append(" ").
                    append(org.getOrganization().getUrl()).
                    append("\n");

            org.getPeriodContentList().forEach(period -> sb.append(period.getMonthBegin()).
                    append("-").
                    append(period.getMonthEnd()).
                    append(" ").
                    append(period.getContent()).
                    append("\n"));
        }
        return sb.toString();
    }
     */
}
