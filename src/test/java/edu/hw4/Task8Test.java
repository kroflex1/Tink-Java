package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task8Test {
    @Test
    @DisplayName("Получение самого тяжелого животного среди животных ниже k см")
    void test() {
        int height = 100;
        Animal heaviesAnimalBelow100 = new Animal("Monya", Animal.Type.BIRD, Animal.Sex.F, 2, 100, 60, true);
        List<Animal> animals = List.of(
            new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 220, 200, true),
            heaviesAnimalBelow100,
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 80, 20, true),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 150, 2, true),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 30, 8, true)
        );
        assertEquals(heaviesAnimalBelow100, AnimalManager.getHeaviestAnimalBelowCertainHeight(animals, height).get());
    }

    @Test
    @DisplayName("Отсутствуют животные ниже k см")
    void testNoAnimalsBelowHeight() {
        int height = 10;
        List<Animal> animals = List.of(
            new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 220, 200, true),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 80, 20, true)
        );
        assertTrue(AnimalManager.getHeaviestAnimalBelowCertainHeight(animals, height).isEmpty());
    }

    @Test
    @DisplayName("Вызов ошибки при отрицательном росте")
    void testNegativeHeight() {
        List<Animal> animals = List.of(
            new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 220, 200, true),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 80, 20, true)
        );
        assertThrows(IllegalArgumentException.class, () ->
            AnimalManager.getHeaviestAnimalBelowCertainHeight(animals, -100));
    }

}
