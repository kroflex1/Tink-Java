package edu.project1;

import edu.project1.WordGenerator.Topic;
import edu.project1.WordGenerator.HangmanWordGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class HangmanWordGeneratorTest {

    static Arguments[] topics() {
        return new Arguments[] {
            Arguments.of(Topic.Food),
            Arguments.of(Topic.Animal),
            Arguments.of(Topic.Sport)
        };
    }

    @Test
    @DisplayName("WordGenerator не имеет слов при инициализации")
    void testWordGeneratorIsEmpty() {
        HangmanWordGenerator hangmanWordGenerator = new HangmanWordGenerator();
        assertEquals(0, hangmanWordGenerator.getAvailableTopics().size());
    }

    @Test
    @DisplayName("В WordGenerator можно добавить 3 стартовые тематики")
    void testWordGeneratorHaveThreeStartTopic() {
        HangmanWordGenerator hangmanWordGenerator = new HangmanWordGenerator();
        hangmanWordGenerator.fillTopicsWithStartWords();
        assertEquals(
            new HashSet<Topic>(Arrays.asList(Topic.Sport, Topic.Food, Topic.Animal)),
            hangmanWordGenerator.getAvailableTopics()
        );
    }

    @Test
    @DisplayName("WordGenerator не может вернуть слово по тематике, которое отсутствует в нём")
    void testWordGeneratorCantReturnRandomWord() {
        Topic notAvailableTopic = Topic.Country;
        HangmanWordGenerator hangmanWordGenerator = new HangmanWordGenerator();
        assertFalse(hangmanWordGenerator.getAvailableTopics().contains(notAvailableTopic));
        assertThrows(IllegalArgumentException.class, () ->
            hangmanWordGenerator.getRandomWordFromTopic(notAvailableTopic));
    }

    @ParameterizedTest
    @MethodSource("topics")
    @DisplayName("WordGenerator возвращает случайное слово")
    void testWordGeneratorReturnRandomWord(Topic topic) {
        HangmanWordGenerator hangmanWordGenerator = new HangmanWordGenerator();
        hangmanWordGenerator.fillTopicsWithStartWords();
        assertNotNull(hangmanWordGenerator.getRandomWordFromTopic(topic));
    }

    @Test
    @DisplayName("Добавление новой тематики и слов в WordGenerator")
    void testAddNewWordsToWordGenerator() {
        Topic newTopic = Topic.Country;
        Set<String> newWords = new HashSet<>(Arrays.asList("Russia", "USA", "India"));
        HangmanWordGenerator hangmanWordGenerator = new HangmanWordGenerator();
        hangmanWordGenerator.addWordsToTopic(newWords, newTopic);
        assertEquals(1, hangmanWordGenerator.getAvailableTopics().size());
        String randomCountry = hangmanWordGenerator.getRandomWordFromTopic(newTopic);
        assertTrue(newWords.contains(randomCountry));
    }
}
