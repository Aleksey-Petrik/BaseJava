package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.MASK_FOR_PRINT_PERIOD;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListOrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> organizations = new ArrayList<>();

    public ListOrganizationSection() {
    }

    public ListOrganizationSection(List<Organization> organizations){
        this.organizations = organizations;
    }

    public List<Organization> getList() {
        return organizations;
    }

    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    public void addAllOrganizations(List<Organization> organizations){
        this.organizations.addAll(organizations);
    }

    @Override
    public String getContents() {
        StringBuilder sb = new StringBuilder();
        for (Organization org : organizations) {
            sb.append(org.getOrganization().getName())
                    .append("--")
                    .append(org.getOrganization().getUrl());

            org.getPeriods().forEach(period -> sb
                    .append(DateUtil.format(period.getDateBegin(), MASK_FOR_PRINT_PERIOD))
                    .append("-")
                    .append(DateUtil.format(period.getDateEnd(), MASK_FOR_PRINT_PERIOD))
                    .append(period.getContent()));
        }
        return sb.toString();
    }

    @Override
    public String getHtmlContents() {
        StringBuilder sb = new StringBuilder("<ul>");
        for (Organization org : organizations) {
            sb.append("<li>")
                    .append("<a href=")
                    .append(org.getOrganization().getUrl())
                    .append("><h4>")
                    .append(org.getOrganization().getName())
                    .append("</h4></a>");

            sb.append("<ul>");
            org.getPeriods().forEach(period -> sb
                    .append("<li>")
                    .append("<b>")
                    .append(DateUtil.format(period.getDateBegin(), MASK_FOR_PRINT_PERIOD))
                    .append("-").append(DateUtil.format(period.getDateEnd(), MASK_FOR_PRINT_PERIOD))
                    .append("</b>")
                    .append("  ")
                    .append(period.getContent())
                    .append("</li>"));
            sb.append("</ul>")
                    .append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
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
