package edu.hw3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task4Test {
    @ParameterizedTest
    @CsvSource(value = {
        "1, I",
        "2, II",
        "3, III",
        "4, IV",
        "5, V",
        "6, VI",
        "7, VII",
        "8, VIII",
        "9, IX",
        "10, X",
        "11, XI",
        "12, XII",
        "14, XIV",
        "15, XV",
        "16, XVI",
        "17, XVII",
        "19, XIX",
        "50, L",
        "100, C",
        "500, D",
        "1000, M",
        "23, XXIII",
        "38, XXXVIII",
        "47, XLVII",
        "99, XCIX",
        "149, CXLIX",
        "1938, MCMXXXVIII",
        "3999, MMMCMXCIX"}, ignoreLeadingAndTrailingWhitespace = true)
    void testConvertToRoman(int arabicValue, String romanValue) {
        assertEquals(romanValue, Task4.convertToRoman(arabicValue));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 4000})
    void testInvalidArgument(int value){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Task4.convertToRoman(value);
        });
        assertEquals("The number must be in the range from 1 to 3999", exception.getMessage());
    }
}
