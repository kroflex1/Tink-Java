package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task4Test {
    @Test
    @DisplayName("Исправление строк")
    void fix() {
        Map<String, String> answers = new HashMap<String, String>();
        answers.put("123456", "214365");
        answers.put("hTsii  s aimex dpus rtni.g", "This is a mixed up string.");
        answers.put("badce", "abcde");
        for (Map.Entry<String, String> pair : answers.entrySet()) {
            String new_word = Task4.fixString(pair.getKey());
            assertEquals(pair.getValue(), new_word);
        }
    }
}
