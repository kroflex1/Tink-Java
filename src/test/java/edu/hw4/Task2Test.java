package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task2Test {
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("Сортировка животных по весу от самого тяжелого к самомому легкому и получение k первых")
    void testSortAnimalsByHeight(int n) {
        List<Animal> animals = List.of(
            new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 20, 4, true),
            new Animal("Monya", Animal.Type.BIRD, Animal.Sex.F, 2, 20, 3, true),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 20, 5, true),
            new Animal("Lola", Animal.Type.CAT, Animal.Sex.M, 2, 20, 2, true),
            new Animal("Tera", Animal.Type.FISH, Animal.Sex.M, 2, 20, 1, true)
        );
        List<Animal> sortedAnimals = List.of(
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 20, 5, true),
            new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 20, 4, true),
            new Animal("Monya", Animal.Type.BIRD, Animal.Sex.F, 2, 20, 3, true),
            new Animal("Lola", Animal.Type.CAT, Animal.Sex.M, 2, 20, 2, true),
            new Animal("Tera", Animal.Type.FISH, Animal.Sex.M, 2, 20, 1, true)
        );
        assertEquals(
            sortedAnimals.stream().limit(n).toList(),
            AnimalManager.getSortedAnimalsByByWeightAngGetFirstN(animals, n)
        );
    }

    @Test
    @DisplayName("Сортировка пустого списка")
    void testEmptyList() {
        List<Animal> animals = new ArrayList<>();
        assertTrue(AnimalManager.getSortedAnimalsByByWeightAngGetFirstN(animals, 3).isEmpty());
    }

    @Test
    @DisplayName("Вызов ошибки при некорректном аргументе")
    void testInvalidArgument() {
        List<Animal> animals = List.of(
            new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 20, 4, true),
            new Animal("Monya", Animal.Type.BIRD, Animal.Sex.F, 2, 20, 3, true)
        );
        assertThrows(IllegalArgumentException.class, () ->
            AnimalManager.getSortedAnimalsByByWeightAngGetFirstN(animals, -1));
        assertThrows(IllegalArgumentException.class, () ->
            AnimalManager.getSortedAnimalsByByWeightAngGetFirstN(animals, 0));
    }
}
