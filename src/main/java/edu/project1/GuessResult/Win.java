package edu.project1.GuessResult;

public record Win(char[] state, int attempt, int maxAttempts, String message) implements GuessResult {
}
