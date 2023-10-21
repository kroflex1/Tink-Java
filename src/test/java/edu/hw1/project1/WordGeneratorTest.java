package edu.hw1.project1;

import edu.project1.Word.Topic;
import edu.project1.Word.WordGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WordGeneratorTest {

    static Arguments[] availableTopics() {
        return new Arguments[] {
            Arguments.of(Topic.Food),
            Arguments.of(Topic.Animals),
            Arguments.of(Topic.Sport)
        };
    }
    
    @ParameterizedTest
    @MethodSource("availableTopics")
    @DisplayName("WordGenerator ")
    void wordGeneratorReturnRandomWord(Topic topic){
        assertNotNull(WordGenerator.getRandomWordFromTopic(topic));
    }


    @ParameterizedTest
    @MethodSource("availableTopics")
    @DisplayName("WordGenerator ")
    void wordGeneratorReturnRandomWord(Topic topic){
        assertNotNull(WordGenerator.getRandomWordFromTopic(topic));
    }
}
