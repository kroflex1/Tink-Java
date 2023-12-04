package edu.project1.WordGenerator;

import org.jetbrains.annotations.NotNull;

public interface WordGenerator {
    @NotNull
    String getRandomWordFromTopic(Topic topic);
}
