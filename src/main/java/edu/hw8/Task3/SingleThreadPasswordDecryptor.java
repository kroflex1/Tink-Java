package edu.hw8.Task3;

import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class SingleThreadPasswordDecryptor extends PasswordDecryptor {
    protected Map<String, String> passwordsHashesAndUsers;
    protected Map<String, String> usersAndPasswords;

    @Override
    public Map<String, String> decryptPasswords(Path pathToPasswordsHashes) {
        passwordsHashesAndUsers = getPasswordsHashesAndUsersFromFile(pathToPasswordsHashes);
        usersAndPasswords = new HashMap<>();
        while (!passwordsHashesAndUsers.isEmpty()) {
            nextPassword();
        }
        return usersAndPasswords;
    }

    @Override
    protected void nextPassword() {
        nextPassword(new StringBuilder());
    }

    protected void nextPassword(StringBuilder prefix) {
        if (passwordsHashesAndUsers.isEmpty() || prefix.length() == MAX_PASSWORD_SIZE) {
            return;
        }
        for (String symbol : alphabet) {
            prefix.append(symbol);
            checkPassword(prefix.toString());
            System.out.print(prefix.toString() + '\n');
            nextPassword(prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    private void checkPassword(String password) {
        messageDigest.update(password.getBytes());
        byte[] digest = messageDigest.digest();
        String passwordHash = toHexString(digest);
        if (passwordsHashesAndUsers.containsKey(passwordHash)) {
            usersAndPasswords.put(passwordsHashesAndUsers.get(passwordHash), password);
            passwordsHashesAndUsers.remove(passwordHash);
        }
    }
}
