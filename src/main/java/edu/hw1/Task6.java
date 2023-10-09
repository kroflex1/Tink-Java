package edu.hw1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Task6 {
    private static final int CONSTANT_KARPEKAR = 6174;
    private static final int MIN_VALUE = 1000;
    private static final int NUMBER_SYSTEM = 10;

    private Task6() {

    }

    private static void checkValue(int value) {
        if (value < MIN_VALUE) {
            throw new IllegalArgumentException(String.format("The number must be greater than %d", MIN_VALUE));
        }
        Set<Integer> set = new HashSet<>();
        int currentValue = value;
        while (currentValue > 0) {
            set.add(currentValue % NUMBER_SYSTEM);
            currentValue = currentValue / NUMBER_SYSTEM;
        }
        if (set.size() == 1) {
            throw new IllegalArgumentException("All the numbers don't have to be the same");
        }
    }

    private static Integer[] getSortedDigits(int value) {
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
        return result.toArray(new Integer[0]);
    }

    private static int getMaxValue(Integer[] sortedDigits) {
        int maxValue = 0;
        int multiplier = 1;
        for (int digit : sortedDigits) {
            maxValue += digit * multiplier;
            multiplier *= NUMBER_SYSTEM;
        }
        return maxValue;
    }

    private static int getMinValue(Integer[] sortedDigits) {
        int minValue = 0;
        int multiplier = (int) Math.pow(NUMBER_SYSTEM, sortedDigits.length - 1);
        for (int digit : sortedDigits) {
            minValue += digit * multiplier;
            multiplier /= NUMBER_SYSTEM;
        }
        return minValue;
    }

    private static int calculateNumberOfSteps(int startValue, int step) {
        checkValue(startValue);
        if (startValue == CONSTANT_KARPEKAR) {
            return step;
        }
        Integer[] sortedDigits = getSortedDigits(startValue);
        int maxValue = getMaxValue(sortedDigits);
        int minValue = getMinValue(sortedDigits);
        int newValue = maxValue - minValue;
        return calculateNumberOfSteps(newValue, step + 1);
    }

    public static int findAmountOfStepsToReachKapekarValue(int value) {
        return calculateNumberOfSteps(value, 0);
    }
}
