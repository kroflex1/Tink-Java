package edu.hw3.Task5;

public record Human(String name, String surname) {
    public Human(String name){
        this(name, null);
    }

}
