package edu.hw4.Checkers;

public class ValidationError extends RuntimeException {

    public ValidationError(AnimalField field, String description) {
        super(String.format("%s: %s", field.toString().toLowerCase(), description));
    }

}
