package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {
    @Test
    @DisplayName("Подсчёт количества цифр в числе")
    void checkCountDigits() {
        Map<Integer, Integer> values = new HashMap<Integer, Integer>();
        values.put(123, 3);
        values.put(12, 2);
        values.put(1, 1);
        values.put(0, 1);
        values.put(10, 2);
        values.put(101, 3);
        values.put(100, 3);
        values.put(-1, 1);
        values.put(-12, 2);
        values.put(-123, 3);
        values.put(-100, 3);
        for (Map.Entry<Integer, Integer> pair : values.entrySet()) {
            int amountOfDigit = Task2.countDigits(pair.getKey());
            assertEquals(pair.getValue(), amountOfDigit);
        }
    }
}
