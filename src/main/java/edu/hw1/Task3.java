package edu.hw1;

public class Task3 {
    public static int countNumberOfDigitsInValue(int value) {
        int amountOfDigits = 1;
        while (value / 10 != 0) {
            ++amountOfDigits;
            value /= 10;
        }
        return amountOfDigits;
    }
}
