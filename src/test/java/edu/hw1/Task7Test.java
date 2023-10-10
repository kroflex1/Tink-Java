package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task7Test {
    @Test
    @DisplayName("Сдвиг влево на 1 цикл")
    void checkOneShiftLeft() {
        Map<Integer, String> answers = new HashMap<>();
        answers.put(8, "0001");
        answers.put(13, "1011");
        answers.put(16, "00001");
        answers.put(17, "00011");
        answers.put(0, "0");
        answers.put(1, "1");
        for (Map.Entry<Integer, String> pair : answers.entrySet()) {
            assertEquals(Integer.parseInt(pair.getValue(), 2), Task7.rotateLeft(pair.getKey(), 1));
        }
    }

    @Test
    @DisplayName("Сдвиг вправо на 1 цикл")
    void checkOneShiftRight() {
        Map<Integer, String> answers = new HashMap<>();
        answers.put(8, "0100");
        answers.put(13, "1110");
        answers.put(16, "01000");
        answers.put(17, "11000");
        answers.put(0, "0");
        answers.put(1, "1");
        for (Map.Entry<Integer, String> pair : answers.entrySet()) {
            assertEquals(Integer.parseInt(pair.getValue(), 2), Task7.rotateRight(pair.getKey(), 1));
        }
    }

    @Test
    @DisplayName("Сдвиг влево на n циклов")
    void checkNShiftLeft() {
        Map<int[], String> answers = new HashMap<>();
        answers.put(new int[] {8, 2}, "0010");
        answers.put(new int[] {13, 3}, "1110");
        answers.put(new int[] {16, 3}, "00100");
        answers.put(new int[] {17, 2}, "00110");
        answers.put(new int[] {0, 16}, "0");
        answers.put(new int[] {1, 4}, "1");
        for (Map.Entry<int[], String> pair : answers.entrySet()) {
            assertEquals(Integer.parseInt(pair.getValue(), 2), Task7.rotateLeft(pair.getKey()[0], pair.getKey()[1]));
        }
    }

    @Test
    @DisplayName("Сдвиг вправо на n циклов")
    void checkNShiftRight() {
        Map<int[], String> answers = new HashMap<>();
        answers.put(new int[] {8, 3}, "0001");
        answers.put(new int[] {13, 2}, "0111");
        answers.put(new int[] {16, 4}, "00001");
        answers.put(new int[] {17, 4}, "00011");
        answers.put(new int[] {0, 16}, "0");
        answers.put(new int[] {1, 4}, "1");
        for (Map.Entry<int[], String> pair : answers.entrySet()) {
            assertEquals(Integer.parseInt(pair.getValue(), 2), Task7.rotateRight(pair.getKey()[0], pair.getKey()[1]));
        }
    }

    @Test
    @DisplayName("Вызов ошибки при передаче некорректных аргументов")
    void checkInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () ->
            Task7.rotateLeft(-10, 1));
        assertThrows(IllegalArgumentException.class, () ->
            Task7.rotateLeft(10, -1));
        assertThrows(IllegalArgumentException.class, () ->
            Task7.rotateLeft(-10, -1));
        assertThrows(IllegalArgumentException.class, () ->
            Task7.rotateRight(-10, 1));
        assertThrows(IllegalArgumentException.class, () ->
            Task7.rotateRight(10, -1));
        assertThrows(IllegalArgumentException.class, () ->
            Task7.rotateRight(-10, -1));
    }
}
