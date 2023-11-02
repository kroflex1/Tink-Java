package edu.hw4.Checkers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

public class AnimalNameChecker {
    private static final AnimalField FIELD_FOR_ERROR = AnimalField.NAME;
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 50;
    private static final Pattern ALLOWED_SYMBOLS_PATTERN = Pattern.compile("[a-zA-z ]+");
    public static final String MESSAGE_FOR_INVALID_LENGTH =
        String.format("The length of name must be greater than %d and less than %d", MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    public static final String MESSAGE_FOR_INVALID_SYMBOLS = "The name can contain only latin letters and spaces";
    public static final String MESSAGE_FOR_CONTAINING_SPACES_AT_BEGIN_OF_END =
        "The name must not contain space characters at the beginning or end";
    public static final String MESSAGE_FOR_INVALID_REGISTER =
        "The name must start with a capital letter and not contain capital letters in other positions";

    public static Optional<Set<ValidationError>> checkName(String name) {
        Set<ValidationError> errors = new HashSet<>();
        if (!isValidLength(name)) {
            errors.add(new ValidationError(FIELD_FOR_ERROR, MESSAGE_FOR_INVALID_LENGTH));
        }
        if (!isContainOnlyAllowedSymbols(name)) {
            errors.add(new ValidationError(FIELD_FOR_ERROR, MESSAGE_FOR_INVALID_SYMBOLS));
        }
        if (!isContainSpacesAtBeginningOrEnd(name)) {
            if (!isValidRegister(name)) {
                errors.add(new ValidationError(FIELD_FOR_ERROR, MESSAGE_FOR_INVALID_REGISTER));
            }
        } else {
            errors.add(new ValidationError(FIELD_FOR_ERROR, MESSAGE_FOR_CONTAINING_SPACES_AT_BEGIN_OF_END));
        }

        if (errors.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(errors);
    }

    private static boolean isValidLength(String name) {
        return name.length() >= MIN_NAME_LENGTH && name.length() <= MAX_NAME_LENGTH;
    }

    private static boolean isContainOnlyAllowedSymbols(String name) {
        return ALLOWED_SYMBOLS_PATTERN.matcher(name).matches();
    }

    private static boolean isContainSpacesAtBeginningOrEnd(String name) {
        return name.charAt(0) == ' ' || name.charAt(name.length() - 1) == ' ';
    }

    private static boolean isValidRegister(String name) {
        if (!Character.isUpperCase(name.charAt(0))) {
            return false;
        }
        for (int i = 1; i < name.length(); ++i) {
            if (Character.isUpperCase(name.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private AnimalNameChecker() {

    }
}
