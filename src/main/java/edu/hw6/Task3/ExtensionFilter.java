package edu.hw6.Task3;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class ExtensionFilter implements AbstractFilter {
    private final String extension;

    public ExtensionFilter(String extension) {
        this.extension = extension;
        if (!isValidExtension(extension)) {
            throw new IllegalArgumentException("Invalid extension format");
        }
    }

    @Override
    public boolean accept(Path entry) throws IOException {
        String[] parts = entry.getFileName().toString().split("\\.");
        return parts[1].equals(extension);
    }

    private boolean isValidExtension(String extension) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");
        return pattern.matcher(extension).matches();
    }
}
