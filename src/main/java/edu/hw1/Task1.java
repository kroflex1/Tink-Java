package edu.hw1;

public class Task1 {
    private static final int SECONDS_PER_MINUTE = 60;

    private Task1() {

    }

    public static int minutesToSeconds(String time) {
        String[] timeParts = time.split(":");
        if (timeParts.length != 2 || isMinusAndPlusInTime(time)) {
            return -1;
        }
        int seconds;
        int minutes;
        try {
            seconds = Integer.parseInt(timeParts[1]);
            minutes = Integer.parseInt(timeParts[0]);
        } catch (NumberFormatException e) {
            return -1;
        }
        if (seconds >= SECONDS_PER_MINUTE) {
            return -1;
        }
        return minutes * SECONDS_PER_MINUTE + seconds;
    }

    private static boolean isMinusAndPlusInTime(String time) {
        for (int i = 0; i < time.length(); ++i) {
            if (time.charAt(i) == '-' || time.charAt(i) == '+') {
                return true;
            }
        }
        return false;
    }
}
