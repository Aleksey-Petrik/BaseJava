package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private LocalDate monthBegin;
    private LocalDate monthEnd;
    private String content;

    public Period(LocalDate monthBegin, LocalDate monthEnd, String content) {
        Objects.requireNonNull(monthBegin, "monthBegin org mast not be null!");
        Objects.requireNonNull(monthEnd, "monthEnd org mast not be null!");
        Objects.requireNonNull(content, "content org mast not be null!");

        this.monthBegin = monthBegin;
        this.monthEnd = monthEnd;
        this.content = content;
    }

    public LocalDate getMonthBegin() {
        return monthBegin;
    }

    public LocalDate getMonthEnd() {
        return monthEnd;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period = (Period) o;

        if (!monthBegin.equals(period.monthBegin)) return false;
        if (!monthEnd.equals(period.monthEnd)) return false;
        return content.equals(period.content);
    }

    @Override
    public int hashCode() {
        int result = monthBegin.hashCode();
        result = 31 * result + monthEnd.hashCode();
        result = 31 * result + content.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return DateUtil.format(monthBegin, "MM/YYYY") +
                "-" +
                DateUtil.format(monthEnd, "MM/YYYY") +
                " " +
                content;
    }
}
