package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate _NOW_ = LocalDate.of(3000, 1, 1);
    public static final String MASK_FOR_PRINT_PERIOD = "MM/YYYY";

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date, String mask) {
        return LocalDate.now().compareTo(date) > 0 ? date.format(DateTimeFormatter.ofPattern(mask)) : "Сейчас";
    }
}
