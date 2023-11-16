package edu.hw6.Task3;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {

    default AbstractFilter add(AbstractFilter abstractFilter) {
        return (Path entry) -> abstractFilter.accept(entry) && this.accept(entry);
    }
}
