package edu.hw8;

import edu.hw8.Task3.PasswordDecryptor;
import edu.hw8.Task3.SingleThreadPasswordDecryptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task3Test {
    static Stream<Arguments> users() {
        List<UserInf> users = List.of(
            new UserInf("vova", "a", "0cc175b9c0f1b6a831c399e269772661"),
            new UserInf("borya", "abc", "900150983cd24fb0d6963f7d28e17f72"),
            new UserInf("katya", "7l8e", "f025867a73a3ca6d998283df60be9ddd"),
            new UserInf("lena", "4Yne", "8cd4b8e00d2e8c94caa16ddf11eff1a8")
        );
        return Stream.of(
//            arguments(List.of(users.get(0)))
//            arguments(List.of(users.get(0), users.get(1)))
//            arguments(List.of(users.get(0), users.get(1), users.get(2))),
            arguments(List.of(users.get(0), users.get(1), users.get(2), users.get(3)))
        );
    }

    @ParameterizedTest
    @MethodSource("users")
    void test(List<UserInf> users, @TempDir Path tempDir) {
        Path passwordsHashAndUsersPath = createFileWithPasswordsHashAndUsers(tempDir, users);
        PasswordDecryptor passwordDecryptor = new SingleThreadPasswordDecryptor();
        Map<String, String> result = passwordDecryptor.decryptPasswords(passwordsHashAndUsersPath);
        assertTrue(isPasswordsCorrect(users, result));
    }

    private boolean isPasswordsCorrect(List<UserInf> users, Map<String, String> usersAndPasswords) {
        if (users.size() != usersAndPasswords.size()) {
            return false;
        }
        for (UserInf currentUser : users) {
            if (!currentUser.password.equals(usersAndPasswords.get(currentUser.name))) {
                return false;
            }
        }
        return true;

    }

    static Path createFileWithPasswordsHashAndUsers(Path directory, List<UserInf> users) {
        Path passwordsPath = directory.resolve("passwords.txt");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(passwordsPath)) {
            for (UserInf currentUser : users) {
                bufferedWriter.write(currentUser.name + " " + currentUser.passwordHash + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return passwordsPath;
    }

    private record UserInf(String name, String password, String passwordHash) {

    }
}
