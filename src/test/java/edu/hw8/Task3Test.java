package edu.hw8;

import edu.hw8.Task3.MultiThreadPasswordDecryptor;
import edu.hw8.Task3.PasswordDecryptor;
import edu.hw8.Task3.SingleThreadPasswordDecryptor;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task3Test {
    static Stream<Arguments> users() {
        List<UserInf> users = List.of(
            new UserInf("vova", "a", "0cc175b9c0f1b6a831c399e269772661"),
            new UserInf("katya", "v", "9e3669d19b675bd57058fd4664205d2a"),
            new UserInf("borya", "cba", "3944b025c9ca7eec3154b44666ae04a0"),
            new UserInf("lena", "4Yne", "8cd4b8e00d2e8c94caa16ddf11eff1a8"),
            new UserInf("vova", "tR7L", "2a716f84edd25b0a3c7a94881d588114")
        );
        return Stream.of(
            arguments(List.of(users.get(0))),
            arguments(List.of(users.get(0), users.get(1))),
            arguments(List.of(users.get(0), users.get(1), users.get(2))),
            arguments(List.of(users.get(0), users.get(1), users.get(2), users.get(3))),
            arguments(List.of(users.get(4))
        ));
    }


    @ParameterizedTest
    @MethodSource("users")
    void testMultiThreadPasswordDecryptor(List<UserInf> users, @TempDir Path tempDir) {
        Path passwordsHashAndUsersPath = createFileWithPasswordsHashAndUsers(tempDir, users);
        PasswordDecryptor passwordDecryptor = new MultiThreadPasswordDecryptor();
        Map<String, String> result = passwordDecryptor.decryptPasswords(passwordsHashAndUsersPath);
        assertTrue(isPasswordsCorrect(users, result));
    }

    @ParameterizedTest
    @MethodSource("users")
    void testSingleThreadPasswordDecryptor(List<UserInf> users, @TempDir Path tempDir) {
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
