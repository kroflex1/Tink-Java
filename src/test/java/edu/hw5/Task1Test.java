package edu.hw5;

import edu.hw2.Task2.Rectangle;
import edu.hw2.Task2.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {
    static Arguments[] intervals() {
        List<String> firstIntervals =
            List.of(
                convertTimesToString(
                    LocalDateTime.of(2022, 3, 12, 20, 0),
                    LocalDateTime.of(2022, 3, 12, 20, 0).plusMinutes(2)
                ));
        String firstIntervalExcept = "2м";

        List<String> secondIntervals = List.of(
            convertTimesToString(
                LocalDateTime.of(2022, 3, 12, 20, 0),
                LocalDateTime.of(2022, 3, 12, 20, 0).plusHours(1)
            ));
        String secondIntervalExcept = "1ч";

        List<String> thirdIntervals = List.of(
            convertTimesToString(
                LocalDateTime.of(2022, 3, 12, 20, 0),
                LocalDateTime.of(2022, 3, 12, 20, 0).plusDays(4)
            ));
        String thirdIntervalExcept = "4д";

        List<String> fourthIntervals = List.of(
            convertTimesToString(
                LocalDateTime.of(2022, 3, 12, 20, 0),
                LocalDateTime.of(2022, 3, 12, 20, 0).plusHours(1).plusMinutes(15)
            ));
        String fourthIntervalExcept = "1ч 15м";

        List<String> fifthIntervals = List.of(
            convertTimesToString(
                LocalDateTime.of(2022, 3, 12, 20, 20),
                LocalDateTime.of(2022, 3, 12, 20, 20).plusHours(3).plusMinutes(30)
            ),
            convertTimesToString(
                LocalDateTime.of(2022, 4, 1, 21, 30),
                LocalDateTime.of(2022, 4, 1, 21, 30).plusHours(3).plusMinutes(50)
            )
        );
        String fifthIntervalExcept = "3ч 40м";

        return new Arguments[] {
            Arguments.of(firstIntervals, firstIntervalExcept),
            Arguments.of(secondIntervals, secondIntervalExcept),
            Arguments.of(thirdIntervals, thirdIntervalExcept),
            Arguments.of(fourthIntervals, fourthIntervalExcept),
            Arguments.of(fifthIntervals, fifthIntervalExcept)
        };
    }

    static Arguments[] invalidIntervals() {
        return new Arguments[] {
            Arguments.of(List.of("2022:03:12, 20:20 - 2022 03 12, 23 50")),
            Arguments.of(List.of("abcd2022:03:12, 20:20 - 2022 03 12, 23 50abcd")),
            Arguments.of(List.of("2022-03-12, 19:20")),
            Arguments.of(List.of("2022-03-12, 19:20 - 2022-03-12, 22:50 - 2022-03-12, 23:40")),
            Arguments.of(List.of("2099-01-01, 01:00 - 2000-01-01, 01:00"))

        };
    }

    @ParameterizedTest
    @MethodSource("intervals")
    @DisplayName("Нахождение среднего времени")
    void testGetAverageTime(List<String> intervals, String except) {
        assertEquals(except, Task1.getAverageTime(intervals));
    }

    @ParameterizedTest
    @MethodSource("invalidIntervals")
    @DisplayName("Неверный формат времени")
    void testInvalidIntervals(List<String> intervals) {
        assertThrows(IllegalArgumentException.class, () ->
            Task1.getAverageTime(intervals));
    }

    static String convertTimesToString(LocalDateTime start, LocalDateTime end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
        return start.format(formatter) + " - " + end.format(formatter);
    }
}
