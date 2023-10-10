package edu.hw1;

import java.util.Arrays;

public class Task1 {
    private static final int SECONDS_PER_MINUTE = 60;

    private Task1() {

    }

    public static int minutesToSeconds(String time) {
        if (!isCorrectTimeFormat(time)) {
            return -1;
        }
        int[] timeParts = Arrays.stream(time.split(":")).mapToInt(Integer::parseInt).toArray();
        return timeParts[0] * SECONDS_PER_MINUTE + timeParts[1];
    }

    private static boolean isMinusAndPlusInTime(String time) {
        for (int i = 0; i < time.length(); ++i) {
            if (time.charAt(i) == '-' || time.charAt(i) == '+') {
                return true;
            }
        }
        return false;
    }

    private static boolean isCorrectTimeFormat(String time) {
        String[] timeParts = time.split(":");
        if (timeParts.length != 2) {
            return false;
        }
        for (String timePart : timeParts) {
            if (isMinusAndPlusInTime(timePart)) {
                return false;
            }
            try {
                Integer.parseInt(timePart);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        int seconds = Integer.parseInt(timeParts[1]);
        int minutes = Integer.parseInt(timeParts[0]);
        return seconds >= 0 && seconds < SECONDS_PER_MINUTE && minutes >= 0;
    }

}
