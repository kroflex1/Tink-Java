package edu.project1;

import edu.project1.GuessResult.Defeat;
import edu.project1.GuessResult.FailedGuess;
import edu.project1.GuessResult.GuessResult;
import edu.project1.GuessResult.InvalidFormatResult;
import edu.project1.GuessResult.SuccessfulGuess;
import edu.project1.GuessResult.Win;
import edu.project1.Word.Topic;
import edu.project1.Word.WordGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private final Map<Character, List<Integer>> hiddenWordInformation;
    private final Map<Character, List<Integer>> userAnswerInformation;
    private final char[] hiddenWord;
    private final char[] userAnswer;
    private final int maxAttempts = 7;
    private int attempt = 0;
    private boolean isGameOver = false;

    public Session(Topic selectedTopic) {
        String word = WordGenerator.getRandomWordFromTopic(selectedTopic);
        hiddenWordInformation = convertWordToInformation(word);
        hiddenWord = word.toCharArray();

        userAnswer = new char[hiddenWord.length];
        Arrays.fill(userAnswer, '_');
        userAnswerInformation = new HashMap<>();
    }

    @NotNull
    public GuessResult guess(char guess) {
        if (isGameOver) {
            return giveUp();
        }
        GuessResult invalidFormatGuess = checkLetter(guess);
        if (invalidFormatGuess != null) {
            return invalidFormatGuess;
        }
        if (isHiddenWordContainLetter(guess)) {
            openNewLetterInUserAnswer(guess);
            if (Arrays.equals(hiddenWord, userAnswer)) {
                isGameOver = true;
                return new Win(userAnswer, attempt, maxAttempts, "You won!");
            }
            return new SuccessfulGuess(userAnswer, attempt, maxAttempts, "Hit!");
        }
        ++attempt;
        if (attempt != maxAttempts) {
            String failedMessage = String.format("Missed, mistake %d out of %d", attempt, maxAttempts);
            return new FailedGuess(userAnswer, attempt, maxAttempts, failedMessage);
        }
        return giveUp();
    }

    @NotNull
    public GuessResult giveUp() {
        isGameOver = true;
        String defeatMessage = String.format("You lost! The hidden word was %s", new String(hiddenWord));
        return new Defeat(userAnswer, attempt, maxAttempts, defeatMessage);
    }

    private boolean isHiddenWordContainLetter(char letter) {
        return hiddenWordInformation.containsKey(letter);
    }

    private void openNewLetterInUserAnswer(char letter) {
        userAnswerInformation.put(letter, hiddenWordInformation.get(letter));
        for (int indexOfNewLetter : userAnswerInformation.get(letter)) {
            userAnswer[indexOfNewLetter] = letter;
        }
    }

    @Nullable
    private InvalidFormatResult checkLetter(char letter) {
        if (userAnswerInformation.containsKey(letter)) {
            return new InvalidFormatResult(userAnswer, attempt, maxAttempts, "You have already guessed this letter");
        }
        if (!Character.isLetter(letter) || !((letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z'))) {
            return new InvalidFormatResult(userAnswer, attempt, maxAttempts, "Invalid letter format");
        }
        return null;
    }

    private Map<Character, List<Integer>> convertWordToInformation(String word) {
        Map<Character, List<Integer>> answerInformation = new HashMap<>();
        for (int i = 0; i < word.length(); ++i) {
            char currentSymbol = word.charAt(i);
            if (!answerInformation.containsKey(currentSymbol)) {
                answerInformation.put(currentSymbol, new ArrayList<>());
            }
            answerInformation.get(currentSymbol).add(i);
        }
        return answerInformation;
    }
}
