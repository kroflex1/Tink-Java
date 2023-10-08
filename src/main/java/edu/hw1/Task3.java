package edu.hw1;

import java.util.Arrays;

public class Task3 {
    private Task3() {

    }

    public static boolean isNestable(int[] firstArray, int[] secondArray) {
        if (firstArray.length == 0 || secondArray.length == 0) {
            throw new IllegalArgumentException("One of the arrays is empty");
        }
        int minFirst = Arrays.stream(firstArray).min().getAsInt();
        int maxFirst = Arrays.stream(firstArray).max().getAsInt();
        int minSecond = Arrays.stream(secondArray).min().getAsInt();
        int maxSecond = Arrays.stream(secondArray).max().getAsInt();
        return minFirst > minSecond && maxFirst < maxSecond;
    }
}
