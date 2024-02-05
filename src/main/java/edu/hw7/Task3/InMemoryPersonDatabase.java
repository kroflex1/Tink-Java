package edu.hw7.Task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class InMemoryPersonDatabase implements PersonDatabase {
    private final Map<Integer, Person> people = new HashMap<>();
    private final Map<String, Set<Person>> peopleByName = new HashMap<>();
    private final Map<String, Set<Person>> peopleByAddress = new HashMap<>();
    private final Map<String, Set<Person>> peopleByPhone = new HashMap<>();

    @Override
    public void add(Person person) {
        int reverseIndex = getReverseId(person.id());
        if (people.containsKey(reverseIndex)) {
            throw new IllegalArgumentException("Person with this id is already in the database");
        }
        people.put(reverseIndex, person);

        peopleByName.putIfAbsent(person.name(), new HashSet<>());
        peopleByName.get(person.name()).add(person);

        peopleByAddress.putIfAbsent(person.address(), new HashSet<>());
        peopleByAddress.get(person.address()).add(person);

        peopleByPhone.putIfAbsent(person.phoneNumber(), new HashSet<>());
        peopleByPhone.get(person.phoneNumber()).add(person);
    }

    @Override
    public void delete(int id) {
        int reverseID = getReverseId(id);
        if (!people.containsKey(reverseID)) {
            throw new IllegalArgumentException("There is no user with this id");
        }
        Person personToDelete = people.get(reverseID);
        peopleByName.get(personToDelete.name()).remove(personToDelete);
        peopleByAddress.get(personToDelete.address()).remove(personToDelete);
        peopleByPhone.get(personToDelete.phoneNumber()).remove(personToDelete);
        people.remove(reverseID);
    }

    @Override
    public List<Person> findByName(String name) {
        return new ArrayList<>(peopleByName.getOrDefault(name, new HashSet<>()));
    }

    @Override
    public List<Person> findByAddress(String address) {
        return new ArrayList<>(peopleByAddress.getOrDefault(address, new HashSet<>()));
    }

    @Override
    public List<Person> findByPhone(String phone) {
        return new ArrayList<>(peopleByPhone.getOrDefault(phone, new HashSet<>()));
    }

    @SuppressWarnings({"MagicNumber", "ParameterAssignment"})
    private int getReverseId(int id) {
        int reverseId = 0;
        while (id != 0) {
            int remainder = id % 10;
            reverseId = reverseId * 10 + remainder;
            id /= 10;
        }
        return reverseId;
    }
}
