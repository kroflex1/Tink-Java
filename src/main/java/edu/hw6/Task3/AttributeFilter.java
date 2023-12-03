package edu.hw6.Task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AttributeFilter implements AbstractFilter {
    private final FileAttribute fileAttribute;

    public AttributeFilter(FileAttribute fileAttribute) {
        this.fileAttribute = fileAttribute;
    }

    @Override
    public boolean accept(Path entry) throws IOException {
        return switch (fileAttribute) {
            case READABLE -> Files.isReadable(entry);
            case WRITEABLE -> Files.isWritable(entry);
            case IS_DIRECTORY -> Files.isDirectory(entry);
            case EXECUTABLE -> Files.isExecutable(entry);
            case HIDDEN -> Files.isHidden(entry);
            case REGULAR_FILE -> Files.isRegularFile(entry);
            case SYMBOLIC_LINK -> Files.isSymbolicLink(entry);
        };
    }

    public enum FileAttribute {
        READABLE,
        WRITEABLE,
        IS_DIRECTORY,
        EXECUTABLE,
        HIDDEN,
        REGULAR_FILE,
        SYMBOLIC_LINK
    }
}
