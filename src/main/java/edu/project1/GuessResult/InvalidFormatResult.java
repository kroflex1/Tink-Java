package edu.project1.GuessResult;

public record InvalidFormatResult(char[] state, int attempt, int maxAttempts, String message) implements GuessResult {
}
