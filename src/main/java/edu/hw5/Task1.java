package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Task1 {
    public static String getAverageTime(List<String> timeIntervals) throws IllegalArgumentException {
        long sumOfSeconds = 0;
        for (String currentTimeInterval : timeIntervals) {
            String[] times = currentTimeInterval.strip().split(" - ");
            if (times.length != 2) {
                throw new IllegalArgumentException(String.format(
                    "%s contains %d time, but should have 2",
                    currentTimeInterval,
                    times.length
                ));
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
            LocalDateTime start;
            LocalDateTime end;
            try {
                start = LocalDateTime.parse(times[0], formatter);
                end = LocalDateTime.parse(times[1], formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid time format");
            }
            if (end.isBefore(start)) {
                throw new IllegalArgumentException("Invalid time interval format");
            }
            sumOfSeconds += Duration.between(start, end).getSeconds();
        }
        long averageSeconds = sumOfSeconds / timeIntervals.size();
        return convertSecondsToDuration(averageSeconds);
    }

    @SuppressWarnings("MagicNumber")
    private static String convertSecondsToDuration(long seconds) {
        long days = seconds / (24 * 60 * 60);
        long hours = seconds / (60 * 60) - 24 * days;
        long minutes = seconds / 60 - hours * 60 - days * 24 * 60;
        List<String> result = new ArrayList<>();
        if (days != 0) {
            result.add(days + "д");
        }
        if (hours != 0) {
            result.add(hours + "ч");
        }
        if (minutes != 0) {
            result.add(minutes + "м");
        }
        return String.join(" ", result);
    }

    private Task1() {

    }
}
