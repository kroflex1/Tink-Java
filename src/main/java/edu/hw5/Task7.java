package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task7 {

    public static boolean solve1(String word) {
        Pattern pattern = Pattern.compile("[01]{2}0[01]*");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

    @SuppressWarnings("MagicNumber")
    public static boolean solve2(String word) {
        Pattern pattern = Pattern.compile("(([01])[01]*([01]))|([01])");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches() && (matcher.group(4) != null || matcher.group(2).equals(matcher.group(3)));
    }

    public static boolean solve3(String word) {
        if (word.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("[01]{1,3}");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

    private Task7() {

    }
}
