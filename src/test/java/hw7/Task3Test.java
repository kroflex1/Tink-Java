package hw7;

import edu.hw7.Task3.Person;
import edu.hw7.Task3.PersonDatabase;
import edu.hw7.Task3.ReadWriteLockPersonDatabase;
import edu.hw7.Task3.SynchronizedPersonDatabase;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Task3Test {

    static Stream<Arguments> databases() {
        return Stream.of(
            arguments(new ReadWriteLockPersonDatabase()),
            arguments(new SynchronizedPersonDatabase())
        );
    }

    @ParameterizedTest
    @MethodSource("databases")
    void testAddPerson(PersonDatabase database) {
        Person person = new Person(1, "Vova", "Moscow", "+74829206543");
        database.add(person);
        assertEquals(person, database.findByName(person.name()).get(0));
        assertEquals(person, database.findByAddress(person.address()).get(0));
        assertEquals(person, database.findByPhone(person.phoneNumber()).get(0));
        database.delete(person.id());
        assertTrue(database.findByName(person.name()).isEmpty());
        assertTrue(database.findByAddress(person.address()).isEmpty());
        assertTrue(database.findByPhone(person.phoneNumber()).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("databases")
    void testDeletePersonWhoNotInDatabase(PersonDatabase database) {
        Person person = new Person(1, "Vova", "Moscow", "+74829206543");
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            database.delete(person.id()));
        assertEquals("There is no user with this id", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("databases")
    void testGetEmptyListIfPersonNotInDatabase(PersonDatabase database) {
        Person person = new Person(1, "Vova", "Moscow", "+74829206543");
        assertTrue(database.findByName(person.name()).isEmpty());
        assertTrue(database.findByName(person.name()).isEmpty());
        assertTrue(database.findByName(person.name()).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("databases")
    void testTryAddPeopleWithSameId(PersonDatabase database) {
        Person firstPerson = new Person(1, "Vova", "Moscow", "+74829206543");
        Person secondPerson = new Person(1, "Vasay", "Ekat", "+77648723972");
        database.add(firstPerson);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            database.add(secondPerson));
        assertEquals("Person with this id is already in the database", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("databases")
    void testAddPeopleToDatabaseWithConcurrency(PersonDatabase database) throws InterruptedException {
        List<Person> people = List.of(
            new Person(1, "Vova", "Moscow", "+7123"),
            new Person(2, "Vova", "New-York", "+7456"),
            new Person(3, "Misha", "Ekat", "+7789"),
            new Person(4, "Kolya", "Ekat", "+7789"),
            new Person(5, "Karl", "Moscow", "+7010"),
            new Person(6, "Dima", "Moscow", "+7010")
        );
        int numberOfThreads = people.size();
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(people.size());
        for (Person currentPerson : people) {
            service.execute(() -> {
                database.add(currentPerson);
                latch.countDown();
            });
        }
        latch.await();
        assertTrue(is_people_in_database(people, database));
    }

    @ParameterizedTest
    @MethodSource("databases")
    void testAddAndGetPersonWithConcurrency(PersonDatabase database) {
        Person person = new Person(1, "Vova", "Moscow", "+7123");
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(() -> database.add(person));
        CompletableFuture<List<Person>> futurePeopleByName =
            CompletableFuture.supplyAsync(() -> database.findByName(person.name()));
        CompletableFuture<List<Person>> futurePeopleByPhone =
            CompletableFuture.supplyAsync(() -> database.findByPhone(person.phoneNumber()));
        CompletableFuture<List<Person>> futurePeopleByAddress =
            CompletableFuture.supplyAsync(() -> database.findByAddress(person.address()));

        try {
            assertEquals(person, futurePeopleByName.get().get(0));
            assertEquals(person, futurePeopleByPhone.get().get(0));
            assertEquals(person, futurePeopleByAddress.get().get(0));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @MethodSource("databases")
    void testRemoveAndGetPersonWithConcurrency(PersonDatabase database) {
        Person person = new Person(1, "Vova", "Moscow", "+7123");
        database.add(person);
        Thread threadToRemove = new Thread(() -> database.delete(person.id()));
        threadToRemove.start();
        CompletableFuture<List<Person>> futurePeopleByName =
            CompletableFuture.supplyAsync(() -> database.findByName(person.name()));
        CompletableFuture<List<Person>> futurePeopleByPhone =
            CompletableFuture.supplyAsync(() -> database.findByPhone(person.phoneNumber()));
        CompletableFuture<List<Person>> futurePeopleByAddress =
            CompletableFuture.supplyAsync(() -> database.findByAddress(person.address()));
        try {
            assertTrue(futurePeopleByName.get().isEmpty());
            assertTrue(futurePeopleByPhone.get().isEmpty());
            assertTrue(futurePeopleByAddress.get().isEmpty());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean is_people_in_database(List<Person> people, PersonDatabase database) {
        for (Person currentPerson : people) {
            List<Person> peopleByName = database.findByName(currentPerson.name());
            List<Person> peopleByPhone = database.findByPhone(currentPerson.phoneNumber());
            List<Person> peopleByAddress = database.findByAddress(currentPerson.address());
            if (!peopleByName.contains(currentPerson) || !peopleByPhone.contains(currentPerson) ||
                !peopleByAddress.contains(currentPerson)) {
                return false;
            }
        }
        return true;
    }
}
