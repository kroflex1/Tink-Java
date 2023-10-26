package edu.hw3.Task5;

import java.util.ArrayList;
import java.util.List;

public class ContactParser {
    public static List<Human> parseContacts(List<Human> people, SortingMethod sortingMethod) {
        if (people == null) {
            return new ArrayList<>();
        }
        List<Human> sortedPeople = new ArrayList<>(people);
        if (sortingMethod.equals(SortingMethod.ASC)) {
            sortedPeople.sort(new HumanComparator());
        } else {
            sortedPeople.sort(new HumanComparator().reversed());
        }
        return sortedPeople;
    }

    private ContactParser() {

    }
}
