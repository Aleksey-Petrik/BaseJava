package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private Link organization;
    List<Period> periods = new ArrayList<>();

    public Organization(Link organization) {
        Objects.requireNonNull(organization, "organization org mast not be null!");
        this.organization = organization;
    }

    public Link getOrganization() {
        return organization;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void addPeriod(LocalDate monthBegin, LocalDate monthEnd, String content) {
        periods.add(new Period(monthBegin, monthEnd, content));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!organization.equals(that.organization)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = organization.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(organization.toString()).append("\n");
        periods.forEach(period -> sb.append(DateUtil.format(period.getMonthBegin(), "MM/YYYY")).
                append("-").
                append(DateUtil.format(period.getMonthEnd(), "MM/YYYY")).
                append("\n").
                append(period.getContent())
        );
        return sb.toString();
    }
}
