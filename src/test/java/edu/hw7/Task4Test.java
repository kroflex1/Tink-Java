package edu.hw7;

import edu.hw7.Task3.ReadWriteLockPersonDatabase;
import edu.hw7.Task3.SynchronizedPersonDatabase;
import edu.hw7.Task4.MultiThreadedPiFinder;
import edu.hw7.Task4.PiFinder;
import edu.hw7.Task4.SingleThreadedPiFinder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task4Test {
    private static final double EPSILON = 0.2d;

    static Stream<Arguments> piFinders() {
        return Stream.of(
            arguments(new SingleThreadedPiFinder(), 10_000),
            arguments(new SingleThreadedPiFinder(), 10_000_000),
            arguments(new MultiThreadedPiFinder(), 10_000),
            arguments(new MultiThreadedPiFinder(), 10_000_000)
        );
    }

    @ParameterizedTest
    @MethodSource("piFinders")
    void testPiDelta(PiFinder piFinder, int iterations) {
        double resultPi = piFinder.findPi(iterations);
        assertEquals(Math.PI, resultPi, EPSILON);
    }
}
