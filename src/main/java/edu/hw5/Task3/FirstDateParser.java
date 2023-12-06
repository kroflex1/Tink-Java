package edu.hw5.Task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.jetbrains.annotations.Nullable;

class FirstDateParser implements DateParser {
    private static final DateTimeFormatter FORMATTER =  DateTimeFormatter.ofPattern("yyyy-MM-d");

    @Override
    @Nullable
    public LocalDate parseDate(String date) {
        LocalDate result;
        try {
            result = LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            result = null;
        }
        return result;
    }
}
