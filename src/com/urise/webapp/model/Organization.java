package com.urise.webapp.model;

import com.urise.webapp.util.DateUtil;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.MASK_FOR_PRINT_PERIOD;

public class Organization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Link link;
    private List<Period> periods = new ArrayList<>();

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

        if (!link.equals(that.link)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = link.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
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

    public static class Period implements Serializable {
        private final LocalDate dateBegin;
        private final LocalDate dateEnd;
        private final String content;

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
        public String toString() {
            return DateUtil.format(dateBegin, MASK_FOR_PRINT_PERIOD) +
                    "-" +
                    DateUtil.format(dateEnd, MASK_FOR_PRINT_PERIOD) +
                    " " +
                    content;
        }
    }
}
