package edu.project1.GuessResult;

import org.jetbrains.annotations.NotNull;

public record SuccessfulGuess(char[] state, int attempt, int maxAttempts, String message) implements GuessResult {

}
