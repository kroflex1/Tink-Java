package edu.project1.Word;

import java.util.Map;
import java.util.Random;
import static java.util.Map.entry;

public class WordGenerator {
    private static final Map<Topic, String[]> words = fillTopicsWithWords();
    private static final Random randomizer = new Random();

    public static String getRandomWordFromTopic(Topic topic) throws IllegalArgumentException {
        if (!words.containsKey(topic)) {
            throw new IllegalArgumentException("The submitted topic is not available");
        }
        String[] topicWords = words.get(topic);
        return topicWords[randomizer.nextInt(topicWords.length)];
    }

    private static Map<Topic, String[]> fillTopicsWithWords() {
        return Map.ofEntries(
            entry(Topic.Sport, new String[] {"football", "basketball", "hockey"}),
            entry(Topic.Animals, new String[] {"lion", "wolf", "eagle"}),
            entry(Topic.Food, new String[] {"apple", "cake", "meat"})
        );
    }
}
