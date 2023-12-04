package edu.project1.Results;

public record CurrentState(char[] state, int attempt, int maxAttempts, String message, boolean isGameOver)
    implements Result {
}
