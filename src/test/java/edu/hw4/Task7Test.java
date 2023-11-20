package edu.hw4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task7Test {
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    @DisplayName("Получение K-е самое старое животное")
    void test(int position) {
        Animal firstOldAnimal = new Animal("Ari", Animal.Type.CAT, Animal.Sex.M, 100, 1, 1, true);
        Animal secondOldestAnimal = new Animal("Mura", Animal.Type.DOG, Animal.Sex.M, 70, 1, 1, true);
        Animal thirdOldestAnimal = new Animal("Reto", Animal.Type.DOG, Animal.Sex.M, 50, 1, 1, true);
        Animal fourthOldestAnimal = new Animal("Luma", Animal.Type.BIRD, Animal.Sex.M, 30, 1, 1, true);
        Animal fifthOldesAnimal = new Animal("Mai", Animal.Type.FISH, Animal.Sex.M, 10, 1, 1, true);
        Map<Integer, Animal> except = new HashMap<>() {{
            put(1, firstOldAnimal);
            put(2, secondOldestAnimal);
            put(3, thirdOldestAnimal);
            put(4, fourthOldestAnimal);
            put(5, fifthOldesAnimal);
        }};
        List<Animal> animals =
            List.of(thirdOldestAnimal, firstOldAnimal, fourthOldestAnimal, fifthOldesAnimal, secondOldestAnimal);
        assertEquals(except.get(position), AnimalManager.getOldestAnimal(animals, position));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 3})
    @DisplayName("Вызов ошибки при неправильном поданном K-ом месте")
    void testInvalidAnimalPosition(int position){
        Animal firstOldAnimal = new Animal("Ari", Animal.Type.CAT, Animal.Sex.M, 100, 1, 1, true);
        Animal secondOldestAnimal = new Animal("Mura", Animal.Type.DOG, Animal.Sex.M, 70, 1, 1, true);
        List<Animal> animals = List.of(firstOldAnimal,  secondOldestAnimal);
        assertThrows(IllegalArgumentException.class, () ->
            AnimalManager.getOldestAnimal(animals, position));
    }

    @Test
    @DisplayName("Вызов ошибки при пустом списке")
    void testInvalidAnimalPosition(){
        assertThrows(IllegalArgumentException.class, () ->
            AnimalManager.getOldestAnimal(new ArrayList<>(), 1));
    }
}
