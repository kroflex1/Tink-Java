package edu.hw6;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task4Test {
    private final File FILE_PATH =
        new File(new File("").getAbsoluteFile(), "/src/test/java/edu/hw6/resources/Task4Resources/text.txt");

    @AfterEach
    void cleanFile() {
        try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test() {
        Task4.writeToFile(FILE_PATH);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH)) ) {
            String resultText = bufferedReader.readLine();
            assertEquals(resultText, Task4.MESSAGE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
