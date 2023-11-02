package edu.hw4;

import edu.hw4.Checkers.ValidationError;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task19Test {
    private static Stream<Arguments> invalidAnimals() {
        Animal animalWithNegativeAge = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, -2, 10, 2, true);
        Animal tooOldAnimal = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, 1000, 10, 2, true);
        Animal animalWithNegativeHeight = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, 1, -10, 2, true);
        Animal tooHighAnimal = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, 1, 700, 2, true);
        Animal animalWithNegativeWeight = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, 1, 100, -100, true);
        Animal tooHeavyAnimal = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, 1, 100, 1000, true);
        Animal animalWithInvalidName = new Animal("borya", Animal.Type.CAT, Animal.Sex.M, 1, 10, 10, true);
        Animal goodAnimal1 = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, 4, 10, 2, true);
        Animal goodAnimal2 = new Animal("Varya", Animal.Type.DOG, Animal.Sex.F, 2, 12, 1, true);
        List<Animal> animals = List.of(
            animalWithNegativeAge,
            tooOldAnimal,
            animalWithNegativeHeight,
            goodAnimal1,
            tooHighAnimal,
            animalWithNegativeWeight,
            goodAnimal2,
            tooHeavyAnimal,
            animalWithInvalidName
        );
        return Stream.of(Arguments.of(animals, 7));
    }

    @ParameterizedTest
    @MethodSource("invalidAnimals")
    void testInvalidAnimals(List<Animal> animals, int amountOfInvalidAnimals) {
        Map<Animal, Set<ValidationError>> result = AnimalManager.getAnimalsThatContainError(animals);
        assertEquals(amountOfInvalidAnimals, result.size());
    }
}

