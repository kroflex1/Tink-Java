package edu.hw4;

import edu.hw4.Checkers.AnimalChecker;
import edu.hw4.Checkers.ValidationError;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnimalManager {
    //Task1
    public static List<Animal> getSortedAnimalsByByHeight(List<Animal> animals) {
        return animals.stream().sorted(Comparator.comparing(Animal::height)).collect(Collectors.toList());
    }

    //Task2
    public static List<Animal> getSortedAnimalsByByWeightAngGetFirstN(List<Animal> animals, int n)
        throws IllegalArgumentException {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }
        return animals.stream().sorted(Comparator.comparing(Animal::weight).reversed()).limit(n)
            .collect(Collectors.toList());
    }

    //Task3
    public static Map<Animal.Type, Integer> countNumberOfAnimalsOfEachSpecies(List<Animal> animals) {
        return animals.stream().collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(a -> 1)));
    }

    //Task4
    public static Animal getAnimalWithLongestName(List<Animal> animals) {
        return animals.stream().max(Comparator.comparing(animal -> animal.name().length()))
            .orElseThrow(IllegalArgumentException::new);
    }

    //Task5
    public static Animal.Sex whatMoreMalesOrFemales(List<Animal> animals) throws IllegalArgumentException {
        if (animals.isEmpty()) {
            throw new IllegalArgumentException("There must be at least 1 item in the list");
        }
        Map<Animal.Sex, Long> numberOfMalesAndFemales =
            animals.stream().collect(Collectors.groupingBy(Animal::sex, Collectors.counting()));
        return numberOfMalesAndFemales.get(Animal.Sex.M) >= numberOfMalesAndFemales.get(Animal.Sex.F) ? Animal.Sex.M
            : Animal.Sex.F;
    }

    //Task6
    public static Map<Animal.Type, Animal> getHeaviestAnimalOfEachSpecies(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.toMap(Animal::type, Function.identity(),
                BinaryOperator.maxBy(Comparator.comparing(Animal::weight))
            ));
    }

    //Task7
    public static Animal getOldestAnimal(List<Animal> animals, int animalPosition) throws IllegalArgumentException {
        if (animals.isEmpty()) {
            throw new IllegalArgumentException("List shouldn`t be empty");
        }
        if (animalPosition <= 0 || animalPosition > animals.size()) {
            throw new IllegalArgumentException(String.format(
                "Position must be greater than 1 and less than %d",
                animals.size()
            ));
        }
        return animals.stream().sorted(Comparator.comparing(Animal::age).reversed()).toList().get(animalPosition - 1);
    }

    //Task8
    public static Optional<Animal> getHeaviestAnimalBelowCertainHeight(List<Animal> animals, int animalHeight)
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
    public static List<Animal> getAnimalsWhoseAgeDoesNotMatchTheNumberOfPaws(List<Animal> animals) {
        return animals.stream().filter(animal -> animal.age() != animal.paws()).collect(Collectors.toList());
    }

    //Task11
    @SuppressWarnings("MagicNumber")
    public static List<Animal> getAnimalsThatCanBiteAndWhoseHeightExceeds100cm(List<Animal> animals) {
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
        return animals.stream().anyMatch(animal -> animal.type() == Animal.Type.DOG && animal.height() >= dogHeight);
    }

    //Task15
    public static Integer getTotalWeightOfAnimalsFromRangeOfAges(List<Animal> animals, int startAge, int endAge)
        throws IllegalArgumentException {
        if (endAge < startAge) {
            throw new IllegalArgumentException("endAge must be more startAge");
        }
        return animals.stream().filter(animal -> animal.age() >= startAge && animal.age() <= endAge)
            .mapToInt(Animal::weight).sum();
    }

    //Task16
    public static List<Animal> getSortedByTypeThenBySexThenByName(List<Animal> animals) {
        return animals.stream()
            .sorted(Comparator.comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name)).collect(Collectors.toList());
    }

    //Task17
    public static Boolean isSpidersBiteMoreOftenThanDogs(List<Animal> animals) {
        var res = animals.stream()
            .filter(animal -> (animal.type() == Animal.Type.SPIDER || animal.type() == Animal.Type.DOG)
                && animal.bites())
            .collect(Collectors.groupingBy(Animal::type));
        int numberOfDogs;
        int numberOfSpiders;
        try {
            numberOfDogs = res.get(Animal.Type.DOG).size();
            numberOfSpiders = res.get(Animal.Type.SPIDER).size();
        } catch (NullPointerException e) {
            return false;
        }
        return numberOfSpiders > numberOfDogs;
    }

    //Task18
    public static Optional<Animal> getMostHeavyFish(List<Animal>... animalLists) throws IllegalArgumentException {
        return Arrays.stream(animalLists).flatMap(List::stream).filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparingInt(Animal::weight));
    }

    //Task19
    public static Map<Animal, Set<ValidationError>> getAnimalsThatContainError(List<Animal> animals) {
        Map<Animal, Optional<Set<ValidationError>>> namesErrors = animals.stream()
            .collect(Collectors.toMap(animal -> animal, AnimalChecker::checkAnimal));
        return namesErrors.entrySet().stream()
            .filter(pair -> pair.getValue().isPresent())
            .collect(Collectors.toMap(Map.Entry::getKey, pair -> pair.getValue().get()));
    }

    //Task20
    public static Map<Animal, String> getAnimalsErrorsDescription(List<Animal> animals) {
        return getAnimalsThatContainError(animals).entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                pair -> convertErrorsIntoReadableInformation(pair.getKey().name(), pair.getValue())
            ));
    }

    private static String convertErrorsIntoReadableInformation(String animalName, Set<ValidationError> errors) {
        StringBuilder result = new StringBuilder();
        result.append(animalName).append(":\n");
        for (ValidationError currentError : errors) {
            result.append('\t').append(currentError.description()).append('\n');
        }
        return result.toString();
    }

    private AnimalManager() {

    }
}
