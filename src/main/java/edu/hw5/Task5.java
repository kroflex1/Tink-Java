package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task5 {
    private static final String ALLOWED_LETTERS = "АВЕКМНОРСТУХ";

    public static boolean checkCarLicensePlate(String carLicensePlate) {
        Pattern pattern = Pattern.compile(String.format("[%s]{1}\\d{3}[%s]{2}\\d{3}", ALLOWED_LETTERS, ALLOWED_LETTERS));
        Matcher matcher = pattern.matcher(carLicensePlate);
        return matcher.matches();
    }

    private Task5() {

    }
}
