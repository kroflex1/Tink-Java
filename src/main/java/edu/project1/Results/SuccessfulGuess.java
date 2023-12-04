package edu.project1.Results;

public record SuccessfulGuess(char[] state, int attempt, int maxAttempts, String message) implements Result {

}
