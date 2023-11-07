package edu.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task4 {

    private final static Map<Integer, String> ARABIC_TO_ROMAN = getArabicToRomanMap();
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 3999;

    @SuppressWarnings("MagicNumber")
    public static String convertToRoman(int value) {
        checkValue(value);
        List<String> result = new ArrayList<>();
        String currentValue = String.valueOf(value);
        int multiplier = 1;
        for (int i = currentValue.length() - 1; i >= 0; --i) {
            int remains = Character.getNumericValue(currentValue.charAt(i));
            if (remains == 0) {
                multiplier *= 10;
                continue;
            }
            if (remains < 4) {
                result.add(ARABIC_TO_ROMAN.get(multiplier).repeat(remains));
            } else if (remains <= 6) {
                result.add(ARABIC_TO_ROMAN.get(remains * multiplier));
            } else if (remains <= 8) {
                result.add(ARABIC_TO_ROMAN.get(multiplier).repeat(remains - 6));
                result.add(ARABIC_TO_ROMAN.get(6 * multiplier));
            } else {
                result.add(ARABIC_TO_ROMAN.get(9 * multiplier));
            }
            multiplier *= 10;
        }
        return String.join("", result.reversed());
    }

    @SuppressWarnings("MagicNumber")
    private static Map<Integer, String> getArabicToRomanMap() {
        return new HashMap<>() {{
            put(1, "I");
            put(4, "IV");
            put(5, "V");
            put(6, "VI");
            put(9, "IX");

            put(10, "X");
            put(40, "XL");
            put(50, "L");
            put(60, "LX");
            put(90, "XC");

            put(100, "C");
            put(400, "CD");
            put(500, "D");
            put(600, "DC");
            put(900, "CM");

            put(1000, "M");
        }};
    }

    private static void checkValue(int value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException("The number must be in the range from 1 to 3999");
        }
    }

    private Task4() {

    }
}
