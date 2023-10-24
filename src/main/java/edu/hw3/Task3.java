package edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task3<E> {
    public Map<E, Integer> freqDict(List<E> objects) {
        Map<E, Integer> map = new HashMap<>();
        for (E object : objects) {
            if (!map.containsKey(object)) {
                map.put(object, 1);
            } else {
                map.put(object, map.get(object) + 1);
            }
        }
        return map;
    }
}
