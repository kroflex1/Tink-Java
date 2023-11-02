package edu.hw4;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import edu.hw4.Checkers.AnimalField;
import edu.hw4.Checkers.AnimalNameChecker;
import edu.hw4.Checkers.ValidationError;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalNameCheckerTest {
    private static Stream<Arguments> invalidNames() {
        ValidationError invalidRegister =
            new ValidationError(AnimalField.TYPE.NAME, AnimalNameChecker.MESSAGE_FOR_INVALID_REGISTER);
        ValidationError invalidSymbols =
            new ValidationError(AnimalField.TYPE.NAME, AnimalNameChecker.MESSAGE_FOR_INVALID_SYMBOLS);
        ValidationError invalidSpaces =
            new ValidationError(AnimalField.TYPE.NAME, AnimalNameChecker.MESSAGE_FOR_CONTAINING_SPACES_AT_BEGIN_OF_END);
        ValidationError invalidLength =
            new ValidationError(AnimalField.TYPE.NAME, AnimalNameChecker.MESSAGE_FOR_INVALID_LENGTH);
        return Stream.of(
            Arguments.of("borya", new HashSet<>(List.of(invalidRegister))),
            Arguments.of("Borya123", new HashSet<>(List.of(invalidSymbols))),
            Arguments.of(" borya ", new HashSet<>(List.of(invalidSpaces))),
            Arguments.of("bOrYa", new HashSet<>(List.of(invalidRegister))),
            Arguments.of("borya_100", new HashSet<>(List.of(invalidRegister, invalidSymbols))),
            Arguments.of("a", new HashSet<>(List.of(invalidRegister, invalidLength))),
            Arguments.of("a".repeat(60), new HashSet<>(List.of(invalidRegister, invalidLength)))
        );
    }

    @ParameterizedTest
    @MethodSource("invalidNames")
    void testInvalidNames(String name, Set<ValidationError> except) {
        Optional<Set<ValidationError>> result = AnimalNameChecker.checkName(name);
        assertEquals(except, result.get());
    }
}
