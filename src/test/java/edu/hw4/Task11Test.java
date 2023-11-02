package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

public class Task11Test {
    @Test
    @DisplayName("Получение животных, которые могут укусить и рост которых превышает 100 см")
    void test() {
        List<Animal> animals = List.of(
            new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 60, 4, true),
            new Animal("Monya", Animal.Type.BIRD, Animal.Sex.F, 2, 120, 3, false),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 40, 5, false),
            new Animal("Lola", Animal.Type.CAT, Animal.Sex.M, 2, 150, 2, true),
            new Animal("Tera", Animal.Type.FISH, Animal.Sex.M, 2, 200, 1, true)
        );
        List<Animal> except = List.of(
            new Animal("Lola", Animal.Type.CAT, Animal.Sex.M, 2, 150, 2, true),
            new Animal("Tera", Animal.Type.FISH, Animal.Sex.M, 2, 200, 1, true)
        );

        assertEquals(except, AnimalManager.getAnimalsThatCanBiteAndWhoseHeightExceeds100cm(animals));
    }
}
