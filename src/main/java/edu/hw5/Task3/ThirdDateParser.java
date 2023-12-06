package edu.hw5.Task3;

import java.time.LocalDate;
import org.jetbrains.annotations.Nullable;

class ThirdDateParser implements DateParser {
    @Override
    @Nullable
    public LocalDate parseDate(String date) {
        if (date.equals("tomorrow")) {
            return LocalDate.now().plusDays(1);
        }
        if (date.equals("today")) {
            return LocalDate.now();
        }
        if (date.equals("yesterday")) {
            return LocalDate.now().minusDays(1);
        }
        return null;
    }
}
