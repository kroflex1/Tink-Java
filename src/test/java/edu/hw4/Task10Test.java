package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task10Test {
    @Test
    @DisplayName("Получение животных, чей возраст не равен количеству их лап")
    void test() {
        Animal exceptCat = new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 1, 10, 8, true);
        Animal exceptSpider = new Animal("Widow", Animal.Type.SPIDER, Animal.Sex.M, 6, 10, 8, true);
        List<Animal> animals = List.of(
            exceptCat,
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 4, 10, 8, true),
            new Animal("Monya", Animal.Type.DOG, Animal.Sex.M, 4, 10, 8, true),
            exceptSpider,
            new Animal("Mera", Animal.Type.FISH, Animal.Sex.M, 0, 10, 8, true)
        );
        List<Animal> except = List.of(exceptCat, exceptSpider);
        assertEquals(except, AnimalManager.getAnimalsWhoseAgeDoesNotMatchTheNumberOfPaws(animals));
    }
}
