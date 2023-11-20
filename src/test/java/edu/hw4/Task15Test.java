package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task15Test {
    @Test
    @DisplayName("Суммарный вес животных каждого вида, которым от k до l лет")
    void test() {
        int startAge = 3;
        int endAge = 6;
        Animal exceptAnimal1 = new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 4, 60, 60, true);
        Animal exceptAnimal2 = new Animal("Valera", Animal.Type.DOG, Animal.Sex.F, 6, 60, 40, true);
        Animal exceptAnimal3 = new Animal("Lola", Animal.Type.BIRD, Animal.Sex.M, 3, 60, 10, true);
        List<Animal> animals = List.of(
            exceptAnimal1,
            exceptAnimal2,
            exceptAnimal3,
            new Animal("Lola Kola", Animal.Type.CAT, Animal.Sex.M, 1, 150, 2, true),
            new Animal("Tera", Animal.Type.FISH, Animal.Sex.M, 8, 200, 1, true)
        );
        int exceptSumWeight = exceptAnimal1.weight() + exceptAnimal2.weight() + exceptAnimal3.weight();
        assertEquals(exceptSumWeight, AnimalManager.getTotalWeightOfAnimalsFromRangeOfAges(animals, startAge, endAge));
    }
}
