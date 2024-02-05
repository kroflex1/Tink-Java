package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Task1 {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");

    public static String getAverageTime(List<String> timeIntervals) throws IllegalArgumentException {
        if (timeIntervals.isEmpty()) {
            throw new IllegalArgumentException("The list of time intervals cannot be empty");
        }
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
            LocalDateTime start;
            LocalDateTime end;
            try {
                start = LocalDateTime.parse(times[0], FORMATTER);
                end = LocalDateTime.parse(times[1], FORMATTER);
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
        long days = TimeUnit.DAYS.convert(seconds, TimeUnit.SECONDS);
        long hours = TimeUnit.HOURS.convert(seconds, TimeUnit.SECONDS) - TimeUnit.HOURS.convert(days, TimeUnit.DAYS);
        long minutes =
            TimeUnit.MINUTES.convert(seconds, TimeUnit.SECONDS) - TimeUnit.MINUTES.convert(hours, TimeUnit.HOURS)
                - TimeUnit.MINUTES.convert(days, TimeUnit.DAYS);
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
