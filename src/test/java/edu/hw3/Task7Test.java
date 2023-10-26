package edu.hw3;

import java.util.TreeMap;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task7Test {

    static Stream<Arguments> objectPair() {
        return Stream.of(
            arguments("bear", "apple", 1),
            arguments("apple", "bear", -1),
            arguments("apple", "apple", 0),
            arguments("apple", null, 1),
            arguments(null, "apple", -1),
            arguments(null, null, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("objectPair")
    @DisplayName("Проверка работы Comparator")
    void testComparator(String firstWord, String secondWord, int except) {
        assertEquals(except, new Task7<String>().compare(firstWord, secondWord));
    }

    @Test
    @DisplayName("TreeMap корректно принимает ключ со значением null")
    void testTreeAndNewComparator() {
        TreeMap<String, String> tree = new TreeMap<>(new Task7<String>());
        tree.put(null, "test");
        assertThat(tree.containsKey(null)).isTrue();
    }
}
