package edu.hw4;

import edu.hw4.Checkers.AnimalChecker;
import edu.hw4.Checkers.AnimalField;
import edu.hw4.Checkers.AnimalNameChecker;
import edu.hw4.Checkers.ValidationError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalCheckerTest {
    private static Stream<Arguments> invalidAnimals() {
        Animal animalWithNegativeAge = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, -2, 10, 2, true);
        Animal tooOldAnimal = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, 1000, 10, 2, true);
        Animal animalWithNegativeHeight = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, 1, -10, 2, true);
        Animal tooHighAnimal = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, 1, 700, 2, true);
        Animal animalWithNegativeWeight = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, 1, 100, -100, true);
        Animal tooHeavyAnimal = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, 1, 100, 1000, true);
        Animal animalWithInvalidName = new Animal("borya", Animal.Type.CAT, Animal.Sex.M, 1, 10, 10, true);
        return Stream.of(
            Arguments.of(
                animalWithNegativeAge,
                new ValidationError(AnimalField.TYPE.AGE, AnimalChecker.MESSAGE_FOR_INVALID_AGE)
            ),
            Arguments.of(
                tooOldAnimal,
                new ValidationError(AnimalField.TYPE.AGE, AnimalChecker.MESSAGE_FOR_INVALID_AGE)
            ),
            Arguments.of(
                animalWithNegativeHeight,
                new ValidationError(AnimalField.TYPE.HEIGHT, AnimalChecker.MESSAGE_FOR_INVALID_HEIGHT)
            ),
            Arguments.of(
                tooHighAnimal,
                new ValidationError(AnimalField.TYPE.HEIGHT, AnimalChecker.MESSAGE_FOR_INVALID_HEIGHT)
            ),
            Arguments.of(
                animalWithNegativeWeight,
                new ValidationError(AnimalField.TYPE.WEIGHT, AnimalChecker.MESSAGE_FOR_INVALID_WEIGHT)
            ),
            Arguments.of(
                tooHeavyAnimal,
                new ValidationError(AnimalField.TYPE.WEIGHT, AnimalChecker.MESSAGE_FOR_INVALID_WEIGHT)
            ),
            Arguments.of(
                animalWithInvalidName,
                new ValidationError(AnimalField.TYPE.NAME, AnimalNameChecker.MESSAGE_FOR_INVALID_REGISTER)
            )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidAnimals")
    void testInvalidAnimals(Animal animal, ValidationError errorExcept) {
        Optional<Set<ValidationError>> result = AnimalChecker.checkAnimal(animal);
        assertEquals(errorExcept, result.get().stream().findFirst().get());
    }

    @Test
    void testAnimalWithSeveralErrors() {
        Animal badAnimal = new Animal("borya", Animal.Type.CAT, Animal.Sex.M, -10, -100, 10000, true);
        Set<ValidationError> except =
            new HashSet<>(List.of(
                new ValidationError(AnimalField.TYPE.AGE, AnimalChecker.MESSAGE_FOR_INVALID_AGE),
                new ValidationError(AnimalField.TYPE.HEIGHT, AnimalChecker.MESSAGE_FOR_INVALID_HEIGHT),
                new ValidationError(AnimalField.TYPE.WEIGHT, AnimalChecker.MESSAGE_FOR_INVALID_WEIGHT),
                new ValidationError(AnimalField.TYPE.NAME, AnimalNameChecker.MESSAGE_FOR_INVALID_REGISTER)
            ));
        Optional<Set<ValidationError>> result = AnimalChecker.checkAnimal(badAnimal);
        assertEquals(except, result.get());
    }
}
