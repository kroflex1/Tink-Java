package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task6Test {
    @ParameterizedTest
    @CsvSource(value = {"3524:3", "6621:5", "6554:4", "1234:3", "1100:4", "7641:1"}, delimiter = ':')
    @DisplayName("Корректный подсчёт количества шагов")
    void countAmountOfSteps(int value, int steps) {
        assertEquals(steps, Task6.findAmountOfStepsToReachKarpekarValue(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {999, -1000, -1001, 2222, 1111, 1, 3524000, 12345})
    @DisplayName("Некорректный формат числа")
    void invalidValueFormat(int value) {
        assertThrows(IllegalArgumentException.class, () ->
            Task6.findAmountOfStepsToReachKarpekarValue(value));
    }
}
