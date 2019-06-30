package ru.restaurant.voting.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeUtil() {
    }

    public static LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    public static LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }

    public static LocalDateTime parseLocalDateTime(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDateTime.parse(str);
    }
}
