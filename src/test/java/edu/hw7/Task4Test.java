package edu.hw7;

import edu.hw7.Task4.MultiThreadedPiFinder;
import edu.hw7.Task4.SingleThreadedPiFinder;
import edu.hw7.Task4.StatisticsManager;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task4Test {
    private static final double EPSILON = 0.2d;

    static Stream<Arguments> singleThreadPiFinder() {
        return Stream.of(
            arguments(new SingleThreadedPiFinder(), 10_000),
            arguments(new SingleThreadedPiFinder(), 10_000_000),
            arguments(new SingleThreadedPiFinder(), 50_000_000),
            arguments(new SingleThreadedPiFinder(), 100_000_000)
        );
    }

    static Stream<Arguments> multiThreadPiFinder() {
        return Stream.of(
            arguments(new MultiThreadedPiFinder(), 10_000, 2),
            arguments(new MultiThreadedPiFinder(), 10_000_000, 2),
            arguments(new MultiThreadedPiFinder(), 50_000_000, 2),
            arguments(new MultiThreadedPiFinder(), 100_000_000, 2),
            arguments(new MultiThreadedPiFinder(), 10_000, 3),
            arguments(new MultiThreadedPiFinder(), 10_000_000, 3),
            arguments(new MultiThreadedPiFinder(), 50_000_000, 3),
            arguments(new MultiThreadedPiFinder(), 100_000_000, 3),
            arguments(new MultiThreadedPiFinder(), 10_000, 4),
            arguments(new MultiThreadedPiFinder(), 10_000_000, 4),
            arguments(new MultiThreadedPiFinder(), 50_000_000, 4),
            arguments(new MultiThreadedPiFinder(), 100_000_000, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("singleThreadPiFinder")
    void testSingleThreadPiDelta(SingleThreadedPiFinder piFinder, int iterations) {
        double resultPi = piFinder.findPi(iterations);
        assertEquals(Math.PI, resultPi, EPSILON);
    }

    @ParameterizedTest
    @MethodSource("multiThreadPiFinder")
    void testMultiThreadPiDelta(MultiThreadedPiFinder piFinder, int iterations, int threads) {
        double resultPi = piFinder.findPi(iterations);
        assertEquals(Math.PI, resultPi, EPSILON);
    }

    @Test
    void testStatistic(){
        String result = StatisticsManager.getStatistics(4);
        System.out.print(result);
        assertFalse(result.isEmpty());
    }
}
