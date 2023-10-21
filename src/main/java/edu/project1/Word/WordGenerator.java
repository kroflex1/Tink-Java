package edu.project1.Word;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import static java.util.Map.entry;

public class WordGenerator {
    private static final Map<Topic, Set<String>> words = new HashMap<>();
    private static final Random randomizer = new Random();

    @NotNull
    public static String getRandomWordFromTopic(Topic topic) throws IllegalArgumentException {
        if (!words.containsKey(topic)) {
            throw new IllegalArgumentException("The submitted topic is not available");
        }
        Set<String> topicWords = words.get(topic);
        int randomIndex = randomizer.nextInt(words.size());
        int i = 0;
        for (String word : topicWords) {
            if (i == randomIndex) {
                return word;
            }
            i++;
        }
        throw new IllegalStateException("Something went wrong while picking a random word");
    }

    public static void addNewWordToTopic(String word, Topic topic) {
        if (!words.containsKey(topic)) {
            words.put(topic, new HashSet<>());
        }
        words.get(topic).add(word);
    }

    public static void addWordsToTopic(Set<String> newWords, Topic topic) {
        if (!words.containsKey(topic)) {
            words.put(topic, newWords);
        }
        words.get(topic).addAll(newWords);
    }

    public static Set<Topic> getAvailableTopics() {
        return words.keySet();
    }

    private static void fillTopicsWithWords() {
        Set<String> sportWords = new HashSet<>(Arrays.asList("football", "basketball", "hockey"));
        Set<String> animalWords = new HashSet<>(Arrays.asList("lion", "wolf", "rabbit"));
        Set<String> foodWords = new HashSet<>(Arrays.asList("apple", "cake", "carrot"));
        addWordsToTopic(sportWords, Topic.Sport);
        addWordsToTopic(animalWords, Topic.Animals);
        addWordsToTopic(foodWords, Topic.Food);
    }

    static {
        fillTopicsWithWords();
    }
}
