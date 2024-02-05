package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task17Test {
    @Test
    @DisplayName("Пауки кусаются чаще, чем собаки")
    void test() {
        List<Animal> animals = List.of(
            new Animal("Abe", Animal.Type.SPIDER, Animal.Sex.M, 1, 150, 2, true),
            new Animal("Momo", Animal.Type.DOG, Animal.Sex.M, 8, 200, 1, true),
            new Animal("Barsik", Animal.Type.SPIDER, Animal.Sex.M, 8, 200, 1, true),
            new Animal("Zemo", Animal.Type.DOG, Animal.Sex.M, 8, 200, 1, false),
            new Animal("Zemo", Animal.Type.DOG, Animal.Sex.M, 8, 200, 1, false)
        );

        assertTrue(AnimalManager.isSpidersBiteMoreOftenThanDogs(animals));
    }

    @Test
    @DisplayName("Недостаточно информации для определения того, кто кусается чаще")
    void testLackOfInformation() {
        List<Animal> animals = List.of(
            new Animal("Abe", Animal.Type.CAT, Animal.Sex.M, 1, 150, 2, true),
            new Animal("Momo", Animal.Type.DOG, Animal.Sex.M, 8, 200, 1, true),
            new Animal("Barsik", Animal.Type.CAT, Animal.Sex.M, 8, 200, 1, true),
            new Animal("Zemo", Animal.Type.DOG, Animal.Sex.M, 8, 200, 1, false),
            new Animal("Zemo", Animal.Type.DOG, Animal.Sex.M, 8, 200, 1, false)
        );

        assertFalse(AnimalManager.isSpidersBiteMoreOftenThanDogs(animals));
    }
}
