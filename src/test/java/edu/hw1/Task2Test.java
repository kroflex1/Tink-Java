package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {
    @ParameterizedTest
    @CsvSource(value = {
        "123, 3",
        "12, 2",
        "1, 1",
        "0, 1",
        "10, 2",
        "101, 3",
        "-1, 1",
        "-12, 2",
        "-123, 3",
        "-100, 3"
    }, ignoreLeadingAndTrailingWhitespace = true)
    @DisplayName("Подсчёт количества цифр в числе")
    void checkCountDigits(int value, int amountOfDigits) {
            assertEquals(amountOfDigits, Task2.countDigits(value));
    }
}
