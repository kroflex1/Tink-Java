package edu.project2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MazeTest {

    static Arguments[] neighboringPoints() {
        Maze maze3x3 = new Maze(3, 3);
        Maze maze5x5 = new Maze(5, 5);
        return new Arguments[] {
            Arguments.of(
                maze3x3,
                new Coordinate(1, 1),
                1,
                List.of(new Coordinate(0, 1), new Coordinate(2, 1), new Coordinate(1, 2), new Coordinate(1, 0))
            ),
            Arguments.of(
                maze3x3,
                new Coordinate(0, 0),
                1,
                List.of(new Coordinate(1, 0), new Coordinate(0, 1))
            ),
            Arguments.of(
                maze3x3,
                new Coordinate(2, 2),
                1,
                List.of(new Coordinate(2, 1), new Coordinate(1, 2))
            ),
            Arguments.of(
                maze5x5,
                new Coordinate(2, 2),
                2,
                List.of(new Coordinate(0, 2), new Coordinate(4, 2), new Coordinate(2, 4), new Coordinate(2, 0))
            ),
            Arguments.of(
                maze5x5,
                new Coordinate(0, 0),
                2,
                List.of(new Coordinate(0, 2), new Coordinate(2, 0))
            ),
            Arguments.of(
                maze5x5,
                new Coordinate(1, 0),
                2,
                List.of(new Coordinate(1, 2), new Coordinate(3, 0))
            ),
        };
    }

    @ParameterizedTest
    @MethodSource("neighboringPoints")
    @DisplayName("Корректное нахождение соседних точек")
    void testGetNeighboringPoints(Maze maze, Coordinate startPoint, int distance, List<Coordinate> except) {
        assertTrue(except.containsAll(maze.getNeighboringPoints(startPoint, distance)));
    }

    @Test
    @DisplayName("Невозможно найти соседние точки")
    void testCantGetNeighboringPoints() {
        Maze maze = new Maze(3, 3);
        Exception negativeDistanceException = assertThrows(IllegalArgumentException.class, () ->
            maze.getNeighboringPoints(new Coordinate(1, 1), -1));
        Exception pointOutBoundaryException = assertThrows(IllegalArgumentException.class, () ->
            maze.getNeighboringPoints(new Coordinate(-1, -1), 1));
        assertEquals("Distance must be greater than zero", negativeDistanceException.getMessage());
        assertEquals("Point(-1; -1) is located outside boundaries of maze", pointOutBoundaryException.getMessage());
    }

    @Test
    @DisplayName("Прокладывание маршрута в лабиринте")
    void testAddPathToMaze() {
        int[][] grid = new int[][] {
            {0, 1, 1},
            {0, 0, 1},
            {1, 0, 0}
        };
        Maze maze = MazeHelper.convertToMaze(grid);
        List<Coordinate> path = List.of(
            new Coordinate(0, 0),
            new Coordinate(1, 0),
            new Coordinate(1, 1),
            new Coordinate(2, 1),
            new Coordinate(2, 2)
        );
        maze.addPath(path);
        for (Coordinate currentPoint : path) {
            assertEquals(CellType.PATH, maze.getPointType(currentPoint));
        }
    }

    @Test
    @DisplayName("Вызов ошибки при некорректном маршруте")
    void testInvalidPath() {
        int[][] grid = new int[][] {
            {0, 1, 1},
            {0, 0, 1},
            {1, 0, 0}
        };
        Maze maze = MazeHelper.convertToMaze(grid);
        List<Coordinate> wallPath = List.of(
            new Coordinate(0, 0),
            new Coordinate(1, 0),
            new Coordinate(2, 0)
        );
        List<Coordinate> outOfBoundaryPath = List.of(
            new Coordinate(0, 0),
            new Coordinate(0, -1)
        );
        Exception wallPathException = assertThrows(IllegalArgumentException.class, () ->
           maze.addPath(wallPath));
        Exception outBoundaryPathException = assertThrows(IllegalArgumentException.class, () ->
            maze.addPath(outOfBoundaryPath));
        assertEquals("Point(2; 0), can't build a path through a wall", wallPathException.getMessage());
        assertEquals("Point(0; -1) is located outside boundaries of maze", outBoundaryPathException.getMessage());
    }
}
