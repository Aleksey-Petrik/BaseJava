package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.MASK_FOR_PRINT_PERIOD;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListOrganizationSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<Organization> organizations = new ArrayList<>();

    public ListOrganizationSection() {
    }

    public List<Organization> getList() {
        return organizations;
    }

    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    @Override
    public String getContents(String separator) {
        StringBuilder sb = new StringBuilder();
        for (Organization org : organizations) {
            sb.append(separator)
                    .append(org.getOrganization().getName())
                    .append("--")
                    .append(org.getOrganization().getUrl());

            org.getPeriods().forEach(period -> sb.append("--")
                    .append(DateUtil.format(period.getDateBegin(), MASK_FOR_PRINT_PERIOD))
                    .append("-")
                    .append(DateUtil.format(period.getDateEnd(), MASK_FOR_PRINT_PERIOD))
                    .append("--")
                    .append(period.getContent()));
        }
        return sb.toString();
    }

    @Override
    public String getContents() {
        return getContents("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListOrganizationSection that = (ListOrganizationSection) o;
        return Objects.equals(organizations, that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }

    @Override
    public String toString() {
        return "ListOrganizationSection{" +
                "organizations=" + organizations +
                '}';
    }
}
