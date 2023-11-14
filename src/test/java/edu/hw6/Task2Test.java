package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task2Test {

    private static final Path ROOT = Paths.get(Paths.get("").toAbsolutePath().toString(), "/src/test/java/edu/hw6");

    @BeforeEach
    void removeAllFilesInDirectory() {
        try (Stream<Path> stream = Files.list(ROOT)) {
            List<Path> paths = stream.toList();
            for (Path currentPath : paths) {
                Files.delete(currentPath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Stream<Arguments> files() {
        Path origin = Paths.get(ROOT.toString(), "/Tinkoff Bank Biggest Secret.txt");

        List<Path> firstStartFiles = List.of(origin);
        int firstNumberOfCopies = 3;
        List<Path> firstExcept = new ArrayList<>();
        firstExcept.add(copyPath(origin, 1));
        firstExcept.add(copyPath(origin, 2));
        firstExcept.add(copyPath(origin, 3));
        firstExcept.addAll(firstStartFiles);

        List<Path> secondStartFiles = List.of(origin, copyPath(origin, 7), copyPath(origin, 8));
        int secondNumberOfCopies = 1;
        List<Path> secondExcept = new ArrayList<>() {{
            add(copyPath(origin, 9));
        }};
        secondExcept.addAll(secondStartFiles);

        return Stream.of(
            arguments(origin, firstStartFiles, firstNumberOfCopies, firstExcept),
            arguments(origin, secondStartFiles, secondNumberOfCopies, secondExcept)
        );
    }

    @ParameterizedTest
    @MethodSource("files")
    void testCloneFiles(Path origin, List<Path> startFiles, int numberOfCopies, List<Path> except) {
        createFiles(startFiles);
        for (int i = 0; i < numberOfCopies; ++i) {
            Task2.cloneFile(origin);
        }
        assertTrue(isFilesInDirectory(except));
    }

    private static boolean isFilesInDirectory(List<Path> paths) {
        for (Path currentPath : paths) {
            if (!Files.exists(currentPath)) {
                return false;
            }
        }
        return true;
    }

    private static Path copyPath(Path source, int index) {
        String[] parts = source.getFileName().toString().split(".");
        String fileNameWithoutExtension = parts[0];
        String extension = "." + parts[1];
        String copyInformation = index == 1 ? " — копия" : String.format(" — копия (%d)", index);
        return Paths.get(source.getParent().toString(), fileNameWithoutExtension + copyInformation + extension);
    }

    private void createFiles(List<Path> paths) {
        for (Path currentPath : paths) {
            try {
                Files.createFile(currentPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
