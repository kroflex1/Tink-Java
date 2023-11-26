package edu.hw7.Task4;

import java.util.List;

public class StatisticsManager {
    @SuppressWarnings("MagicNumber")
    public static String getStatistics(int numberOfThreads) {
        List<Integer> iterations = List.of(10_000_000, 100_000_000, 1_000_000_000);
        SingleThreadedPiFinder singlePiFinder = new SingleThreadedPiFinder();
        MultiThreadedPiFinder multiPiFinder = new MultiThreadedPiFinder();
        StringBuilder result = new StringBuilder();
        for (int numberOfIterations : iterations) {
            result.append("Number of iterations: ").append(numberOfIterations).append("\n");
            long startTime = System.nanoTime();
            double resultPI = singlePiFinder.findPi(numberOfIterations);
            long endTime = System.nanoTime();
            result.append("Single-threaded PI: ").append(resultPI).append("\n");
            result.append("Single-threaded Time: ").append((double) (endTime - startTime) / 1_000_000_000.0).append("s")
                .append("\n");

            startTime = System.nanoTime();
            resultPI = multiPiFinder.findPi(numberOfIterations, numberOfThreads);
            endTime = System.nanoTime();
            result.append("Multi-threaded PI: ").append(resultPI).append("\n");
            result.append("Multi-threaded Time: ").append((double) (endTime - startTime) / 1_000_000_000.0)
                .append("s").append("\n");
            result.append("\n");
        }
        return result.toString();
    }

    private StatisticsManager() {

    }
}
