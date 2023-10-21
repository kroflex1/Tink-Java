package edu.hw1.project1;

import edu.project1.Results.CurrentState;
import edu.project1.Results.FailedGuess;
import edu.project1.Results.InvalidFormatResult;
import edu.project1.Results.Result;
import edu.project1.Results.Win;
import edu.project1.Session;
import edu.project1.WordGenerator.HangmanWordGenerator;
import edu.project1.WordGenerator.Topic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionTest {
    static Arguments[] words() {
        return new Arguments[] {
            Arguments.of(new HiddenWordInformation("football", Topic.Sport)),
            Arguments.of(new HiddenWordInformation("apple", Topic.Food)),
            Arguments.of(new HiddenWordInformation("Russia", Topic.Country)),
            Arguments.of(new HiddenWordInformation("wolf", Topic.Animal)),
        };
    }

    @ParameterizedTest
    @MethodSource("words")
    @DisplayName("Игра корректно загадывает секретное слово")
    void testCreationOfSecretWord(HiddenWordInformation hiddenWordInformation) {
        Session session = getSessionWithHiddenWord(hiddenWordInformation);
        Result result = session.getCurrentStateOfGame();
        assertEquals(hiddenWordInformation.word.length(), result.state().length);
        assertEquals(0, result.attempt());
    }

    @ParameterizedTest
    @MethodSource("words")
    @DisplayName("Отгадывание секретного слова без ошибок")
    void testGuessingWordWithoutMistakes(HiddenWordInformation hiddenWordInformation) {
        Session session = getSessionWithHiddenWord(hiddenWordInformation);
        String hiddenWord = hiddenWordInformation.word;
        for (int i = 0; i < hiddenWord.length(); ++i) {
            Result result = session.guess(hiddenWord.charAt(i));
            if (result instanceof Win) {
                break;
            }
            assertTrue(
                result.message().equals("Hit!") || result.message().equals("You have already guessed this letter"));
            assertEquals(0, result.attempt());
        }
        CurrentState currentState = session.getCurrentStateOfGame();
        assertEquals(0, currentState.attempt());
        assertTrue(currentState.isGameOver());
        assertEquals(hiddenWord.toLowerCase(), new String(currentState.state()));
    }

    @ParameterizedTest
    @MethodSource("words")
    @DisplayName("Игра останавливается после отгадвания слова")
    void testSessionStopAfterWinning(HiddenWordInformation hiddenWordInformation) {
        Session session = getSessionWithHiddenWord(hiddenWordInformation);
        String hiddenWord = hiddenWordInformation.word;
        for (int i = 0; i < hiddenWord.length(); ++i) {
            Result result = session.guess(hiddenWord.charAt(i));
            if (result instanceof Win) {
                break;
            }
        }
        Result resultAfterGameOver = session.guess('a');
        assertEquals(0, resultAfterGameOver.attempt());
        assertTrue(resultAfterGameOver instanceof CurrentState);
        assertTrue(((CurrentState) resultAfterGameOver).isGameOver());
    }

    @ParameterizedTest
    @MethodSource("words")
    @DisplayName("Попытки не тратятся, если введеный символ некорректен или уже является отгаданной буквой")
    void testAttemptsNotWastedWhenEnteringIncorrectlySymbol(HiddenWordInformation hiddenWordInformation) {
        Session session = getSessionWithHiddenWord(hiddenWordInformation);
        List<Character> incorrectSymbols = Arrays.asList('1', 'ы', '-', ' ', '(', '*');
        for (char symbol : incorrectSymbols) {
            Result result = session.guess(symbol);
            assertTrue(result instanceof InvalidFormatResult);
            assertEquals(0, result.attempt());
        }
        session.guess(hiddenWordInformation.word.charAt(0));
        Result result = session.guess(hiddenWordInformation.word.charAt(0));
        assertTrue(result instanceof InvalidFormatResult);
        assertEquals(0, result.attempt());
    }

    @ParameterizedTest
    @MethodSource("words")
    @DisplayName("Игра засчитывает букву, даже если она строчная")
    void testSessionAcceptUppercaseLetters(HiddenWordInformation hiddenWordInformation) {
        Session session = getSessionWithHiddenWord(hiddenWordInformation);
        String hiddenWord = hiddenWordInformation.word;
        for (int i = 0; i < hiddenWord.length(); ++i) {
            Result result = session.guess(Character.toLowerCase(hiddenWord.charAt(i)));
            if (result instanceof Win) {
                break;
            }
        }
        Result resultAfterGameOver = session.guess('a');
        assertEquals(0, resultAfterGameOver.attempt());
        assertTrue(resultAfterGameOver instanceof CurrentState);
        assertTrue(((CurrentState) resultAfterGameOver).isGameOver());
    }

    @Test
    @DisplayName("Игра засчитывает попытку, при неудачно отгаданной букве")
    void testIncreasingNumberOfAttempts() {
        String hiddenWord = "aaa";
        Session session = getSessionWithHiddenWord(new HiddenWordInformation(hiddenWord, Topic.Sport));
        Result result = session.guess('b');
        assertEquals(1, result.attempt());
        assertEquals("___", new String(result.state()));
        assertTrue(result instanceof FailedGuess);
    }

    @Test
    @DisplayName("Игра завершается при достижении максимально возможных попыток")
    void testGameOverAfterLosingAllAttempts() {
        String hiddenWord = "aaa";
        Session session = getSessionWithHiddenWord(new HiddenWordInformation(hiddenWord, Topic.Sport));
        for (int i = 0; i < session.getMaxAttempts(); ++i) {
            session.guess('b');
        }
        CurrentState currentState = session.getCurrentStateOfGame();
        assertTrue(currentState.isGameOver());
        assertEquals(currentState.attempt(), session.getMaxAttempts());
        assertEquals("___", new String(currentState.state()));
        Result result = session.guess('a');
        assertTrue(result instanceof CurrentState);
        assertTrue(((CurrentState) result).isGameOver());
        assertEquals(result.attempt(), session.getMaxAttempts());
        assertEquals("___", new String(result.state()));
    }

    private Session getSessionWithHiddenWord(HiddenWordInformation hiddenWordInf) {
        HangmanWordGenerator wordGenerator = new HangmanWordGenerator();
        wordGenerator.addWordToTopic(hiddenWordInf.word, hiddenWordInf.topic);
        Session session = new Session(hiddenWordInf.topic, wordGenerator);
        return session;
    }

    private record HiddenWordInformation(String word, Topic topic) {
    }
}
