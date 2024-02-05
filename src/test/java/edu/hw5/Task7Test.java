package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task7Test {


    @ParameterizedTest
    @CsvSource(value = {"110", "000", "11011", "110001"})
    @DisplayName("Строка соответствует условию: содержат не менее 3 символов, третий символ равен 0")
    void testMatchSolve1(String word){
        assertTrue(Task7.solve1(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"001", "00", "111111", "10", "ab0", "230", "110345", "abc110abc", "abc110"})
    @DisplayName("Строка НЕ соответствует условию: содержат не менее 3 символов, третий символ равен 0")
    void testCantMatchSolve1(String word){
        assertFalse(Task7.solve1(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"101", "010", "11", "1", "0"})
    @DisplayName("Строка соответствует условию: начинается и заканчивается одним и тем же символом")
    void testMatchSolve2(String word){
        assertTrue(Task7.solve2(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"100", "01", "abc101abc", "a10101a", "a", "abc101"})
    @DisplayName("Строка НЕ соответствует условию: начинается и заканчивается одним и тем же символом")
    void testCantMatchSolve2(String word){
        assertFalse(Task7.solve2(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"1", "10", "101"})
    @DisplayName("Строка соответствует условию: длина не менее 1 и не более 3")
    void testMatchSolve3(String word){
        assertTrue(Task7.solve3(word));
    }

    @ParameterizedTest
    @CsvSource(value = {"1010", "abc101abc", "abc101"})
    @DisplayName("Строка НЕ соответствует условию: длина не менее 1 и не более 3")
    void testCantMatchSolve3(String word){
        assertFalse(Task7.solve3(word));
    }
}
