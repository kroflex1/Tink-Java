package edu.hw8.Task2;

public class FibonacciNumbersManager {

    @SuppressWarnings("MagicNumber")
    public int find(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        int prevPrev = 1;
        int prev = 1;
        for (int i = 3; i <= n; ++i) {
            int temp = prev + prevPrev;
            prevPrev = prev;
            prev = temp;
        }
        return prev;
    }
}
