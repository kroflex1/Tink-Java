package edu.hw5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task6Test {
    static Arguments[] words() {
        return new Arguments[] {
            Arguments.of("achfdbaabgabcaabg", "abc", true),
            Arguments.of("abc", "abc", true),
            Arguments.of("123abc123", "abc", true),
            Arguments.of("123a12bc3123c123a", "abcca", true),
            Arguments.of("price: $100", "$100", true),
            Arguments.of("some [A-Z]{4,9} text", "[A-Z]{4,9}", true),
            Arguments.of("some te\\d{9}", "\\d{9}", true),
            Arguments.of("\\d{100}", "\\d{100}", true),
            Arguments.of("Hello.124{45}", ".{45}", true),
            Arguments.of("cab", "abc", false),
            Arguments.of("123a123b", "abc", false),
            Arguments.of("abc", "abcde", false)

        };
    }


    @ParameterizedTest
    @MethodSource("words")
    void test(String word, String subsequence, boolean except){
        assertEquals(except, Task6.isContainSubsequence(word, subsequence));
    }
}
