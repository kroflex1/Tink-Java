package edu.hw6;

import org.jetbrains.annotations.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Task2 {
    private static final String COPY_FLAG = " — копия ";

    public static void cloneFile(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Path to the file is incorrectly");
        }
        int maxCopyIndex = getMaxCopyIndex(path);
        String pathToCopy =
            path.getParent().toString() + removeExtensionFromFilename(path.getFileName()) + COPY_FLAG + "(" +
                maxCopyIndex + ")" + getFileExtension(path);
        try {
            Files.copy(path, Paths.get(pathToCopy));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getMaxCopyIndex(Path pathToFile) {
        try (Stream<Path> stream = Files.list(pathToFile)) {
            return stream
                .filter(file -> !Files.isDirectory(file))
                .mapToInt(currentPath -> tryGetIndexOfCopyFile(pathToFile, currentPath))
                .max().orElse(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    private static Integer tryGetIndexOfCopyFile(Path source, Path intendedCopyFile) {
        String sourceFileNameWithoutExtension = removeExtensionFromFilename(source);
        Pattern pattern = Pattern.compile(sourceFileNameWithoutExtension + COPY_FLAG + "(\\((\\d+)\\))*");
        Matcher matcher = pattern.matcher(removeExtensionFromFilename(intendedCopyFile));
        if (matcher.matches()) {
            return matcher.group(1) == null ? 1 : Integer.parseInt(matcher.group(1));
        }
        return null;
    }

    private static String removeExtensionFromFilename(Path file) {
        String[] parts = file.getFileName().toString().split("\\.");
        return parts[0];
    }

    private static String getFileExtension(Path file) {
        String[] parts = file.getFileName().toString().split("\\.");
        return parts[1];
    }

}
