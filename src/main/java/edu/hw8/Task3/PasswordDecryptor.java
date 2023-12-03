package edu.hw8.Task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class PasswordDecryptor {
    protected final static int MAX_PASSWORD_SIZE = 4;
    protected final List<String> alphabet;

    protected final MessageDigest messageDigest;

    public PasswordDecryptor() {
        alphabet = new ArrayList<>();
        for (char symbol = 'a'; symbol <= 'z'; ++symbol) {
            alphabet.add(String.valueOf(symbol));
            alphabet.add(String.valueOf((char) (symbol - 32)));
        }
        for (int symbol = 0; symbol <= 9; ++symbol) {
            alphabet.add(String.valueOf(symbol));
        }
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    abstract public Map<String, String> decryptPasswords(Path pathToPasswordsHashes);

    abstract protected void nextPassword();

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

    protected String toHexString(byte[] bytes) {
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
}
