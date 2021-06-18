package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class ListOrganizationSection extends AbstractSection {
    private List<Organization> organizations = new ArrayList<>();

    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    @Override
    public String getContents() {
        StringBuilder sb = new StringBuilder();
        for (Organization org : organizations) {
            sb.append(org.getOrganization().getName()).append(" ").
                    append(org.getOrganization().getUrl()).
                    append("\n");

            org.getPeriods().forEach(period -> sb.append(DateUtil.format(period.getMonthBegin(), "MM/YYYY")).
                    append("-").
                    append(DateUtil.format(period.getMonthEnd(), "MM/YYYY")).
                    append(" ").
                    append(period.getContent()).
                    append("\n"));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}
