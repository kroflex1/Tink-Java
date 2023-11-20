package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task4Test {

    @Test
    @DisplayName("Получние животного с самым длинным именем")
    void test() {
        Animal animalWithBiggestName = new Animal("AAAAA", Animal.Type.CAT, Animal.Sex.M, 2, 20, 1, true);
        List<Animal> animals = List.of(
            new Animal("A", Animal.Type.CAT, Animal.Sex.M, 2, 20, 1, true),
            new Animal("AAA", Animal.Type.BIRD, Animal.Sex.F, 2, 20, 1, true),
            new Animal("A", Animal.Type.DOG, Animal.Sex.M, 2, 20, 1, true),
            animalWithBiggestName,
            new Animal("AA", Animal.Type.FISH, Animal.Sex.M, 2, 20, 1, true)
        );
        assertEquals(animalWithBiggestName, AnimalManager.getAnimalWithLongestName(animals));
    }

    @Test
    @DisplayName("Вызов ошибки, при получении пустого списка")
    void testEmptyList() {
        List<Animal> animals = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () ->
            AnimalManager.getAnimalWithLongestName(animals));
    }

}
