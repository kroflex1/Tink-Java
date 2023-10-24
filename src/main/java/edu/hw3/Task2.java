package edu.hw3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.regex.Pattern;

public class Task2 {

    private static final String UNCLOSED_BRACKETS_MESSAGE = "One of the brackets is not closed";

    public static List<String> clusterize(String line) {
        checkLine(line);
        List<String> clusters = new ArrayList<>();
        Queue<Character> queue = new LinkedList<>();
        int startIndex = 0;
        queue.offer(line.charAt(0));
        for (int endIndex = 1; endIndex < line.length(); ++endIndex) {
            if (line.charAt(endIndex) == '(') {
                queue.offer('(');
            } else {
                try {
                    queue.remove();
                } catch (NoSuchElementException e) {
                    throw new IllegalArgumentException(UNCLOSED_BRACKETS_MESSAGE);
                }
            }
            if (queue.isEmpty()) {
                clusters.add(line.substring(startIndex, endIndex + 1));
                startIndex = endIndex + 1;
            }
        }
        if (!queue.isEmpty()) {
            throw new IllegalArgumentException(UNCLOSED_BRACKETS_MESSAGE);
        }
        return clusters;
    }

    private static void checkLine(String line) {
        Pattern pattern = Pattern.compile("[()]+");
        if (!pattern.matcher(line).matches()) {
            throw new IllegalArgumentException("The line must contain only characters '(' and ')'");
        }
    }

    private Task2() {

    }
}
