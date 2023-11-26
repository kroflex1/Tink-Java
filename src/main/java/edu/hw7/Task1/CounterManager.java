package edu.hw7.Task1;

public class CounterManager {

    public static int increaseCounterByTwo(Counter counter) {
        Thread firstThread = new Thread(counter::increase);
        Thread secondThread = new Thread(counter::increase);
        firstThread.start();
        secondThread.start();
        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return counter.getValue();
    }

    private CounterManager() {

    }
}
