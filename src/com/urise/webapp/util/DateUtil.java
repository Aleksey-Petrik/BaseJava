package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {
    public static final LocalDate _NOW_ = LocalDate.of(3000, 1, 1);
    public static final String MASK_FOR_PRINT_PERIOD = "MM/yyyy";
    public static final String MASK_FOR_READ_PERIOD_HTML = "yyyy-MM";

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate parse(String date, String mask) {
        if ("Сейчас".equals(date) || date.trim().isEmpty()) {
            return _NOW_;
        }
        return LocalDate.parse("01/" + date, DateTimeFormatter.ofPattern("dd/" + mask));
    }

    public static String format(LocalDate date, String mask) {
        if (date == null) {
            return "";
        }
        return LocalDate.now().compareTo(date) > 0 ? date.format(DateTimeFormatter.ofPattern(mask, Locale.ENGLISH)) : "Сейчас";
    }
}
