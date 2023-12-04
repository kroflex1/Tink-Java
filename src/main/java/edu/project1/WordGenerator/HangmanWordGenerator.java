package edu.project1.WordGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class HangmanWordGenerator implements WordGenerator {
    private final Map<Topic, Set<String>> words;
    private final Random randomizer;

    public HangmanWordGenerator() {
        words = new HashMap<>();
        randomizer = new Random();
    }

    @NotNull
    public String getRandomWordFromTopic(Topic topic) throws IllegalArgumentException, IllegalStateException {
        if (!words.containsKey(topic)) {
            throw new IllegalArgumentException("The submitted topic is not available");
        }
        Set<String> topicWords = words.get(topic);
        int randomIndex = randomizer.nextInt(topicWords.size());
        int i = 0;
        for (String word : topicWords) {
            if (i == randomIndex) {
                return word;
            }
            i++;
        }
        throw new IllegalStateException("Something went wrong while picking a random word");
    }

    public void fillTopicsWithStartWords() {
        Set<String> sportWords = new HashSet<>(Arrays.asList("football", "basketball", "hockey"));
        Set<String> animalWords = new HashSet<>(Arrays.asList("lion", "wolf", "rabbit"));
        Set<String> foodWords = new HashSet<>(Arrays.asList("apple", "cake", "carrot"));
        addWordsToTopic(sportWords, Topic.Sport);
        addWordsToTopic(animalWords, Topic.Animal);
        addWordsToTopic(foodWords, Topic.Food);
    }

    public void addWordsToTopic(Set<String> newWords, Topic topic) {
        if (!words.containsKey(topic)) {
            words.put(topic, newWords);
        }
        words.get(topic).addAll(newWords);
    }

    public void addWordToTopic(String word, Topic topic) {
        if (!words.containsKey(topic)) {
            words.put(topic, new HashSet<>());
        }
        words.get(topic).add(word);
    }

    public Set<Topic> getAvailableTopics() {
        return words.keySet();
    }
}
