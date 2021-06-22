package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.util.DateUtil.MASK_FOR_PRINT_PERIOD;

public class ListOrganizationSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;

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

            org.getPeriods().forEach(period -> sb.append(DateUtil.format(period.getDateBegin(), MASK_FOR_PRINT_PERIOD)).
                    append("-").
                    append(DateUtil.format(period.getDateEnd(), MASK_FOR_PRINT_PERIOD)).
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
