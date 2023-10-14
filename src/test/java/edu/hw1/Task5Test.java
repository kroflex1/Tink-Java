package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task5Test {
    @ParameterizedTest
    @ValueSource(ints = {11211230, 13001120, 23336014, 9182, 11})
    @DisplayName("Палиндром присутствует")
    void findPalindrome(int value) {
        assertTrue(Task5.isPalindromeDescendant(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {5112, 13})
    @DisplayName("Отсутствует палиндром")
    void notFindPalindrome(int value) {
        assertFalse(Task5.isPalindromeDescendant(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {123, 13245, 1932})
    @DisplayName("Нечётное количество чисел")
    void oddAmountOfNumbers(int value) {
        assertThrows(IllegalArgumentException.class, () ->
            Task5.isPalindromeDescendant(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2222, -313})
    @DisplayName("Нечётное количество чисел")
    void negativeNumbers(int value) {
        assertThrows(IllegalArgumentException.class, () ->
            Task5.isPalindromeDescendant(value));
    }
}

