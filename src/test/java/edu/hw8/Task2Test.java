package edu.hw8;

import edu.hw3.Task5.Human;
import edu.hw8.Task2.FibonacciNumbersManager;
import edu.hw8.Task2.FixedThreadPool;
import edu.hw8.Task2.ThreadPool;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {

    static Stream<Arguments> fibonacciNumbers() {
        return Stream.of(
            arguments(1, 1),
            arguments(2, 1),
            arguments(3, 2),
            arguments(4, 3),
            arguments(5, 5),
            arguments(6, 8),
            arguments(7, 13),
            arguments(8, 21),
            arguments(9, 34),
            arguments(10, 55),
            arguments(11, 89)
        );
    }

    @ParameterizedTest
    @MethodSource("fibonacciNumbers")
    void testFibonacciNumbersManager(int n, int expect) {
        FibonacciNumbersManager fibonacciNumbersManager = new FibonacciNumbersManager();
        assertEquals(expect, fibonacciNumbersManager.find(n));
    }

    @Test
    void testSingleThreadPool() {
        int n = 11;
        AtomicInteger result = new AtomicInteger();
        FibonacciNumbersManager fibonacciNumbersManager = new FibonacciNumbersManager();
        try (ThreadPool threadPool = new FixedThreadPool()) {
            threadPool.create(1);
            threadPool.execute(() -> {
                result.set(fibonacciNumbersManager.find(n));
            });
            threadPool.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(fibonacciNumbersManager.find(n), result.get());
    }

    @ParameterizedTest
    @CsvSource({"1, 2", "2, 1", "3, 9", "4, 40"})
    void testMultiThreadPool(int numberOfThreads, int numberOfTasks) {
        int n = 11;
        FibonacciNumbersManager fibonacciNumbersManager = new FibonacciNumbersManager();
        List<Integer> results = Collections.synchronizedList(new ArrayList<>());
        Runnable runnable = () -> results.add(fibonacciNumbersManager.find(n));
        List<Runnable> tasks = Stream.generate(() -> runnable).limit(numberOfTasks).toList();

        try (ThreadPool threadPool = new FixedThreadPool()) {
            threadPool.create(numberOfThreads);
            tasks.forEach(threadPool::execute);
            threadPool.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(results.size(), numberOfTasks);
        assertEquals(Stream.generate(()->fibonacciNumbersManager.find(n)).limit(numberOfTasks).toList(), results);
    }
}
