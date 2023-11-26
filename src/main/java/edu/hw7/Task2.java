package edu.hw7;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Task2 {

    public static BigInteger getFactorial(long value) {
        List<BigInteger> digits = new ArrayList<>();
        for (long i = 1; i <= value; ++i) {
            digits.add(BigInteger.valueOf(i));
        }
        return digits.parallelStream()
            .reduce(BigInteger.valueOf(1L), (a, b) -> a.multiply(b));
    }

    private Task2() {

    }
}
