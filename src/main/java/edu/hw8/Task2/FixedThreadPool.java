package edu.hw8.Task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedThreadPool implements ThreadPool {
    private int maxNumberOfThreads;
    private final AtomicInteger numberOfRunnableThreads = new AtomicInteger(0);
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    public void start() {
        while (!tasks.isEmpty()) {
            if (numberOfRunnableThreads.get() < maxNumberOfThreads) {
                numberOfRunnableThreads.incrementAndGet();
                try {
                    Runnable task = tasks.take();
                    Thread thread = new Thread(() -> {
                        task.run();
                        numberOfRunnableThreads.decrementAndGet();
                    });
                    thread.start();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    public void execute(Runnable runnable) {
        try {
            tasks.put(runnable);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(int numberOfThreads) {
        maxNumberOfThreads = numberOfThreads;
    }

    @Override
    public void close() throws Exception {
        while (numberOfRunnableThreads.get() != 0) {

        }
    }
}
