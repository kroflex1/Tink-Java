package edu.hw1.project1;

import edu.hw1.Task5;
import edu.project1.Word.Topic;
import edu.project1.Word.WordGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WordGeneratorTest {

    static Arguments[] availableTopics() {
        return new Arguments[] {
            Arguments.of(Topic.Food),
            Arguments.of(Topic.Animals),
            Arguments.of(Topic.Sport)
        };
    }

    @Test
    @DisplayName("WordGenerator не может вернуть слово по заданной тематике")
    void wordGeneratorCantReturnRandomWord() {
        Topic notAvailableTopic = Topic.Country;
        WordGenerator wordGenerator = new WordGenerator();
        assertFalse(wordGenerator.getAvailableTopics().contains(notAvailableTopic));
        assertThrows(IllegalArgumentException.class, () ->
            wordGenerator.getRandomWordFromTopic(notAvailableTopic));
    }

    @ParameterizedTest
    @MethodSource("availableTopics")
    @DisplayName("WordGenerator возвращает случайное слово")
    void wordGeneratorReturnRandomWord(Topic topic) {
        WordGenerator wordGenerator = new WordGenerator();
        assertNotNull(wordGenerator.getRandomWordFromTopic(topic));
    }



    @Test
    @DisplayName("Добавление нового слова в WordGenerator")
    void addNewWordsToWordGenerator() {
        Topic newTopic = Topic.Country;
        Set<String> newWords = new HashSet<>(Arrays.asList("Russia", "USA", "India"));
        WordGenerator wordGenerator = new WordGenerator();
        assertFalse(wordGenerator.getAvailableTopics().contains(newTopic));
        wordGenerator.addWordsToTopic(newWords, newTopic);
        assertNotNull(wordGenerator.getRandomWordFromTopic(newTopic));
    }

    @Test
    @DisplayName("Добавление новой тематики в WordGenerator")
    void addNewWordToWordGenerator() {
        String newWord = "Russia";
        Topic newTopic = Topic.Country;
        WordGenerator wordGenerator = new WordGenerator();
        assertFalse(wordGenerator.getAvailableTopics().contains(newTopic));
        wordGenerator.addNewWordToTopic(newWord, newTopic);
        assertEquals(newWord, wordGenerator.getRandomWordFromTopic(newTopic));
    }
}
