package edu.hw1;

public class Task2 {
    private static final int DIVIDER = 10;

    private Task2() {

    }

    public static int countDigits(int value) {
        int currentValue = value;
        int amountOfDigits = 1;
        while (currentValue / DIVIDER != 0) {
            ++amountOfDigits;
            currentValue /= DIVIDER;
        }
        return amountOfDigits;
    }
}
