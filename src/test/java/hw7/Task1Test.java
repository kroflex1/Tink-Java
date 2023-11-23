package hw7;

import edu.hw7.Task1.Counter;
import edu.hw7.Task1.CounterManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {

    @Test
    void testIncreaseCounterByTwo(){
        Counter counter = new Counter();
        CounterManager.increaseCounterByTwo(counter);
        assertEquals(2, counter.getValue());
    }

    @Test
    void testIncreaseCounterByThree() {
        Counter counter = new Counter();
        Thread thread = new Thread(counter::increase);
        thread.start();
        CounterManager.increaseCounterByTwo(counter);
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(3, counter.getValue());
    }
}
