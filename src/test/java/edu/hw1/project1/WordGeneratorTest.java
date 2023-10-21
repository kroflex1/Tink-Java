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

    static Arguments[] topics() {
        return new Arguments[] {
            Arguments.of(Topic.Food),
            Arguments.of(Topic.Animals),
            Arguments.of(Topic.Sport)
        };
    }

    @Test
    @DisplayName("WordGenerator не имеет слов при инициализации")
    void wordGeneratorIsEmpty() {
        WordGenerator wordGenerator = new WordGenerator();
        assertEquals(0, wordGenerator.getAvailableTopics().size());
    }

    @Test
    @DisplayName("В WordGenerator можно добавить 3 стартовые тематики")
    void wordGeneratorHaveThreeStartTopic() {
        WordGenerator wordGenerator = new WordGenerator();
        wordGenerator.fillTopicsWithStartWords();
        assertEquals(
            new HashSet<Topic>(Arrays.asList(Topic.Sport, Topic.Food, Topic.Animals)),
            wordGenerator.getAvailableTopics()
        );
    }

    @Test
    @DisplayName("WordGenerator не может вернуть слово по тематике, которое отсутствует в нём")
    void wordGeneratorCantReturnRandomWord() {
        Topic notAvailableTopic = Topic.Country;
        WordGenerator wordGenerator = new WordGenerator();
        assertFalse(wordGenerator.getAvailableTopics().contains(notAvailableTopic));
        assertThrows(IllegalArgumentException.class, () ->
            wordGenerator.getRandomWordFromTopic(notAvailableTopic));
    }

    @ParameterizedTest
    @MethodSource("topics")
    @DisplayName("WordGenerator возвращает случайное слово")
    void wordGeneratorReturnRandomWord(Topic topic) {
        WordGenerator wordGenerator = new WordGenerator();
        wordGenerator.fillTopicsWithStartWords();
        assertNotNull(wordGenerator.getRandomWordFromTopic(topic));
    }

    @Test
    @DisplayName("Добавление новой тематики и слов в WordGenerator")
    void addNewWordsToWordGenerator() {
        Topic newTopic = Topic.Country;
        Set<String> newWords = new HashSet<>(Arrays.asList("Russia", "USA", "India"));
        WordGenerator wordGenerator = new WordGenerator();
        wordGenerator.addWordsToTopic(newWords, newTopic);
        assertEquals(1, wordGenerator.getAvailableTopics().size());
        String randomCountry = wordGenerator.getRandomWordFromTopic(newTopic);
        assertTrue(newWords.contains(randomCountry));
    }
}
