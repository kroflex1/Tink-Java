package edu.hw5;

import edu.hw5.Task3.DateManager;
import java.time.LocalDate;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task3Test {
    static Arguments[] dates() {
        return new Arguments[] {
            Arguments.of("2020-10-10", LocalDate.of(2020,10,10)),
            Arguments.of("2020-12-2", LocalDate.of(2020,12,2)),
            Arguments.of("1/3/1976", LocalDate.of(1976,3,1)),
            Arguments.of("1/3/20", LocalDate.of(20,3,1)),
            Arguments.of("tomorrow", LocalDate.now().plusDays(1)),
            Arguments.of("today", LocalDate.now()),
            Arguments.of("yesterday", LocalDate.now().minusDays(1)),
            Arguments.of("1 day ago", LocalDate.now().minusDays(1)),
            Arguments.of("2234 days ago", LocalDate.now().minusDays(2234)),
        };
    }

    static Arguments[] invalidDates() {
        return new Arguments[] {
            Arguments.of("aaa2020-10-10aaa"),
            Arguments.of("2020-1-10"),
            Arguments.of("1976/1/3"),
            Arguments.of("11tomorrow11"),
            Arguments.of("11today11"),
            Arguments.of("11yesterday11"),
            Arguments.of("-1 day ago"),
            Arguments.of("-2234 days ago"),
            Arguments.of("aaaa2234 days agoaaaa"),
        };
    }

    @ParameterizedTest
    @MethodSource("dates")
    void testParseDate(String date, LocalDate except) {
        assertEquals(except, DateManager.parseDate(date).get());
    }

    @ParameterizedTest
    @MethodSource("invalidDates")
    void testInvalidDateFormat(String date) {
        assertTrue(DateManager.parseDate(date).isEmpty());
    }
}
