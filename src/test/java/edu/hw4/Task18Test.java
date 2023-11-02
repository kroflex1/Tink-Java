package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task18Test {
    @Test
    @DisplayName("Найти самую тяжелую рыбку")
    void test() {
        Animal theMostHeavyFish = new Animal("Nemo", Animal.Type.FISH, Animal.Sex.M, 8, 200, 1000, true);
        List<Animal> animals1 = List.of(
            new Animal("Abe", Animal.Type.FISH, Animal.Sex.M, 1, 150, 20, true),
            new Animal("Momo", Animal.Type.DOG, Animal.Sex.M, 8, 200, 1, true),
            new Animal("Zemo", Animal.Type.FISH, Animal.Sex.M, 8, 200, 10, false),
            new Animal("Luma", Animal.Type.DOG, Animal.Sex.M, 8, 200, 1, false)
        );
        List<Animal> animals2 = List.of(
            new Animal("Bamo", Animal.Type.CAT, Animal.Sex.M, 1, 150, 20, true),
            new Animal("Kira", Animal.Type.FISH, Animal.Sex.M, 8, 200, 400, true),
            theMostHeavyFish,
            new Animal("Varya", Animal.Type.BIRD, Animal.Sex.M, 8, 200, 1000, true)
        );
        List<Animal> animals3 = List.of(
            new Animal("Lum", Animal.Type.FISH, Animal.Sex.M, 1, 150, 20, true),
            new Animal("Ero", Animal.Type.BIRD, Animal.Sex.M, 8, 200, 1, true)
        );

        assertEquals(theMostHeavyFish, AnimalManager.getMostHeavyFish(animals1, animals2, animals3).get());
    }

    @Test
    @DisplayName("Нет ни 1 рыбы")
    void testLackOfFish() {
        Animal theMostHeavyFish = new Animal("Nemo", Animal.Type.FISH, Animal.Sex.M, 8, 200, 1000, true);
        List<Animal> animals1 = List.of(
            new Animal("Abe", Animal.Type.CAT, Animal.Sex.M, 1, 150, 20, true),
            new Animal("Momo", Animal.Type.DOG, Animal.Sex.M, 8, 200, 1, true),
            new Animal("Zemo", Animal.Type.BIRD, Animal.Sex.M, 8, 200, 10, false),
            new Animal("Luma", Animal.Type.DOG, Animal.Sex.M, 8, 200, 1, false)
        );
        assertTrue(AnimalManager.getMostHeavyFish(animals1).isEmpty());
    }
}
