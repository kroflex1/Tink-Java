package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task5Test {
    @Test
    @DisplayName("Палиндром присутствует")
    void findPalindrome() {
        int[] values = new int[] {11211230, 13001120, 23336014, 9182, 11};
        for (int value : values) {
            assertTrue(Task5.isPalindromeDescendant(value));
        }
    }

    @Test
    @DisplayName("Отсутствует палиндром")
    void notFindPalindrome() {
        int[] values = new int[] {5112, 13};
        for (int value : values) {
            assertFalse(Task5.isPalindromeDescendant(value));
        }
    }

    @Test
    @DisplayName("Нечётное количество чисел")
    void oddAmountOfNumbers() {
        int[] values = new int[] {123, 13245, 1932};
        for (int value : values) {
            assertThrows(IllegalArgumentException.class, () ->
                Task5.isPalindromeDescendant(value));
        }
    }

    @Test
    @DisplayName("Нечётное количество чисел")
    void negativeNumbers() {
        int[] values = new int[] {-1, -2222, -313};
        for (int value : values) {
            assertThrows(IllegalArgumentException.class, () ->
                Task5.isPalindromeDescendant(value));
        }
    }
}
