package edu.hw5.Task3;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FourthDateParser implements DateParser {
    private static final Pattern DATE_PATTERN = Pattern.compile("^(\\d+) day(s)? ago$");

    @Override
    public LocalDate parseDate(String date) {
        Matcher matcher = DATE_PATTERN.matcher(date);
        if (matcher.matches()) {
            long days = Long.parseLong(matcher.group(1));
            if (days <= 0) {
                return null;
            }
            return LocalDate.now().minusDays(days);
        }
        return null;
    }
}
