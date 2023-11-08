package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task6 {

    public static boolean isContainSubsequence(String word, String subsequence) {
        StringBuilder regex = new StringBuilder();
        for (int i = 0; i < subsequence.length(); ++i) {
            regex.append("\\Q").append(subsequence.charAt(i)).append("\\E").append(".*");
        }
        Pattern pattern = Pattern.compile(regex.toString());
        Matcher matcher = pattern.matcher(word);
        return matcher.find();
    }

    private Task6() {

    }
}
