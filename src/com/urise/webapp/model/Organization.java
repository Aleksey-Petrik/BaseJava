package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.MASK_FOR_PRINT_PERIOD;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link link;
    private final List<Period> periods = new ArrayList<>();

    public Organization() {
    }

    public Organization(Link link) {
        Objects.requireNonNull(link, "organization org mast not be null!");
        this.link = link;
    }

    public Link getOrganization() {
        return link;
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
        return Objects.equals(link, that.link) &&
                Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, periods);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(link.toString()).append("\n");
        periods.forEach(period -> sb.append(DateUtil.format(period.getDateBegin(), MASK_FOR_PRINT_PERIOD)).
                append("-").
                append(DateUtil.format(period.getDateEnd(), MASK_FOR_PRINT_PERIOD)).
                append("\n").
                append(period.getContent())
        );
        return sb.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateBegin;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateEnd;
        private String content;

        public Period() {
        }

        public Period(LocalDate dateBegin, LocalDate dateEnd, String content) {
            Objects.requireNonNull(dateBegin, "dateBegin org mast not be null!");
            Objects.requireNonNull(dateEnd, "dateEnd org mast not be null!");
            Objects.requireNonNull(content, "content org mast not be null!");
            this.dateBegin = dateBegin;
            this.dateEnd = dateEnd;
            this.content = content;
        }

        public LocalDate getDateBegin() {
            return dateBegin;
        }

        public LocalDate getDateEnd() {
            return dateEnd;
        }

        public String getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return Objects.equals(dateBegin, period.dateBegin) && Objects.equals(dateEnd, period.dateEnd) && Objects.equals(content, period.content);
        }

        @Override
        public int hashCode() {
            return Objects.hash(dateBegin, dateEnd, content);
        }

        @Override
        public String toString() {
            return DateUtil.format(dateBegin, MASK_FOR_PRINT_PERIOD) +
                    "-" +
                    DateUtil.format(dateEnd, MASK_FOR_PRINT_PERIOD) +
                    " " +
                    content;
        }
    }
}
