package edu.hw3.Task5;

import java.util.Comparator;

public class HumanComparator implements Comparator<Human> {
    @Override
    public int compare(Human o1, Human o2) {
        if (o1.surname() == null && o2.surname() == null) {
            return o1.name().compareTo(o2.name());
        }
        if (o1.surname() == null) {
            return o1.name().compareTo(o2.surname());
        }
        if (o2.surname() == null) {
            String a = o1.surname();
            String b = o2.name();
            int value = a.compareTo(b);
            return o1.surname().compareTo(o2.name());
        }
        return o1.surname().compareTo(o2.surname());
    }
}
