package edu.hw5.Task3;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FourthDateParser implements DateParser {
    @Override
    public LocalDate parseDate(String date) {
        Pattern pattern = Pattern.compile("^(\\d+) day(s)? ago$");
        Matcher matcher = pattern.matcher(date);
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
