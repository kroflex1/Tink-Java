package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task16Test {
    @Test
    @DisplayName("Список животных, отсортированный по виду, затем по полу, затем по имени")
    void test() {
        List<Animal> animals = List.of(
            new Animal("Abe", Animal.Type.CAT, Animal.Sex.F, 1, 150, 2, true),
            new Animal("Abe", Animal.Type.CAT, Animal.Sex.M, 1, 150, 2, true),
            new Animal("Momo", Animal.Type.FISH, Animal.Sex.M, 8, 200, 1, true),
            new Animal("Barsik", Animal.Type.DOG, Animal.Sex.M, 8, 200, 1, true),
            new Animal("Zemo", Animal.Type.FISH, Animal.Sex.M, 8, 200, 1, true)
        );

        List<Animal> except = List.of(
            new Animal("Abe", Animal.Type.CAT, Animal.Sex.M, 1, 150, 2, true),
            new Animal("Abe", Animal.Type.CAT, Animal.Sex.F, 1, 150, 2, true),
            new Animal("Barsik", Animal.Type.DOG, Animal.Sex.M, 8, 200, 1, true),
            new Animal("Momo", Animal.Type.FISH, Animal.Sex.M, 8, 200, 1, true),
            new Animal("Zemo", Animal.Type.FISH, Animal.Sex.M, 8, 200, 1, true)
        );

        assertEquals(except, AnimalManager.getSortedByTypeThenBySexThenByName(animals));
    }
}
