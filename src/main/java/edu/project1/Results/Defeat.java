package edu.project1.Results;

public record Defeat(char[] state, int attempt, int maxAttempts, String message) implements Result {
}
