package edu.hw1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task6 {
    private static final int CONSTANT_KARPEKAR = 6174;
    private static final int VALUE_LENGTH = 4;
    private static final int MIN_VALUE = 1001;
    private static final int NUMBER_SYSTEM = 10;

    public static int findAmountOfStepsToReachKarpekarValue(int value) {
        return calculateNumberOfSteps(value, 0);
    }

    private Task6() {

    }

    private static int calculateNumberOfSteps(int startValue, int step) {
        if (startValue == CONSTANT_KARPEKAR) {
            return step;
        }
        List<Integer> sortedDigits = getSortedDigits(startValue);
        checkValue(sortedDigits);
        int maxValue = getMaxValue(sortedDigits);
        int minValue = getMinValue(sortedDigits);
        int newValue = maxValue - minValue;
        return calculateNumberOfSteps(newValue, step + 1);
    }

    private static void checkValue(List<Integer> sortedDigits) {
        if (sortedDigits.size() < VALUE_LENGTH || sortedDigits.size() > VALUE_LENGTH) {
            throw new IllegalArgumentException(String.format(
                "The length of the number must be equal to%d and be greater than %d", VALUE_LENGTH, MIN_VALUE));
        }
        if (sortedDigits.get(0).equals(sortedDigits.get(sortedDigits.size() - 1))) {
            throw new IllegalArgumentException("All the numbers don't have to be the same");
        }
    }

    private static List<Integer> getSortedDigits(int value) {
        Integer[] digits = new Integer[NUMBER_SYSTEM];
        Arrays.fill(digits, 0);
        int currentValue = value;
        while (currentValue > 0) {
            digits[currentValue % NUMBER_SYSTEM] += 1;
            currentValue = currentValue / NUMBER_SYSTEM;
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < NUMBER_SYSTEM; ++i) {
            if (digits[i] != 0) {
                for (int j = 0; j < digits[i]; ++j) {
                    result.add(i);
                }
            }
        }
        return result;
    }

    private static int getMaxValue(List<Integer> sortedDigits) {
        return convertListToInt(sortedDigits.reversed());
    }

    private static int getMinValue(List<Integer> sortedDigits) {
        return convertListToInt(sortedDigits);
    }

    private static int convertListToInt(List<Integer> sortedDigits) {
        int value = 0;
        for (int digit : sortedDigits) {
            value = value * NUMBER_SYSTEM + digit;
        }
        return value;
    }

}
