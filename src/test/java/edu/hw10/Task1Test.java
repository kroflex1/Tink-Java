package edu.hw10;

import edu.hw10.Task1.Max;
import edu.hw10.Task1.Min;
import edu.hw10.Task1.RandomObjectGenerator;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task1Test {
    RandomObjectGenerator rog = new RandomObjectGenerator();

    @Test
    void testCreateSimpleObject() {
        Person person = rog.nextObject(Person.class);
        assertNotNull(person);
        assertNotNull(person.age);
        assertNotNull(person.name);
        assertNotNull(person.isParent);
    }

    @Test
    void testCreateRecordObject() {
        PersonRecord person = rog.nextObject(PersonRecord.class);
        assertNotNull(person);
        assertNotNull(person.age);
        assertNotNull(person.name);
        assertNotNull(person.isParent);
    }

    @Test
    void testCreateObjectByFactoryMethod() {
        Person person = (Person) rog.nextObject(FactoryClass.class, "create");
        assertNotNull(person);
        assertNotNull(person.age);
        assertNotNull(person.name);
        assertNotNull(person.isParent);
    }

    @Test
    void testCreateClassWithComplexStructures() {
        ClassWithComplexStructures hardObject = rog.nextObject(ClassWithComplexStructures.class);
        assertNotNull(hardObject);
        assertNull(hardObject.date);
        assertNotNull(hardObject.numberOfPeople);
    }

    @Test
    void testCantFindFactoryMethodWithRegex() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            rog.nextObject(FactoryClass.class, "make"));
        assertEquals("Not found public static factory method in this class", exception.getMessage());
    }

    @Test
    void testCantFindPublicAndStaticFactoryMethod() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            rog.nextObject(PersonRecord.class, "create"));
        assertEquals("Not found public static factory method in this class", exception.getMessage());
    }

    @Test
    void testClassWithMinAndMaxAnnotation() {
        ClassWithAnnotation classWithAnnotation = rog.nextObject(ClassWithAnnotation.class);
        assertNotNull(classWithAnnotation);
        assertTrue(classWithAnnotation.age >= 10 && classWithAnnotation.age <= 90);
    }

    @Test
    void testClassWithMinAndMaxAnnotation2() {
        ClassWithAnnotation classWithAnnotation =(ClassWithAnnotation) rog.nextObject(ClassWithAnnotation.class, "create");
        assertNotNull(classWithAnnotation);
        assertTrue(classWithAnnotation.age >= 10 && classWithAnnotation.age <= 90);
    }

    public static class Person {
        public Integer age;
        public String name;
        public Boolean isParent;

        public Person(int age, String name, boolean isParent) {
            this.age = age;
            this.name = name;
            this.isParent = isParent;
        }
    }

    public record PersonRecord(Integer age, String name, Boolean isParent) {
        public PersonRecord createNewPersonWithName(String newName) {
            return new PersonRecord(age, newName, isParent);
        }
    }

    public static class FactoryClass {
        public static Person createPerson(Integer age, String name, Boolean isParent) {
            return new Person(age, name, isParent);
        }
    }

    public static class ClassWithComplexStructures {
        private LocalDate date;
        private Double numberOfPeople;

        public ClassWithComplexStructures(LocalDate date, Double numberOfPeople) {
            this.date = date;
            this.numberOfPeople = numberOfPeople;
        }
    }

    public static class ClassWithAnnotation {
        public int age;

        public ClassWithAnnotation(@Min(minValue = 10)  @Max(maxValue = 20)  int age) {
            this.age = age;
        }

        public static ClassWithAnnotation createNew(@Min(minValue = 10) @Max(maxValue = 20) int age) {
            return new ClassWithAnnotation(age);
        }
    }
}
