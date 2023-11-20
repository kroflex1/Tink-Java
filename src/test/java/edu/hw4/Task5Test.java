package edu.hw4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task5Test {
    @Test
    @DisplayName("Каких животных больше: самцов или самок")
    void test() {
        List<Animal> animals = List.of(
            new Animal("Boris", Animal.Type.CAT, Animal.Sex.M, 2, 20, 4, true),
            new Animal("Monya", Animal.Type.BIRD, Animal.Sex.M, 2, 20, 3, true),
            new Animal("Valera", Animal.Type.DOG, Animal.Sex.M, 2, 20, 5, true),
            new Animal("Lola", Animal.Type.CAT, Animal.Sex.F, 2, 20, 2, true),
            new Animal("Tera", Animal.Type.FISH, Animal.Sex.F, 2, 20, 1, true)
        );
        assertEquals(Animal.Sex.M, AnimalManager.whatMoreMalesOrFemales(animals));
    }

    @Test
    @DisplayName("Вызов ошибки при пустом списке")
    void testEmptyList() {
        assertThrows(IllegalArgumentException.class, () ->
            AnimalManager.whatMoreMalesOrFemales(new ArrayList<>()));
    }
}
