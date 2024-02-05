package edu.hw8.Task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PasswordDecryptor {
    protected final static int MAX_PASSWORD_SIZE = 4;
    protected final List<String> alphabet;
    protected Map<String, String> passwordsHashesAndUsers;
    protected Map<String, String> usersAndPasswords;

    @SuppressWarnings("MagicNumber")
    public PasswordDecryptor() {
        alphabet = new ArrayList<>();
        for (char symbol = 'a'; symbol <= 'z'; ++symbol) {
            alphabet.add(String.valueOf(symbol));
            alphabet.add(String.valueOf((char) (symbol - 32)));
        }
        for (int symbol = 0; symbol <= 9; ++symbol) {
            alphabet.add(String.valueOf(symbol));
        }
    }

    abstract public Map<String, String> decryptPasswords(Path pathToPasswordsHashes);

    protected void nextPassword(StringBuilder prefix) {
        for (String symbol : alphabet) {
            if (passwordsHashesAndUsers.isEmpty() || prefix.length() == MAX_PASSWORD_SIZE) {
                return;
            }
            prefix.append(symbol);
            checkPassword(prefix.toString());
            nextPassword(prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    protected void checkPassword(String password) {
        MessageDigest messageDigest1 = null;
        try {
            messageDigest1 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        messageDigest1.update(password.getBytes());
        byte[] digest = messageDigest1.digest();
        String passwordHash = toHexString(digest);
        if (passwordsHashesAndUsers.containsKey(passwordHash)) {
            usersAndPasswords.put(passwordsHashesAndUsers.get(passwordHash), password);
            passwordsHashesAndUsers.remove(passwordHash);
        }
    }

    @SuppressWarnings("MagicNumber")
    private String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    protected Map<String, String> getPasswordsHashesAndUsersFromFile(Path pathToPasswordsHashes) {
        Map<String, String> result = new HashMap<>();
        try (BufferedReader br = Files.newBufferedReader(pathToPasswordsHashes)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                result.put(parts[1], parts[0]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
