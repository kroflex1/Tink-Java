package edu.hw4;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task6Test {
    @Test
    void test() {
        Animal heaviestCat =  new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 20, 4, true);
        Animal heaviestDog = new Animal("Lola", Animal.Type.DOG, Animal.Sex.F, 2, 20, 5, true);
        Animal heaviestFish =  new Animal("Tera", Animal.Type.FISH, Animal.Sex.M, 2, 20, 3, true);
        Animal heaviestBird =  new Animal("Soma", Animal.Type.BIRD, Animal.Sex.F, 2, 20, 7, true);
        List<Animal> animals = List.of(
            heaviestCat,
            new Animal("Monya", Animal.Type.CAT, Animal.Sex.F, 2, 20, 1, true),
            heaviestDog,
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 20, 2, true),
            heaviestBird,
            heaviestFish
        );
        Map<Animal.Type, Animal> except = new HashMap<>(){{
            put(Animal.Type.CAT, heaviestCat);
            put(Animal.Type.DOG, heaviestDog);
            put(Animal.Type.FISH, heaviestFish);
            put(Animal.Type.BIRD, heaviestBird);
        }};
        assertEquals(except, AnimalManager.getHeaviestAnimalOfEachSpecies(animals));

    }
}
