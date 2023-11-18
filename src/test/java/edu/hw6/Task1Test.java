package edu.hw6;

import edu.hw6.Task1.DiskMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task1Test {
    private static final Path FILE_PATH =
        Paths.get(
            Paths.get("").toAbsolutePath().toString(),
            "/src/test/java/edu/hw6/resources/Task1Resources/diskMap.txt"
        );

    static Stream<Arguments> validPairs() {
        Map<String, String> firstMap = new HashMap<>() {{
            put("hello", "hey");
            put("1234", "4321");
            put("LOL", "Kek");
            put("Borya", "Varya");
        }};
        Map<String, String> secondMap = new HashMap<>() {{
            put("hello", "hey");
        }};
        Map<String, String> thirdMap = new HashMap<>() {{
            put("hello", "hey");
            put("Miro", "hey");
            put("Nuna", "lemo");
            put("mem", "lem");
            put("apple", "fruit");
            put("game of the year", "BGT3");
        }};
        return Stream.of(
            arguments(firstMap),
            arguments(secondMap),
            arguments(thirdMap)
        );
    }

    static Stream<Arguments> invalidPairs() {
        Map<String, String> firstMap = new HashMap<>() {{
            put("hello", null);
            put(null, "4321");
            put(null, null);
        }};
        Map<String, String> secondMap = new HashMap<>() {{
            put("hello:", "hey");
            put("apple", ":fruit");
            put(":Nuna", "Luna");
            put("Nuna", "Luna:");
            put("good game:", ":minecraft");
        }};
        return Stream.of(
            arguments(firstMap),
            arguments(secondMap)
        );
    }

    @AfterEach
    void removeFile() {
        if (Files.exists(FILE_PATH)) {
            try {
                Files.delete(FILE_PATH);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Test
    @DisplayName("Проверка создания файла")
    void testCreateFile() {
        DiskMap diskMap = new DiskMap(FILE_PATH);
        assertTrue(Files.exists(FILE_PATH));
    }

    @Test
    @DisplayName("Вызов ошибки, если в заданном пути уже существует файл")
    void testInvalidFilePath() {
        try {
            Files.createFile(FILE_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new DiskMap(FILE_PATH));
        assertEquals("Invalid file path", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("validPairs")
    @DisplayName("Добавление эелементов")
    void testPutElements(Map<String, String> map) {
        DiskMap diskMap = new DiskMap(FILE_PATH);
        for (Map.Entry<? extends String, ? extends String> entry : map.entrySet()) {
            diskMap.put(entry.getKey(), entry.getValue());
        }
        assertTrue(isPairsInFile(map.entrySet()));
        assertFalse(isHaveFileEmptyLines());
    }

    @ParameterizedTest
    @MethodSource("validPairs")
    @DisplayName("Добавление эелементов с повторяющимися ключами")
    void testPutElementsWithSameKeys(Map<String, String> map) {
        DiskMap diskMap = new DiskMap(FILE_PATH);
        for (Map.Entry<? extends String, ? extends String> entry : map.entrySet()) {
            diskMap.put(entry.getKey(), entry.getValue());
        }
        String randomKey = getRandomKey(map);
        String newValue = "blablablabla";
        diskMap.put(randomKey, newValue);
        map.put(randomKey, newValue);

        assertTrue(isPairsInFile(map.entrySet()));
        assertFalse(isHaveFileEmptyLines());
    }

    @ParameterizedTest
    @MethodSource("validPairs")
    @DisplayName("Удаление элементов поштучно")
    void testRemoveElements(Map<String, String> map) {
        List<String> removeKeys = List.of(getRandomKey(map), getRandomKey(map));

        DiskMap diskMap = new DiskMap(FILE_PATH);
        for (Map.Entry<? extends String, ? extends String> entry : map.entrySet()) {
            diskMap.put(entry.getKey(), entry.getValue());
        }
        for (String removeKey : removeKeys) {
            diskMap.remove(removeKey);
            map.remove(removeKey);
        }
        assertTrue(isPairsInFile(map.entrySet()));
        assertFalse(isHaveFileEmptyLines());
    }

    @ParameterizedTest
    @MethodSource("validPairs")
    @DisplayName("Добавление сразу всех элементов из другого Map")
    void testPutAllElementsFromAnotherMap(Map<String, String> map) {
        DiskMap diskMap = new DiskMap(FILE_PATH);
        diskMap.putAll(map);
        assertTrue(isPairsInFile(map.entrySet()));
        assertFalse(isHaveFileEmptyLines());
    }

    @ParameterizedTest
    @MethodSource("validPairs")
    @DisplayName("Очистка файла")
    void testClearFile(Map<String, String> map) {
        DiskMap diskMap = new DiskMap(FILE_PATH);
        diskMap.putAll(map);
        diskMap.clear();
        assertTrue(diskMap.isEmpty());
        assertFalse(isHaveFileEmptyLines());
    }

    @ParameterizedTest
    @MethodSource("invalidPairs")
    @DisplayName("Вызов ошибки при некорректных значениях")
    void testTryPutInvalidElements(Map<String, String> map) {
        DiskMap diskMap = new DiskMap(FILE_PATH);
        for (Map.Entry<? extends String, ? extends String> entry : map.entrySet()) {
            assertThrows(IllegalArgumentException.class, () ->
                diskMap.put(entry.getKey(), entry.getValue()));
        }
        assertThrows(IllegalArgumentException.class, () ->
            diskMap.putAll(map));
        assertTrue(diskMap.isEmpty());
        assertFalse(isHaveFileEmptyLines());
    }

    boolean isPairsInFile(Set<Map.Entry<String, String>> pairs) {
        try (BufferedReader reader = Files.newBufferedReader(FILE_PATH)) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] keyAndValue = currentLine.split(":");
                Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>(keyAndValue[0], keyAndValue[1]);
                if (!pairs.contains(entry)) {
                    return false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    boolean isHaveFileEmptyLines() {
        try (BufferedReader reader = Files.newBufferedReader(FILE_PATH)) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.isEmpty()) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    String getRandomKey(Map<String, String> map) {
        List<String> keysAsArray = new ArrayList<String>(map.keySet());
        Random random = new Random();
        return keysAsArray.get(random.nextInt(keysAsArray.size()));
    }
}
