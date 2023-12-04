package edu.project1.Results;

public record Win(char[] state, int attempt, int maxAttempts, String message) implements Result {
}
