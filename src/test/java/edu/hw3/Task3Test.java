package edu.hw3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task3Test {

    @Test
    @DisplayName("Частотный словарь для String")
    void testStrings() {
        List<String> objects = Arrays.asList("this", "and", "that", "and");
        Map<String, Integer> expect = new HashMap<>() {{
            put("this", 1);
            put("and", 2);
            put("that", 1);
        }};
        assertEquals(expect, new Task3<String>().freqDict(objects));
    }

    @Test
    @DisplayName("Частотный словарь для Integer")
    void testIntegers() {
        List<Integer> objects = Arrays.asList(1, 1, 2, 2);
        Map<Integer, Integer> expect = new HashMap<>() {{
            put(1, 2);
            put(2, 2);
        }};
        assertEquals(expect, new Task3<Integer>().freqDict(objects));
    }

    @Test
    @DisplayName("Частотный словарь для List<Integer>")
    void testLists() {
        List<List<Integer>> objects =
            Arrays.asList(Arrays.asList(1, 1), Arrays.asList(1, 1), Arrays.asList(2, 2), Arrays.asList(3, 5));
        Map<List<Integer>, Integer> expect = new HashMap<>() {{
            put(Arrays.asList(1, 1), 2);
            put(Arrays.asList(2, 2), 1);
            put(Arrays.asList(3, 5), 1);

        }};
        assertEquals(expect, new Task3<List<Integer>>().freqDict(objects));
    }

}
