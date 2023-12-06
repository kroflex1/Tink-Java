package edu.hw5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {
    static Arguments[] years() {
        return new Arguments[] {
            Arguments.of(1925, List.of("1925-02-13", "1925-03-13", "1925-11-13")),
            Arguments.of(2024, List.of("2024-09-13", "2024-12-13")),
        };
    }

    static Arguments[] dates() {
        return new Arguments[] {
            Arguments.of(LocalDate.of(1925, 1, 1), LocalDate.of(1925, 2, 13)),
            Arguments.of(LocalDate.of(1925, 2, 13), LocalDate.of(1925, 2, 13)),
            Arguments.of(LocalDate.of(2023, 12, 28), LocalDate.of(2024,9,13)),
            Arguments.of(LocalDate.of(2023, 9, 15), LocalDate.of(2023,10,13))
        };
    }

    @ParameterizedTest
    @MethodSource("years")
    void testGetAllFridays13thAtYear(int year, List<String> except) {
        assertEquals(except, Task2.getAllFridays13th(year));
    }

    @ParameterizedTest
    @MethodSource("dates")
    void testGetNextFriday13th(LocalDate startDate, LocalDate except) {
        assertEquals(except, Task2.getNearestNextFriday13th(startDate));
    }

}
