package edu.project1.Results;

import org.jetbrains.annotations.NotNull;

sealed public interface Result permits SuccessfulGuess, FailedGuess, Win, Defeat, InvalidFormatResult, CurrentState {
    char[] state();

    int attempt();

    int maxAttempts();

    @NotNull String message();
}
