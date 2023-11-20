package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task9Test {
    @Test
    @DisplayName("Получение сум количества лап у всех животных")
    void test() {
        List<Animal> animals = List.of(
            new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 10, 8, true),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 10, 8, true),
            new Animal("Monya", Animal.Type.DOG, Animal.Sex.M, 2, 10, 8, true),
            new Animal("Mera", Animal.Type.FISH, Animal.Sex.M, 2, 10, 8, true),
            new Animal("Loma", Animal.Type.SPIDER, Animal.Sex.M, 2, 10, 8, true)
        );
        int numberOfPaws = 4 + 4 * 2 + 0 + 8;
        assertEquals(numberOfPaws, AnimalManager.getNumberOfPawsOfAnimals(animals));
    }
}
