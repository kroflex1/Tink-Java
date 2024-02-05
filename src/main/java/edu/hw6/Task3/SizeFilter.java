package edu.hw6.Task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SizeFilter implements AbstractFilter {
    private final Comparison comparison;
    private final long size;

    public SizeFilter(Comparison comparison, long size) {
        this.comparison = comparison;
        this.size = size;

    }

    @Override
    public boolean accept(Path entry) throws IOException {
        return switch (comparison) {
            case LARGER_THAN -> Files.size(entry) > size;
            case LARGER_THAN_OR_EQUAL -> Files.size(entry) >= size;
            case LESS_THAN -> Files.size(entry) < size;
            case LESS_THAN_OR_EQUAL -> Files.size(entry) <= size;
        };
    }

    public enum Comparison {
        LARGER_THAN,
        LARGER_THAN_OR_EQUAL,
        LESS_THAN,
        LESS_THAN_OR_EQUAL,

    }
}
