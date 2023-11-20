package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task13Test {
    @Test
    @DisplayName("Получение животных, имена которых состоят из более чем двух слов")
    void test() {
        List<Animal> animals = List.of(
            new Animal("Boris Vivo", Animal.Type.CAT, Animal.Sex.M, 2, 60, 100, true),
            new Animal("Monya Lama Topa", Animal.Type.BIRD, Animal.Sex.F, 2, 120, 3, false),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 30, 50, false),
            new Animal("Lola Kola", Animal.Type.CAT, Animal.Sex.M, 2, 150, 2, true),
            new Animal("Tera", Animal.Type.FISH, Animal.Sex.M, 2, 200, 1, true)
        );

        List<Animal> except = List.of(
            new Animal("Boris Vivo", Animal.Type.CAT, Animal.Sex.M, 2, 60, 100, true),
            new Animal("Monya Lama Topa", Animal.Type.BIRD, Animal.Sex.F, 2, 120, 3, false),
            new Animal("Lola Kola", Animal.Type.CAT, Animal.Sex.M, 2, 150, 2, true)
        );
        assertEquals(except, AnimalManager.getAnimalsWhoseNamesConsistOfMoreThanTwoWords(animals));
    }
}
