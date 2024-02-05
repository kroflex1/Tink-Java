package edu.hw6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task4Test {
    @Test
    void test(@TempDir Path tempDir) {
        Path pathToFile = tempDir.resolve("text.txt");
        Task4.writeToFile(pathToFile.toFile());
        try (BufferedReader bufferedReader = Files.newBufferedReader(pathToFile)) {
            String resultText = bufferedReader.readLine();
            assertEquals(resultText, Task4.MESSAGE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
