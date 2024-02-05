package edu.hw10;

import edu.hw10.Task2.Cache;
import edu.hw10.Task2.CacheProxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {

    @Test
    void testFibCalculatorProxy() {
        SimpleFib simpleFib = new SimpleFib();
        FibCalculator proxy = CacheProxy.create(simpleFib, FibCalculator.class);
        assertEquals(1, proxy.findFibValue(1));
        assertEquals(1, proxy.findFibValue(2));
        assertEquals(2, proxy.findFibValue(3));
        assertEquals(3, proxy.findFibValue(4));
        assertEquals(5, proxy.findFibValue(5));
        assertEquals(8, proxy.findFibValue(6));
        assertEquals(13, proxy.findFibValue(7));
    }

    public interface FibCalculator {
        @Cache(persist = true)
        long findFibValue(int number);
    }

    public static class SimpleFib implements FibCalculator {
        @Cache
        public long findFibValue(int number) {
            if (number == 1 || number == 2) {
                return 1;
            }
            return findFibValue(number - 1) + findFibValue(number - 2);
        }
    }
}
