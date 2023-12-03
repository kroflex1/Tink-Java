package edu.hw8.Task3;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SingleThreadPasswordDecryptor extends PasswordDecryptor {

    @Override
    public Map<String, String> decryptPasswords(Path pathToPasswordsHashes) {
        passwordsHashesAndUsers = getPasswordsHashesAndUsersFromFile(pathToPasswordsHashes);
        usersAndPasswords = new HashMap<>();
        nextPassword(new StringBuilder());
        return usersAndPasswords;
    }

}
