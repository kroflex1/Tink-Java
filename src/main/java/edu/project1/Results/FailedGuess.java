package edu.project1.Results;

public record FailedGuess(char[] state, int attempt, int maxAttempts, String message) implements Result {
}
