package edu.hw6.Task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String> {
    private final HashMap<String, String> hashMap;
    private final Path dataStoragePath;
    private static final String FILE_SEPARATOR = System.getProperty("line.separator");

    public DiskMap(Path path) {
        hashMap = new HashMap<>();
        dataStoragePath = path;
        if (!isValidPath(path)) {
            throw new IllegalArgumentException("Invalid file path");
        }
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return hashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return hashMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return hashMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return hashMap.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return hashMap.get(key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        checkKeyAndValue(key, value);
        String oldValue = hashMap.get(key);
        if (hashMap.containsKey(key)) {
            changeValueInFile(key, value);
        } else {
            addNewPairInFile(key, value);
        }
        hashMap.put(key, value);
        return oldValue;
    }

    @Override
    public String remove(Object key) {
        String oldValue = hashMap.get(key);
        if (hashMap.containsKey(key)) {
            hashMap.remove(key);
            removePairInFile((String) key);
        }
        return oldValue;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        for (Entry<? extends String, ? extends String> entry : m.entrySet()) {
            checkKeyAndValue(entry.getKey(), entry.getValue());
            if (hashMap.containsKey(entry.getKey())) {
                changeValueInFile(entry.getKey(), entry.getValue());
            } else {
                addNewPairInFile(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void clear() {
        try (BufferedWriter writer = Files.newBufferedWriter(dataStoragePath)) {
            writer.write("");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return hashMap.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return hashMap.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return hashMap.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DiskMap) {
            return hashMap.equals(o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return hashMap.hashCode();
    }

    private void changeValueInFile(String key, String newValue) {
        removePairInFile(key);
        addNewPairInFile(key, newValue);
    }

    private void addNewPairInFile(String key, String value) {
        try (BufferedWriter reader = new BufferedWriter(Files.newBufferedWriter(dataStoragePath))) {
            reader.write(key + ":" + value + FILE_SEPARATOR);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void removePairInFile(String key) {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("tempfiles", ".tmp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader reader = Files.newBufferedReader(dataStoragePath);
             BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] items = currentLine.split(":");
                if (items[0].equals(key)) {
                    continue;
                }
                writer.write(currentLine + FILE_SEPARATOR);
            }
            Files.move(tempFile, dataStoragePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidPath(Path path) {
        return !Files.exists(path) && !Files.isDirectory(path);
    }

    private void checkKeyAndValue(String key, String value) throws IllegalArgumentException {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value can`t be null");
        }
        if (key.contains(":") || value.contains(":")) {
            throw new IllegalArgumentException("Key and value can`t contain ':' symbol");
        }
    }
}