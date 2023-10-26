package edu.hw1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task8Test {
    @Test
    @DisplayName("Проверка стола на котором нет возможности захвата")
    void checkGoodTable() {
        int[][] table = {
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0}
        };
        Assertions.assertTrue(Task8.knightBoardCapture(table));
    }

    @Test
    @DisplayName("Проверка первого стола на котором есть возможность захвата")
    void checkFirstBadTable() {
        int[][] table = {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 1}
        };
        assertFalse(Task8.knightBoardCapture(table));
    }

    @Test
    @DisplayName("Проверка второго стола на котором есть возможность захвата")
    void checkSecondBadTable() {
        int[][] table = {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 1}
        };
        assertFalse(Task8.knightBoardCapture(table));
    }

    @Test
    @DisplayName("На столе присутствуют некорректные данные")
    void invalidTable() {
        int[][] table = {
            {4, 2, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 6, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 4, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 1}
        };
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            Task8.knightBoardCapture(table));
        assertEquals("The numbers on the table must be equal to zero or one", exception.getMessage());
    }

    @Test
    @DisplayName("Некорректный размер стола")
    void invalidTableSize() {
        int[][] table = {
            {4, 2, 1, 0},
            {0, 1, 0, 1},
            {0, 0, 0, 0},
            {0, 0, 1, 4},
            {1, 0, 0, 0},
        };
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            Task8.knightBoardCapture(table));
        assertEquals("The table should be 8x8 in size", exception.getMessage());
    }

    @Test
    @DisplayName("Некорректный размер стола 2")
    void invalidTableSize2() {
        int[][] table = {
            {1, 0, 1, 0, 1, 0, 1, 1},
            {0, 1, 0, 1},
            {0, 0, 0, 0},
            {0, 0, 1, 1},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
        };
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            Task8.knightBoardCapture(table));
        assertEquals("The table should be 8x8 in size", exception.getMessage());
    }

    @Test
    @DisplayName("Некорректный размер стола 3")
    void invalidTableSize3() {
        int[][] table = {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0},
            {0, 0, 0},
            {1, 0, 0},
            {0, 0, 0}
        };
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            Task8.knightBoardCapture(table));
        assertEquals("The table should be 8x8 in size", exception.getMessage());
    }
}
