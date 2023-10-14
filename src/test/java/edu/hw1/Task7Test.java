package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task7Test {
    @ParameterizedTest
    @CsvSource(value = {"8:0001", "13:1011", "16:00001", "17:00011", "0:0", "1:1"}, delimiter = ':')
    @DisplayName("Сдвиг влево на 1 цикл")
    void checkOneShiftLeft(int value, String shiftedBinaryValue) {
        assertEquals(Integer.parseInt(shiftedBinaryValue, 2), Task7.rotateLeft(value, 1));
    }

    @ParameterizedTest
    @CsvSource(value = {"8:0100", "13:1110", "16:01000", "17:11000", "0:0", "1:1"}, delimiter = ':')
    @DisplayName("Сдвиг вправо на 1 цикл")
    void checkOneShiftRight(int value, String shiftedBinaryValue) {
        assertEquals(Integer.parseInt(shiftedBinaryValue, 2), Task7.rotateRight(value, 1));
    }

    @ParameterizedTest
    @CsvSource(value = {"8:2:0010", "13:3:1110", "16:3:00100", "17:2:00110", "0:16:0", "1:4:1"}, delimiter = ':')
    @DisplayName("Сдвиг влево на n циклов")
    void checkNShiftLeft(int value, int shift, String shiftedBinaryValue) {
        assertEquals(Integer.parseInt(shiftedBinaryValue, 2), Task7.rotateLeft(value, shift));
    }

    @ParameterizedTest
    @CsvSource(value = {"8:3:0001", "13:2:0111", "16:4:00001", "17:4:00011", "0:16:0", "1:4:1"}, delimiter = ':')
    @DisplayName("Сдвиг вправо на n циклов")
    void checkNShiftRight(int value, int shift, String shiftedBinaryValue) {
        assertEquals(Integer.parseInt(shiftedBinaryValue, 2), Task7.rotateRight(value, shift));
    }

    @ParameterizedTest
    @CsvSource(value = {"-10:1", "10:-1", "-10:-1",}, delimiter = ':')
    @DisplayName("Вызов ошибки при передаче некорректных аргументов")
    void checkInvalidArguments(int value, int shift) {
        assertThrows(IllegalArgumentException.class, () ->
            Task7.rotateLeft(value, shift));
        assertThrows(IllegalArgumentException.class, () ->
            Task7.rotateRight(value, shift));
    }
}
