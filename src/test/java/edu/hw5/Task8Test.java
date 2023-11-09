package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task8Test {
    @ParameterizedTest
    @CsvSource(value = {"1", "101", "10001"})
    @DisplayName("Строка соответствует условию: строка из алфавита {0, 1} нечетной длины")
    void testMatchSolve1(String word) {
        assertTrue(Task8.solve1(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"11", "abc101abc", "abc101", "1abc"})
    @DisplayName("Строка НЕ соответствует условию: строка из алфавита {0, 1} нечетной длины")
    void testCantMatchSolve1(String word) {
        assertFalse(Task8.solve1(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"010", "01100", "0", "11", "1101", "101101"})
    @DisplayName(
        "Строка соответствует условию: начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину")
    void testMatchSolve2(String word) {
        assertTrue(Task8.solve2(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"01", "0101", "101", "11100", "0ab", "ab0", "abc1111avc"})
    @DisplayName(
        "Строка НЕ соответствует условию: начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину")
    void testCantMatchSolve2(String word) {
        assertFalse(Task8.solve2(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"000", "101010", "10111111111100", "000111", "010101000", "00011001010010", "1010101010101",
        "1111100011101010"})
    @DisplayName("Строка соответствует условию: количество 0 кратно 3")
    void testMatchSolve3(String word) {
        assertTrue(Task8.solve3(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"010", "111", "100010", "10101010", "00011111110", "abc000", "abc000abc", "00abc"})
    @DisplayName("Строка НЕ соответствует условию: количество 0 кратно 3")
    void testCantMatchSolve3(String word) {
        assertFalse(Task8.solve3(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"101", "10101", "1000000100001", "000", "1", "0", "111111"})
    @DisplayName("Строка соответствует условию: любая строка, кроме 11 или 111")
    void testMatchSolve4(String word) {
        assertTrue(Task8.solve4(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"11", "111", "abc10", "10abc", "abc101abc"})
    @DisplayName("Строка НЕ соответствует условию: любая строка, кроме 11 или 111")
    void testCantMatchSolve4(String word) {
        assertFalse(Task8.solve4(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"10000", "010", "001", "000000100000", "000000001", "100000"})
    @DisplayName("Строка соответствует условию: содержит не менее двух 0 и не более одной 1")
    void testMatchSolve6(String word) {
        assertTrue(Task8.solve6(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"01", "0010000001", "10001", "0000011"})
    @DisplayName("Строка НЕ соответствует условию: содержит не менее двух 0 и не более одной 1")
    void testCantMatchSolve6(String word) {
        assertFalse(Task8.solve6(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "01", "10", "010", "101", "1010101", "0101010", "000010001001", "1010100101", "000000",
        "0"})
    @DisplayName("Строка соответствует условию: нет последовательных 1")
    void testMatchSolve7(String word) {
        assertTrue(Task8.solve7(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"11", "10101011", "00011", "1111000111", "abc101", "abc101abc", "101abc", "11111"})
    @DisplayName("Строка НЕ соответствует условию: нет последовательных 1")
    void testCantMatchSolve7(String word) {
        assertFalse(Task8.solve7(word));
    }

}
