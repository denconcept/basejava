package com.basejava.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate parse(String dateStr) {
        if (dateStr.equals("Сейчас") || dateStr.isEmpty()) {
            return NOW;
        }
        return YearMonth.parse(dateStr, FORMATTER).atDay(1);
    }

    public static String format(LocalDate date) {
        if (date == null) return "";
        return date.equals(NOW) ? "Сейчас" : date.format(FORMATTER);
    }

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}
