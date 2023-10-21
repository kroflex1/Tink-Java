package edu.project1.WordGenerator;

import org.jetbrains.annotations.NotNull;

public interface WordGenerator {
    @NotNull
    public String getRandomWordFromTopic(Topic topic);
}
