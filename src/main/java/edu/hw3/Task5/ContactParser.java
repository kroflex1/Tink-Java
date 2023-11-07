package edu.hw3.Task5;

import java.util.ArrayList;
import java.util.List;

public class ContactParser {
    public static List<Human> parseContacts(List<String> people, SortingMethod sortingMethod) {
        if (people == null) {
            return new ArrayList<>();
        }
        return people.stream()
            .map(human -> {
                var nameParts = human.split(" ");
                return new Human(nameParts[0], nameParts.length == 2 ? nameParts[1] : null);
            })
            .sorted(sortingMethod.equals(SortingMethod.ASC) ? new HumanComparator() : new HumanComparator().reversed())
            .toList();
    }

    private ContactParser() {

    }
}
