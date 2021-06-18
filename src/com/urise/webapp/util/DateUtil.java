package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date, String mask) {
        return date.format(DateTimeFormatter.ofPattern(mask));
    }
}
