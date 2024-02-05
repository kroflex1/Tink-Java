package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task2Test {
    static Stream<Arguments> files() {
        String origin =  "Tinkoff.txt";

        List<String> firstStartFiles = List.of(origin);
        int firstNumberOfCopies = 3;
        List<String> firstExcept =
            new ArrayList<>(List.of(copyPath(origin, 1), copyPath(origin, 2), copyPath(origin, 3)));
        firstExcept.addAll(firstStartFiles);

        List<String> secondStartFiles = List.of(origin, copyPath(origin, 7), copyPath(origin, 8));
        int secondNumberOfCopies = 1;
        List<String> secondExcept = new ArrayList<>(List.of(copyPath(origin, 9)));
        secondExcept.addAll(secondStartFiles);

        List<String> thirdStartFiles = List.of(origin, "Tinkoff deception.txt", "someFile.txt");
        int thirdNumberOfCopies = 3;
        List<String> thirdExcept =
            new ArrayList<>(List.of(copyPath(origin, 1), copyPath(origin, 2), copyPath(origin, 3)));
        secondExcept.addAll(secondStartFiles);

        return Stream.of(
            arguments(origin, firstStartFiles, firstNumberOfCopies, firstExcept),
            arguments(origin, secondStartFiles, secondNumberOfCopies, secondExcept),
            arguments(origin, thirdStartFiles, thirdNumberOfCopies, thirdExcept)
        );
    }

    @ParameterizedTest
    @MethodSource("files")
    @DisplayName("Создание копий файла")
    void testCloneFiles(String originFileName, List<String> startFilesNames, int numberOfCopies, List<String> exceptFilesNames, @TempDir Path tempDir) {
        createFiles(startFilesNames, tempDir);
        for (int i = 0; i < numberOfCopies; ++i) {
            Task2.cloneFile(tempDir.resolve(originFileName));
        }
        List<Path> exceptFiles = new ArrayList<>();
        for(String exceptFileName: exceptFilesNames){
            exceptFiles.add(tempDir.resolve(exceptFileName));
        }
        assertTrue(isFilesInDirectory(exceptFiles));
    }

    @Test
    @DisplayName("Вызов ошибки при попытке клонировать несуществующий файл")
    void testNotExistFile(@TempDir Path tempDir) {
        assertThrows(IllegalArgumentException.class, () ->
            Task2.cloneFile(tempDir.resolve("nonExistentFile.txt")));
    }

    private static boolean isFilesInDirectory(List<Path> paths) {
        for (Path currentPath : paths) {
            if (!Files.exists(currentPath)) {
                return false;
            }
        }
        return true;
    }

    private static String copyPath(String source, int index) {
        String[] parts = source.split("\\.");
        String fileNameWithoutExtension = parts[0];
        String extension = "." + parts[1];
        String copyInformation = index == 1 ? " — копия" : String.format(" — копия (%d)", index);
        return fileNameWithoutExtension + copyInformation + extension;
    }

    private void createFiles(List<String> paths, Path dir) {
        for (String currentPath : paths) {
            try {
                Files.createFile(dir.resolve(currentPath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
