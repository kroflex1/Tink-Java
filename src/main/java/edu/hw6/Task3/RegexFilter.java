package edu.hw6.Task3;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class RegexFilter implements AbstractFilter {
    private final Pattern fileNamePattern;

    public RegexFilter(String regexPattern) {
        this.fileNamePattern = Pattern.compile(regexPattern);
    }

    @Override
    public boolean accept(Path entry) throws IOException {
        return fileNamePattern.matcher(entry.getFileName().toString()).find();
    }
}
