package edu.hw5.Task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.jetbrains.annotations.Nullable;

 class SecondDateParser implements DateParser {
    @Override
    @Nullable
    public LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/y");
        LocalDate result;
        try {
            result = LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            result = null;
        }
        return result;
    }
}
