package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task6Test {
    @Test
    @DisplayName("Корректный подсчёт количества шагов")
    void countAmountOfSteps() {
        Map<Integer, Integer> answers = new HashMap<>();
        answers.put(3524, 3);
        answers.put(6621, 5);
        answers.put(6554, 4);
        answers.put(1234, 3);
        answers.put(1100, 4);
        answers.put(7641, 1);
        for (Map.Entry<Integer, Integer> pair : answers.entrySet()) {
            int steps = Task6.findAmountOfStepsToReachKapekarValue(pair.getKey());
            assertEquals(pair.getValue().intValue(), steps);
        }
    }

    @Test
    @DisplayName("Некорректный формат числа")
    void invalidValueFormat() {
        int[] answers = new int[] {999, -1000, 2222, 1111, 1};
        for (int value : answers) {
            assertThrows(IllegalArgumentException.class, () ->
                Task6.findAmountOfStepsToReachKapekarValue(value));
        }
    }
}
