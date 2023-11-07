package edu.hw5.Task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.jetbrains.annotations.Nullable;

class FirstDateParser implements DateParser {

    @Override
    @Nullable
    public LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        LocalDate result;
        try {
            result = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            result = null;
        }
        return result;
    }
}
