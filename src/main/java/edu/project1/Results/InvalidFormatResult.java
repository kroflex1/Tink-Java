package edu.project1.Results;

public record InvalidFormatResult(char[] state, int attempt, int maxAttempts, String message) implements Result {
}
