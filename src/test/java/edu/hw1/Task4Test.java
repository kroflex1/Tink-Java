package edu.hw1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task4Test {
    @ParameterizedTest
    @CsvSource(value = {"123456:214365", "hTsii  s aimex dpus rtni.g:This is a mixed up string.", "badce:abcde"}, delimiter = ':')
    @DisplayName("Исправление строк")
    void fix(String word, String fixedWord) {
        Assertions.assertEquals(fixedWord, Task4.fixString(word));
    }
}
