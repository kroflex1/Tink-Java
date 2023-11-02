package edu.hw4.Checkers;

import edu.hw4.Animal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AnimalChecker {
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 200;
    private static final int MIN_HEIGHT = 1;
    private static final int MAX_HEIGHT = 200;
    private static final int MIN_WEIGHT = 1;
    private static final int MAX_WEIGHT = 200;
    public static final String MESSAGE_FOR_INVALID_AGE =
        String.format("Age must be greater than %d and less than %d", MIN_AGE, MAX_AGE);
    public static final String MESSAGE_FOR_INVALID_HEIGHT =
        String.format("Height must be greater than %d and less than %d", MIN_HEIGHT, MAX_HEIGHT);
    public static final String MESSAGE_FOR_INVALID_WEIGHT =
        String.format("Weight must be greater than %d and less than %d", MIN_WEIGHT, MAX_WEIGHT);

    public static Optional<Set<ValidationError>> checkAnimal(Animal animal) {
        Set<ValidationError> errors = new HashSet<>();
        Optional<Set<ValidationError>> nameErrors = AnimalNameChecker.checkName(animal.name());
        nameErrors.ifPresent(errors::addAll);
        if (animal.age() < MIN_AGE || animal.age() > MAX_AGE) {
            errors.add(new ValidationError(AnimalField.AGE, MESSAGE_FOR_INVALID_AGE));
        }
        if (animal.height() < MIN_HEIGHT || animal.height() > MAX_HEIGHT) {
            errors.add(new ValidationError(AnimalField.HEIGHT, MESSAGE_FOR_INVALID_HEIGHT));
        }
        if (animal.weight() < MIN_WEIGHT || animal.weight() > MAX_WEIGHT) {
            errors.add(new ValidationError(AnimalField.WEIGHT, MESSAGE_FOR_INVALID_WEIGHT));
        }
        if (errors.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(errors);
    }

    private AnimalChecker() {

    }

}
