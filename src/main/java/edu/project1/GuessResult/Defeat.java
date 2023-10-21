package edu.project1.GuessResult;

public record Defeat(char[] state, int attempt, int maxAttempts, String message) implements GuessResult{
}
