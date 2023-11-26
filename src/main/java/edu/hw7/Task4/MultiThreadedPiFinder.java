package edu.hw7.Task4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class MultiThreadedPiFinder extends PiFinder {
    @Override
    public double findPi(int numberOfIterations) {
        return findPi(numberOfIterations, Runtime.getRuntime().availableProcessors() + 1);
    }

    @SuppressWarnings("MagicNumber")
    public double findPi(int numberOfIterations, int numberOfThreads) {
        ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);
        int totalCount = numberOfIterations;
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            futures.add(
                CompletableFuture.supplyAsync(
                    () -> {
                        int circleCount = 0;
                        for (int j = 0; j < numberOfIterations / numberOfThreads; ++j) {
                            Point currentPoint = new Point(
                                ThreadLocalRandom.current().nextDouble(),
                                ThreadLocalRandom.current().nextDouble()
                            );
                            double distanceToCenterOfCircle = getDistanceBetweenTwoPoints(CIRCLE_CENTER, currentPoint);
                            if (distanceToCenterOfCircle <= RADIUS) {
                                ++circleCount;
                            }
                        }
                        return circleCount;
                    },
                    threadPool
                ));
        }

        int circleCount = 0;
        for (Future<Integer> future : futures) {
            try {
                circleCount += future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        threadPool.shutdown();

        return 4 * (circleCount * 1.0 / totalCount);
    }
}
