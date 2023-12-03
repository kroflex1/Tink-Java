package edu.hw8.Task3;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadPasswordDecryptor extends PasswordDecryptor {
    private static final int DEFAULT_NUMBER_OF_THREADS = 4;

    @Override
    public Map<String, String> decryptPasswords(Path pathToPasswordsHashes) {
        return decryptPasswords(pathToPasswordsHashes, DEFAULT_NUMBER_OF_THREADS);
    }

    public Map<String, String> decryptPasswords(Path pathToPasswordsHashes, int numberOfThreads) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        passwordsHashesAndUsers = new ConcurrentHashMap<>(getPasswordsHashesAndUsersFromFile(pathToPasswordsHashes));
        usersAndPasswords = new ConcurrentHashMap<>();
        for (String symbol : alphabet) {
            executorService.execute(() -> {
                checkPassword(symbol);
                nextPassword(new StringBuilder(symbol));
            });
        }
        while (!passwordsHashesAndUsers.isEmpty()) {

        }
        executorService.shutdown();
        return usersAndPasswords;
    }
}
