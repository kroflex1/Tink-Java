package edu.hw5.Task3;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DateManager {
    private static final List<DateParser> DATE_PARSERS =
        List.of(new FirstDateParser(), new SecondDateParser(), new ThirdDateParser(), new FourthDateParser());

    public static Optional<LocalDate> parseDate(String date) {
        return DATE_PARSERS.stream()
            .map(parser -> parser.parseDate(date))
            .filter(Objects::nonNull)
            .findFirst();
    }

    private DateManager(){

    }
}
