package edu.hw7.Task4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadedPiFinder extends PiFinder {
    @Override
    public double findPi(int numberOfIterations) {
        return findPi(numberOfIterations, 4);
    }

    public double findPi(int numberOfIterations, int numberOfThreads) {
        ExecutorService threadPool = Executors.newFixedThreadPool(numberOfThreads);
        int totalCount = numberOfIterations;
        AtomicInteger circleCount = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        for (int i = 0; i < numberOfThreads; ++i) {
            threadPool.execute(() -> {
                for (int j = 0; j < numberOfIterations / numberOfThreads; ++j) {
                    Point currentPoint =
                        new Point(ThreadLocalRandom.current().nextDouble(), ThreadLocalRandom.current().nextDouble());
                    double distanceToCenterOfCircle = getDistanceBetweenTwoPoints(CIRCLE_CENTER, currentPoint);
                    if (distanceToCenterOfCircle <= RADIUS) {
                        circleCount.incrementAndGet();
                    }
                }
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threadPool.shutdown();

        return 4 * (circleCount.get() * 1.0 / totalCount);
    }
}
