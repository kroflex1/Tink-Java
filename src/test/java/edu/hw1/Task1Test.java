package edu.hw1;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Task1Test {
    @Test
    @DisplayName("Перевод корректного формата времени в секунды")
    void convertCorrectTimeToSeconds() {
        Map<String, Integer> times = new HashMap<String, Integer>();
        times.put("10:25", 10 * 60 + 25);
        times.put("10:01", 10 * 60 + 1);
        times.put("00:01", 1);
        times.put("01:00", 60);
        times.put("00:00", 0);
        times.put("100:25", 100 * 60 + 25);
        times.put("999:59", 999 * 60 + 59);
        for (Map.Entry<String, Integer> pair : times.entrySet()) {
            int seconds = Task1.minutesToSeconds(pair.getKey());
            assertEquals(pair.getValue().intValue(), seconds);
        }
    }

    @Test
    @DisplayName("Отрицательные числа в формате времени")
    void convertNegativeTimeToSeconds() {
        Map<String, Integer> times = new HashMap<String, Integer>();
        times.put("10:-25", -1);
        times.put("-10:25", -1);
        times.put("-10:-25", -1);
        times.put("00:-01", -1);
        times.put("-01:00", -1);
        times.put("-00:-00", -1);
        for (Map.Entry<String, Integer> pair : times.entrySet()) {
            int seconds = Task1.minutesToSeconds(pair.getKey());
            assertEquals(pair.getValue().intValue(), seconds);
        }
    }

    @Test
    @DisplayName("Неверный формат времени")
    void convertInvalidTimeToSeconds() {
        Map<String, Integer> times = new HashMap<String, Integer>();
        times.put("01:60", -1);
        times.put("+01:00", -1);
        times.put("+00:+01", -1);
        times.put("25", -1);
        times.put("00:00:12", -1);
        times.put(":15", -1);
        times.put("15:", -1);
        times.put(":15:", -1);
        times.put("ABCD", -1);
        times.put("ab:cd", -1);
        times.put("ab:01", -1);
        times.put("01:ab", -1);
        for (Map.Entry<String, Integer> pair : times.entrySet()) {
            int seconds = Task1.minutesToSeconds(pair.getKey());
            assertEquals(pair.getValue().intValue(), seconds);
        }
    }
}
