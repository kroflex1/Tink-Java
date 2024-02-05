package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task1Test {

    @Test
    @DisplayName("Сортировка животных по росту, от самых маленьких до самых больших")
    void testSortAnimalsByHeight() {
        List<Animal> animals = List.of(
            new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 20, 8, true),
            new Animal("Monya", Animal.Type.BIRD, Animal.Sex.F, 2, 4, 8, true),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 8, 8, true)
        );
        List<Animal> except = List.of(
            new Animal("Monya", Animal.Type.BIRD, Animal.Sex.F, 2, 4, 8, true),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 8, 8, true),
            new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 20, 8, true)
        );
        assertEquals(except, AnimalManager.getSortedAnimalsByByHeight(animals));
    }

    @Test
    @DisplayName("Сортировка пустого списка")
    void testEmptyList() {
        List<Animal> animals = new ArrayList<>();
        assertTrue(AnimalManager.getSortedAnimalsByByHeight(animals).isEmpty());
    }
}
