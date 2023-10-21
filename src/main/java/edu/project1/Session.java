package edu.project1;

import edu.project1.Results.Result;
import edu.project1.Results.CurrentState;
import edu.project1.Results.Defeat;
import edu.project1.Results.FailedGuess;
import edu.project1.Results.InvalidFormatResult;
import edu.project1.Results.SuccessfulGuess;
import edu.project1.Results.Win;
import edu.project1.WordGenerator.Topic;
import edu.project1.WordGenerator.WordGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Session {
    private final Map<Character, List<Integer>> hiddenWordInformation;
    private final Map<Character, List<Integer>> userAnswerInformation;
    private final char[] hiddenWord;
    private final char[] userAnswer;
    private final int maxAttempts = 7;
    private int attempt = 0;
    private boolean isGameOver = false;

    public Session(Topic selectedTopic, WordGenerator wordGenerator) {
        String word = wordGenerator.getRandomWordFromTopic(selectedTopic).toLowerCase();
        hiddenWordInformation = convertWordToInformation(word);
        hiddenWord = word.toCharArray();

        userAnswer = new char[hiddenWord.length];
        Arrays.fill(userAnswer, '_');
        userAnswerInformation = new HashMap<>();
    }

    @NotNull
    public Result guess(char guess) {
        if (isGameOver) {
            return getCurrentStateOfGame();
        }
        guess = Character.toLowerCase(guess);
        Result invalidFormatGuess = checkLetter(guess);
        if (invalidFormatGuess != null) {
            return invalidFormatGuess;
        }
        if (tryOpenNewLetterInUserAnswer(guess)) {
            if (Arrays.equals(hiddenWord, userAnswer)) {
                isGameOver = true;
                String winMessage = String.format("You WON! Hidden word was '%s'", new String(hiddenWord));
                return new Win(userAnswer, attempt, maxAttempts, winMessage);
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
    public Result giveUp() {
        isGameOver = true;
        String defeatMessage = String.format("You LOST! The hidden word was '%s'", new String(hiddenWord));
        return new Defeat(userAnswer, attempt, maxAttempts, defeatMessage);
    }

    @NotNull
    public CurrentState getCurrentStateOfGame() {
        return new CurrentState(userAnswer, attempt, maxAttempts, "Current game state", isGameOver);
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    private boolean tryOpenNewLetterInUserAnswer(char letter) {
        if (hiddenWordInformation.containsKey(letter)) {
            userAnswerInformation.put(letter, hiddenWordInformation.get(letter));
            for (int indexOfNewLetter : userAnswerInformation.get(letter)) {
                userAnswer[indexOfNewLetter] = letter;
            }
            return true;
        }
        return false;
    }

    @Nullable
    private InvalidFormatResult checkLetter(char letter) {
        if (userAnswerInformation.containsKey(letter)) {
            return new InvalidFormatResult(userAnswer, attempt, maxAttempts, "You have already guessed this letter");
        }
        if (!Character.isLetter(letter) || !((letter >= 'a' && letter <= 'z'))) {
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
