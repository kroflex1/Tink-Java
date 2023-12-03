package edu.hw6;

import edu.hw6.Task3.AttributeFilter;
import edu.hw6.Task3.ExtensionFilter;
import edu.hw6.Task3.MagicInitialIdentityFilter;
import edu.hw6.Task3.RegexFilter;
import edu.hw6.Task3.SizeFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static java.nio.file.Files.newDirectoryStream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task3Test {

    private static final Path ROOT =
        Paths.get(Paths.get("").toAbsolutePath().toString(), "/src/test/java/edu/hw6/resources/Task3Resources");

    static Stream<Arguments> attributeDirectories() {
        Path directoryWithReadableFiles = Paths.get(ROOT.toString(), "/readableAndWritableFiles");
        Path directoryWithWritableFiles = Paths.get(ROOT.toString(), "/readableAndWritableFiles");
        return Stream.of(
            arguments(directoryWithReadableFiles, AttributeFilter.FileAttribute.READABLE),
            arguments(directoryWithWritableFiles, AttributeFilter.FileAttribute.WRITEABLE)
        );
    }

    static Stream<Arguments> sizeDirectories() {
        Path directoryWithFilesLargerThan100 = Paths.get(ROOT.toString(), "/filesLargerThan100");
        Path directoryWithFilesLessThan100 = Paths.get(ROOT.toString(), "/filesLessThan100");
        return Stream.of(
            arguments(directoryWithFilesLargerThan100, SizeFilter.Comparison.LARGER_THAN, 100L),
            arguments(directoryWithFilesLessThan100, SizeFilter.Comparison.LESS_THAN, 100L)
        );
    }

    static Stream<Arguments> extensionDirectories() {
        Path directoryWithFilesTXT = Paths.get(ROOT.toString(), "/txtFiles");
        Path directoryWithFilesXLSX = Paths.get(ROOT.toString(), "/xlsxFiles");
        return Stream.of(
            arguments(directoryWithFilesTXT, "txt"),
            arguments(directoryWithFilesXLSX, "xlsx")
        );
    }

    static Stream<Arguments> regexDirectories() {
        Path directoryWithFilesDigitsInName = Paths.get(ROOT.toString(), "/filesWithDigitInName");
        Path directoryWithFilesTinkoffInName = Paths.get(ROOT.toString(), "/FilesWithTinkoffInInName");
        return Stream.of(
            arguments(directoryWithFilesDigitsInName, "[1-9]+"),
            arguments(directoryWithFilesTinkoffInName, "Tinkoff")
        );
    }

    static Stream<Arguments> magicDirectories() {
        Path directoryWithFilesPng = Paths.get(ROOT.toString(), "/pngFiles");
        return Stream.of(
            arguments(directoryWithFilesPng, (byte) 0x89)
        );
    }

    @ParameterizedTest
    @MethodSource("attributeDirectories")
    void testAttributeFilter(Path directory, AttributeFilter.FileAttribute fileAttribute) throws IOException {
        AttributeFilter attributeFilter = new AttributeFilter(fileAttribute);
        try (DirectoryStream<Path> resultStream = newDirectoryStream(directory);
             DirectoryStream<Path> exceptStream = newDirectoryStream(directory, attributeFilter)) {
            assertTrue(isEqualDirectoryStreams(resultStream, exceptStream));
        }
    }

    @ParameterizedTest
    @MethodSource("sizeDirectories")
    void testSizeFilter(Path directory, SizeFilter.Comparison comparison, long size) throws IOException {
        SizeFilter sizeFilter = new SizeFilter(comparison, size);
        try (DirectoryStream<Path> resultStream = newDirectoryStream(directory);
             DirectoryStream<Path> exceptStream = newDirectoryStream(directory, sizeFilter)) {
            assertTrue(isEqualDirectoryStreams(resultStream, exceptStream));
        }
    }

    @ParameterizedTest
    @MethodSource("extensionDirectories")
    void testExtensionFilter(Path directory, String fileExtension) throws IOException {
        ExtensionFilter extensionFilter = new ExtensionFilter(fileExtension);
        try (DirectoryStream<Path> resultStream = newDirectoryStream(directory);
             DirectoryStream<Path> exceptStream = newDirectoryStream(directory, extensionFilter)) {
            assertTrue(isEqualDirectoryStreams(resultStream, exceptStream));
        }
    }

    @ParameterizedTest
    @MethodSource("regexDirectories")
    void testRegexFilter(Path directory, String regexPattern) throws IOException {
        RegexFilter regexFilter = new RegexFilter(regexPattern);
        try (DirectoryStream<Path> resultStream = newDirectoryStream(directory);
             DirectoryStream<Path> exceptStream = newDirectoryStream(directory, regexFilter)) {
            assertTrue(isEqualDirectoryStreams(resultStream, exceptStream));
        }
    }

    @ParameterizedTest
    @MethodSource("magicDirectories")
    void testMagicFilter(Path directory, byte magicDigit) throws IOException {
        MagicInitialIdentityFilter magicInitialIdentityFilter = new MagicInitialIdentityFilter(magicDigit);
        try (DirectoryStream<Path> resultStream = newDirectoryStream(directory);
             DirectoryStream<Path> exceptStream = newDirectoryStream(directory, magicInitialIdentityFilter)) {
            assertTrue(isEqualDirectoryStreams(resultStream, exceptStream));
        }
    }

    @Test
    void testChainFilter() throws IOException {
        Path directory = Paths.get(ROOT.toString(), "/files-larger-than-100-and-with-digits-in-name");
        DirectoryStream.Filter<Path> goodChainFilter =
            new SizeFilter(SizeFilter.Comparison.LARGER_THAN, 100).add(new RegexFilter("[1-9]+"));
        try (DirectoryStream<Path> resultStream = newDirectoryStream(directory);
             DirectoryStream<Path> exceptStream = newDirectoryStream(directory, goodChainFilter)) {
            assertTrue(isEqualDirectoryStreams(resultStream, exceptStream));
        }

        DirectoryStream.Filter<Path> badChainFilter =
            new SizeFilter(SizeFilter.Comparison.LESS_THAN, 50).add(new RegexFilter("[1-9]+"));
        try (DirectoryStream<Path> resultStream = newDirectoryStream(directory);
             DirectoryStream<Path> exceptStream = newDirectoryStream(directory, badChainFilter)) {
            assertFalse(isEqualDirectoryStreams(resultStream, exceptStream));
        }
    }

    @Test
    void testChainFilter2() throws IOException {
        Path directory = Paths.get(ROOT.toString(), "/readable-png-files-larger-than-100-and-with-tink-in-name");
        DirectoryStream.Filter<Path> goodChainFilter = new AttributeFilter(AttributeFilter.FileAttribute.READABLE)
            .add(new SizeFilter(SizeFilter.Comparison.LARGER_THAN, 100))
            .add(new MagicInitialIdentityFilter((byte)0x89))
            .add(new ExtensionFilter("png"))
            .add(new RegexFilter("tink"));
        try (DirectoryStream<Path> resultStream = newDirectoryStream(directory);
             DirectoryStream<Path> exceptStream = newDirectoryStream(directory, goodChainFilter)) {
            assertTrue(isEqualDirectoryStreams(resultStream, exceptStream));
        }
    }

    private boolean isEqualDirectoryStreams(DirectoryStream<Path> first, DirectoryStream<Path> second) {
        Set<Path> firstElements = new HashSet<>();
        for (Path child : first) {
            firstElements.add(child);
        }
        Set<Path> secondElements = new HashSet<>();
        for (Path child : second) {
            secondElements.add(child);
        }

        return firstElements.equals(secondElements);
    }

}
