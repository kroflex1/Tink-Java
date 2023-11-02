package edu.hw4;

import edu.hw4.Checkers.AnimalChecker;
import edu.hw4.Checkers.AnimalNameChecker;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task20Test {
    private static Stream<Arguments> invalidAnimals() {
        Animal animalWithNegativeAge = new Animal("Borya", Animal.Type.CAT, Animal.Sex.M, -2, 10, 2, true);
        Animal animalWithNegativeHeight = new Animal("Varya", Animal.Type.CAT, Animal.Sex.M, 1, -10, 2, true);
        Animal animalWithSeveralErrors = new Animal("lara", Animal.Type.CAT, Animal.Sex.M, 2, -10, 1000, true);
        String animalWithNegativeAgeDescription =
            String.format("Borya:\n\tage: %s\n", AnimalChecker.MESSAGE_FOR_INVALID_AGE);
        String animalWithNegativeHeightDescription =
            String.format("Varya:\n\theight: %s\n", AnimalChecker.MESSAGE_FOR_INVALID_HEIGHT);
        String animalWithSeveralErrorsDescription = String.format("lara:\n\tname: %s\n\theight: %s\n\tweight: %s\n",
            AnimalNameChecker.MESSAGE_FOR_INVALID_REGISTER,
            AnimalChecker.MESSAGE_FOR_INVALID_HEIGHT,
            AnimalChecker.MESSAGE_FOR_INVALID_WEIGHT
        );
        Map<Animal, String> except = new HashMap<>(){{
            put(animalWithNegativeAge, animalWithNegativeAgeDescription);
            put(animalWithNegativeHeight, animalWithNegativeHeightDescription);
            put(animalWithSeveralErrors, animalWithSeveralErrorsDescription);
        }};
        return Stream.of(Arguments.of(List.of(animalWithNegativeAge, animalWithNegativeHeight, animalWithSeveralErrors), except));
    }

    @ParameterizedTest
    @MethodSource("invalidAnimals")
    void testInvalidAnimals(List<Animal> animals, Map<Animal, String> except) {
        Map<Animal, String> result = AnimalManager.getAnimalsErrorsDescription(animals);
        assertEquals(except, result);
    }
}
