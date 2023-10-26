package edu.hw3;

import edu.hw3.Task8.BackwardIterator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task8Test {

    static Stream<Arguments> collections() {
        return Stream.of(
            arguments(List.of(1, 2, 3)),
            arguments(List.of("a", "b", "c")),
            arguments(List.of(1))
        );
    }

    @ParameterizedTest
    @MethodSource("collections")
    void test(List<?> collection) {
        Iterator<?> iterator = new BackwardIterator<>(collection);
        for (var object : collection.reversed()) {
            assertEquals(object, iterator.next());
        }
    }
}
