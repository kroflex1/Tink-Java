package edu.hw6;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

public class Task4 {
    public static final String MESSAGE = "Programming is learned by writing programs. ― Brian Kernighan";

    public static void writeToFile(File file) {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            try (CheckedOutputStream checkedSum = new CheckedOutputStream(outputStream, new Adler32())) {
                try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedSum)) {
                    try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                        bufferedOutputStream,
                        StandardCharsets.UTF_8
                    )) {
                        try (PrintWriter printWriter = new PrintWriter(outputStreamWriter)) {
                            printWriter.print(MESSAGE);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Task4() {

    }
}
