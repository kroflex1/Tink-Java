package edu.project1.GuessResult;

public record FailedGuess(char[] state, int attempt, int maxAttempts, String message) implements GuessResult {
}
