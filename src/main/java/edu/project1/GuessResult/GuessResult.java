package edu.project1.GuessResult;

import org.jetbrains.annotations.NotNull;

sealed public interface GuessResult permits SuccessfulGuess, FailedGuess, Win, Defeat, InvalidFormatResult {
    char[] state();

    int attempt();

    int maxAttempts();

    @NotNull String message();
}
