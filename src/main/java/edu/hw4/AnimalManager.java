package edu.hw4;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnimalManager {
    //Task1
    public static List<Animal> getSortedAnimalsByByHeight(List<Animal> animals) {
        animals.sort(Comparator.comparingInt(Animal::height));
        return animals;
    }

    //Task2
    public static List<Animal> getSortedAnimalsByByWeight(List<Animal> animals) {
        animals.sort(Comparator.comparingInt(Animal::weight).reversed());
        return animals;
    }

    //Task3
    public static Map<Animal.Type, Long> countNumberOfAnimalsOfEachSpecies(List<Animal> animals) {
        return animals.stream().collect(Collectors.groupingBy(Animal::type, Collectors.counting()));
    }

    //Task4
    public static Animal getAnimalWithLongestName(List<Animal> animals) {
//        Optional<Integer> var = list.stream()
//            .max(Comparator.reverseOrder());
//
//        // If a value is present, isPresent()
//        // will return true, else display message
//        if (var.isPresent()) {
//            System.out.println(var.get());
//        }
//        else {
//            System.out.println("-1");
//        }
        return animals.stream().max(Comparator.comparing(Animal::height)).orElseThrow(NoSuchElementException::new);
    }

    //Task5
    public static Animal.Sex whatMoreMalesOrFemales(List<Animal> animals) {
        Map<Animal.Sex, Long> numberOfMalesAndFemales =
            animals.stream().collect(Collectors.groupingBy(Animal::sex, Collectors.counting()));
        return numberOfMalesAndFemales.get(Animal.Sex.M) >= numberOfMalesAndFemales.get(Animal.Sex.F) ? Animal.Sex.M :
            Animal.Sex.F;
    }

    //Task6
    public static Map<Animal.Type, Optional<Animal>> getHeaviestAnimalOfEachSpecies(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.maxBy(Comparator.comparingInt(Animal::weight))));
    }

    //Task7
    public static Animal getOldestAnimal(List<Animal> animals, int animalPosition) throws IllegalArgumentException {
        if (animalPosition < 0 || animalPosition >= animals.size()) {
            throw new IllegalArgumentException();
        }
        animals.sort(Comparator.comparingInt(Animal::age).reversed());
        return animals.get(animalPosition);
    }

    //Task8
    public static Optional<Animal> getHeaviestAnimalAmongAnimalsBelowCertainCm(List<Animal> animals, int animalHeight)
        throws IllegalArgumentException {
        if (animalHeight < 0) {
            throw new IllegalArgumentException();
        }
        return animals.stream().filter(animal -> animal.height() <= animalHeight)
            .max(Comparator.comparingInt(Animal::weight));
    }

    //Task9
    public static Integer getNumberOfPawsOfAnimals(List<Animal> animals) {
        return animals.stream().mapToInt(Animal::paws).sum();
    }

    //Task10
    public static List<Animal> animalsWhoseAgeDoesNotMatchTheNumberOfPaws(List<Animal> animals) {
        return animals.stream().filter(animal -> animal.age() != animal.paws()).collect(Collectors.toList());
    }

    //Task11
    public static List<Animal> animalsThatCanBiteAndWhoseHeightExceeds100cm(List<Animal> animals) {
        return animals.stream().filter(animal -> animal.height() >= 100 && animal.bites()).collect(Collectors.toList());
    }

    //Task12
    public static Integer getNumberOfAnimalsWhoseWeightExceedsHeight(List<Animal> animals) {
        return Math.toIntExact(animals.stream().filter(animal -> animal.weight() > animal.height()).count());
    }

    //Task13
    public static List<Animal> getAnimalsWhoseNamesConsistOfMoreThanTwoWords(List<Animal> animals) {
        return animals.stream().filter(animal -> animal.name().split(" ").length >= 2).collect(Collectors.toList());
    }

    //Task14
    public static Boolean isDogInListWithHeightMoreThanCertainCm(List<Animal> animals, int dogHeight) {
        return animals.stream().anyMatch(animal -> animal.type() == Animal.Type.DOG && animal.height() >=dogHeight);
    }

}
