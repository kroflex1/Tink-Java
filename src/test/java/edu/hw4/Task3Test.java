package edu.hw4;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task3Test {
    @Test
    @DisplayName("Подсчёт количества животных каждого вида")
    void test() {
        var lion = new Animal("lion", Animal.Type.CAT, Animal.Sex.M, 1, 1, 1, true);
        var cat = new Animal("cat", Animal.Type.CAT, Animal.Sex.M, 1, 1, 1, true);
        var wolf = new Animal("wolf", Animal.Type.DOG, Animal.Sex.M, 1, 1, 1, true);
        var widow = new Animal("widow", Animal.Type.SPIDER, Animal.Sex.M, 1, 1, 1, true);
        var result = AnimalManager.countNumberOfAnimalsOfEachSpecies(List.of(lion, cat, wolf, widow));
        assertEquals(1, result.get(Animal.Type.DOG));
        assertEquals(1, result.get(Animal.Type.SPIDER));
        assertEquals(2, result.get(Animal.Type.CAT));
    }

}
