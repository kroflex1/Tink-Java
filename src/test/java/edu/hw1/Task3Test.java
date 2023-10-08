package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task3Test {
    @Test
    @DisplayName("Проверка на вложенность")
    void checkNestable() {
        assertAll(
            () -> {
                int[] firstArray = new int[] {1, 2, 3, 4};
                int[] secondArray = new int[] {0, 6};
                assertTrue(Task3.isNestable(firstArray, secondArray));
            },
            () -> {
                int[] firstArray = new int[] {3, 1};
                int[] secondArray = new int[] {4, 0};
                assertTrue(Task3.isNestable(firstArray, secondArray));
            },
            () -> {
                int[] firstArray = new int[] {9, 9, 8};
                int[] secondArray = new int[] {8, 9};
                assertFalse(Task3.isNestable(firstArray, secondArray));
            },
            () -> {
                int[] firstArray = new int[] {1, 2, 3, 4};
                int[] secondArray = new int[] {2, 3};
                assertFalse(Task3.isNestable(firstArray, secondArray));
            },
            () -> {
                int[] firstArray = new int[] {2};
                int[] secondArray = new int[] {1, 3};
                assertTrue(Task3.isNestable(firstArray, secondArray));
            },
            () -> {
                int[] firstArray = new int[] {1, 2};
                int[] secondArray = new int[] {3};
                assertFalse(Task3.isNestable(firstArray, secondArray));
            }
        );
    }

    @Test
    @DisplayName("Проверка на вызов ошибки при получении пустых массивов")
    void isArraysNull() {
        assertAll(
            () -> {
                int[] firstArray = new int[] {};
                int[] secondArray = new int[] {1, 3};
                assertThrows(IllegalArgumentException.class, () ->
                    Task3.isNestable(firstArray, secondArray));
            },
            () -> {
                int[] firstArray = new int[] {};
                int[] secondArray = new int[] {1, 3};
                assertThrows(IllegalArgumentException.class, () ->
                    Task3.isNestable(firstArray, secondArray));
            },
            () -> {
                int[] firstArray = new int[] {};
                int[] secondArray = new int[] {1, 3};
                assertThrows(IllegalArgumentException.class, () ->
                    Task3.isNestable(firstArray, secondArray));
            }
        );
    }
}
