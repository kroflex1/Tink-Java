package edu.hw3;

import edu.hw3.Task5.ContactParser;
import edu.hw3.Task5.Human;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import edu.hw3.Task5.HumanComparator;
import edu.hw3.Task5.SortingMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task5Test {

    static Stream<Arguments> twoHumans() {
        return Stream.of(
            arguments(new Human("Arnold", "Archer"), new Human("Ben", "Belo"), -1),
            arguments(new Human("Ben", "Belo"), new Human("Arnold", "Archer"), 1),
            arguments(new Human("Arnold", "Archer"), new Human("Ben", "Archer"), 0),
            arguments(new Human("Arnold"), new Human("Ben"), -1),
            arguments(new Human("Arnold", "Gustavo"), new Human("Ben"), 1),
            arguments(new Human("Zandro"), new Human("Andrew", "Gustavo"), 1)
        );
    }

    static Stream<Arguments> people() {
        return Stream.of(
            arguments(
                convertStringListToHumanList(Arrays.asList(
                    "John Locke",
                    "Thomas Aquinas",
                    "David Hume",
                    "Rene Descartes"
                )),
                SortingMethod.ASC,
                convertStringListToHumanList(Arrays.asList(
                    "Thomas Aquinas",
                    "Rene Descartes",
                    "David Hume",
                    "John Locke"
                ))
            ),
            arguments(
                convertStringListToHumanList(Arrays.asList("Paul Erdos", "Leonhard Euler", "Carl Gauss")),
                SortingMethod.DESC,
                convertStringListToHumanList(Arrays.asList("Carl Gauss", "Leonhard Euler", "Paul Erdos"))
            ),
            arguments(
                convertStringListToHumanList(Arrays.asList("Ben", "Leonhard", "Carl Gauss")),
                SortingMethod.ASC,
                convertStringListToHumanList(Arrays.asList("Ben", "Carl Gauss", "Leonhard"))
            ),
            arguments(
                new ArrayList<Human>(),
                SortingMethod.DESC,
                new ArrayList<Human>()
            ),
            arguments(
                null,
                SortingMethod.DESC,
                new ArrayList<Human>()
            )
        );
    }

    @ParameterizedTest
    @MethodSource("twoHumans")
    @DisplayName("HumanComparator корректно сравнивает")
    void testHumanComparator(Human firstHuman, Human secondHuman, int except) {
        if (except == 1) {
            assertTrue(new HumanComparator().compare(firstHuman, secondHuman) >= 1);
        } else if (except == -1) {
            assertTrue(new HumanComparator().compare(firstHuman, secondHuman) <= -1);
        }
        if (except == 0) {
            assertTrue(new HumanComparator().compare(firstHuman, secondHuman) == 0);
        }
    }

    @ParameterizedTest
    @MethodSource("people")
    @DisplayName("Сортировка людей")
    void testSorting(List<Human> people, SortingMethod sortingMethod, List<Human> except) {
        assertEquals(except, ContactParser.parseContacts(people, sortingMethod));
    }

    private static List<Human> convertStringListToHumanList(List<String> peopleName) {
        List<Human> newList = new ArrayList<>();
        for (String line : peopleName) {
            newList.add(convertStringToHuman(line));
        }
        return newList;
    }

    private static Human convertStringToHuman(String line) {
        String[] words = line.split(" ");
        String name = words[0];
        if (words.length == 2) {
            return new Human(name, words[1]);
        }
        return new Human(name);
    }
}
