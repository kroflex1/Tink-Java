package edu.hw4.Checkers;

public record ValidationError(String description, AnimalField field) {

    public ValidationError(AnimalField field, String description) {
        this(String.format("%s: %s", field.toString().toLowerCase(), description), field);
    }
}
