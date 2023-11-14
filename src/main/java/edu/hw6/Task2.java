package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

public class Task2 {
    private static final String COPY_FLAG = " — копия";
    private static final String FILE_NAME_SEPARATOR = "\\.";

    public static void cloneFile(Path path) {
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Path to the file is incorrectly");
        }
        int maxCopyIndex = getMaxCopyIndex(path);
        String pathToCopy = String.format(
            "%s/%s%s%s%s",
            path.getParent().toString(),
            removeExtensionFromFilename(path.getFileName()),
            COPY_FLAG,
            maxCopyIndex == 0 ? "" : " (" + (maxCopyIndex + 1) + ")",
            getFileExtension(path)
        );

        try {
            Files.copy(path, Paths.get(pathToCopy));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getMaxCopyIndex(Path pathToFile) {

        try (Stream<Path> files = Files.list(pathToFile.getParent())) {
            var x = files
                .filter(file -> !Files.isDirectory(file))
                .map(currentPath -> tryGetIndexOfCopyFile(pathToFile, currentPath))
                .filter(Objects::nonNull).toList();
            return x.stream().max(Comparator.comparingInt(value -> value)).orElse(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    private static Integer tryGetIndexOfCopyFile(Path source, Path intendedCopyFile) {
        String sourceFileNameWithoutExtension = removeExtensionFromFilename(source);
        Pattern pattern = Pattern.compile(sourceFileNameWithoutExtension + COPY_FLAG + "( \\((\\d+)\\))*");
        Matcher matcher = pattern.matcher(removeExtensionFromFilename(intendedCopyFile.getFileName()));
        if (matcher.matches()) {
            return matcher.group(2) == null ? 1 : Integer.parseInt(matcher.group(2));
        }
        return null;
    }

    private static String removeExtensionFromFilename(Path file) {
        String[] parts = file.getFileName().toString().split(FILE_NAME_SEPARATOR);
        return parts[0];
    }

    private static String getFileExtension(Path file) {
        String[] parts = file.getFileName().toString().split(FILE_NAME_SEPARATOR);
        return "." + parts[1];
    }

    private Task2() {

    }

}
