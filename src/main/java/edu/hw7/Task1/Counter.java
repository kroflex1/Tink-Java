package edu.hw7.Task1;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private final AtomicInteger counter = new AtomicInteger(0);

    public void increase() {
        counter.incrementAndGet();
    }

    public int getValue() {
        return counter.get();
    }
}
