package edu.hw1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {
    @ParameterizedTest
    @CsvSource(value = {
        "10:25, 625",
        "10:01, 601",
        "00:01, 1",
        "01:00, 60",
        "00:00, 0",
        "100:25, 6025",
        "999:59, 59999",
    }, ignoreLeadingAndTrailingWhitespace = true)
    @DisplayName("Перевод корректного формата времени в секунды")
    void convertCorrectTimeToSeconds(String time, int seconds) {
        Assertions.assertEquals(seconds, Task1.minutesToSeconds(time));
    }

    @ParameterizedTest
    @ValueSource(strings = {"10:-25", "-10:25", "-10:-25", "00:-01", "-01:00", "-00:-00"})
    @DisplayName("Отрицательные числа в формате времени")
    void convertNegativeTimeToSeconds(String time) {
        assertEquals(-1, Task1.minutesToSeconds(time));
    }

    @ParameterizedTest
    @ValueSource(strings = {"01:60", "+01:00", "+00:+01", "25", "00:00:12", ":15", "15:", ":15:", "ABCD", "ab:cd",
        "ab:01", "01:ab"})
    @DisplayName("Неверный формат времени")
    void convertInvalidTimeToSeconds(String time) {
        assertEquals(-1, Task1.minutesToSeconds(time));
    }
}
