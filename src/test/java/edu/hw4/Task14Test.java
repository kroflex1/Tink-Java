package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task14Test {
    @Test
    @DisplayName("Есть ли в списке собака ростом более k см")
    void test() {
        int height = 100;
        Animal dogWithHeightMoreThan100cm = new Animal("Boris", Animal.Type.DOG, Animal.Sex.M, 2, 120, 100, true);
        List<Animal> animals = List.of(
            new Animal("Monya Lama Topa", Animal.Type.BIRD, Animal.Sex.F, 2, 120, 3, false),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 30, 50, false),
            dogWithHeightMoreThan100cm,
            new Animal("Lola Kola", Animal.Type.CAT, Animal.Sex.M, 2, 150, 2, true),
            new Animal("Tera", Animal.Type.FISH, Animal.Sex.M, 2, 200, 1, true)
        );
        assertTrue(AnimalManager.isDogInListWithHeightMoreThanCertainCm(animals, height));

    }

}
