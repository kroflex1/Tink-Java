package edu.hw3;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task2Test {

    static Stream<Arguments> lineAndClusters() {
        return Stream.of(
            arguments("()()()", Arrays.asList("()", "()", "()")),
            arguments("((()))", Arrays.asList("((()))")),
            arguments("((()))(())()()(()())", Arrays.asList("((()))", "(())", "()", "()", "(()())")),
            arguments("((())())(()(()()))", Arrays.asList("((())())", "(()(()()))"))
        );
    }

    @ParameterizedTest
    @MethodSource("lineAndClusters")
    @DisplayName("Проверка примеров из заданий")
    void test(String line, List<String> cluster) {
        List<String> result = Task2.clusterize(line);
        assertEquals(cluster, result);
    }

    @ParameterizedTest
    @ValueSource(strings = {"()(.", "(avc)", "(]", })
    @DisplayName("Вызов ошибки при содержании некорректных данных в строке")
    void testInvalidSymbolAtCluster(String line) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Task2.clusterize(line);
        });
        assertEquals("The line must contain only characters '(' and ')'", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {")", "(", "(()", "(())(", "(()()"})
    @DisplayName("Вызов ошибки при невозможности закрыть одну из скобок")
    void testInvalidClusterFormat(String line) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Task2.clusterize(line);
        });
        assertEquals("One of the brackets is not closed", exception.getMessage());
    }

}
