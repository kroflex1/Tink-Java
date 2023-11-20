package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task12Test {
    @Test
    @DisplayName("")
    void test() {
        List<Animal> animals = List.of(
            new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 60, 100, true),
            new Animal("Monya", Animal.Type.BIRD, Animal.Sex.F, 2, 120, 3, false),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 30, 50, false),
            new Animal("Lola", Animal.Type.CAT, Animal.Sex.M, 2, 150, 2, true),
            new Animal("Tera", Animal.Type.FISH, Animal.Sex.M, 2, 200, 1, true)
        );
        Integer except = 2;

        assertEquals(2, AnimalManager.getNumberOfAnimalsWhoseWeightExceedsHeight(animals));
    }
}
