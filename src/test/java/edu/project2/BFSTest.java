package edu.project2;

import edu.project2.Solvers.BFS;
import edu.project2.Solvers.Solver;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BFSTest {
    static Arguments[] mazes() {
        int[][] firstGrid = new int[][] {
            {0, 0, 0}
        };
        List<Coordinate> shortestPathForFirstGrid = List.of(
            new Coordinate(0, 0),
            new Coordinate(0, 1),
            new Coordinate(0, 2)
        );

        int[][] secondGrid = new int[][] {
            {0},
            {0},
            {0}
        };
        List<Coordinate> shortestPathForSecondGrid = List.of(
            new Coordinate(0, 0),
            new Coordinate(1, 0),
            new Coordinate(2, 0)
        );

        int[][] thirdGrid = new int[][] {
            {0, 1, 1},
            {0, 0, 1},
            {1, 0, 0}
        };
        List<Coordinate> shortestPathForThirdGrid = List.of(
            new Coordinate(0, 0),
            new Coordinate(1, 0),
            new Coordinate(1, 1),
            new Coordinate(2, 1),
            new Coordinate(2, 2)
        );

        int[][] fourthGrid = new int[][] {
            {0, 1, 1, 1, 1},
            {0, 0, 0, 1, 1},
            {0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0},
        };
        List<Coordinate> shortestPathForFourthGrid = List.of(
            new Coordinate(0, 0),
            new Coordinate(1, 0),
            new Coordinate(1, 1),
            new Coordinate(1, 2),
            new Coordinate(2, 2),
            new Coordinate(2, 3),
            new Coordinate(2, 4),
            new Coordinate(3, 4),
            new Coordinate(4, 4)
        );

        int[][] fifthGrid = new int[][] {
            {0, 1, 0, 0, 0, 1, 1, 1, 1},
            {0, 1, 0, 1, 0, 1, 1, 0, 0},
            {0, 0, 0, 1, 0, 1, 1, 0, 0},
            {0, 1, 1, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        List<Coordinate> shortestPathForFifthGrid = List.of(
            new Coordinate(0, 0),
            new Coordinate(1, 0),
            new Coordinate(2, 0),
            new Coordinate(3, 0),
            new Coordinate(4, 0),
            new Coordinate(5, 0),
            new Coordinate(5, 1),
            new Coordinate(5, 2),
            new Coordinate(5, 3),
            new Coordinate(5, 4),
            new Coordinate(5, 5),
            new Coordinate(5, 6),
            new Coordinate(5, 7),
            new Coordinate(5, 8)
        );
        return new Arguments[] {
            Arguments.of(MazeHelper.convertToMaze(firstGrid), shortestPathForFirstGrid),
            Arguments.of(MazeHelper.convertToMaze(secondGrid), shortestPathForSecondGrid),
            Arguments.of(MazeHelper.convertToMaze(thirdGrid), shortestPathForThirdGrid),
            Arguments.of(MazeHelper.convertToMaze(fourthGrid), shortestPathForFourthGrid),
            Arguments.of(MazeHelper.convertToMaze(fifthGrid), shortestPathForFifthGrid)
        };
    }

    static Arguments[] unattainablePath() {
        int[][] firstGrid = new int[][] {
            {0, 1, 1},
            {0, 1, 1},
            {1, 1, 0}
        };

        int[][] secondGrid = new int[][] {
            {0, 1, 1, 1, 1},
            {0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1},
            {0, 1, 1, 1, 0},
            {0, 0, 1, 0, 0},
        };
        return new Arguments[] {
            Arguments.of(MazeHelper.convertToMaze(firstGrid)),
            Arguments.of(MazeHelper.convertToMaze(secondGrid)),
        };
    }

    @ParameterizedTest
    @MethodSource("mazes")
    @DisplayName("Нахождение кратчайшего пути")
    void testFindShortestPath(Maze maze, List<Coordinate> shortestPath) {
        Solver solver = new BFS();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);
        assertEquals(shortestPath, solver.solve(maze, start, end));
    }

    @ParameterizedTest
    @MethodSource("unattainablePath")
    @DisplayName("Вызов ошибки, при невозможности достичь другой точки")
    void testImpossibleToPavePath(Maze maze) {
        Solver solver = new BFS();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);
        Exception exception = assertThrows(RuntimeException.class, () ->
            solver.solve(maze, start, end));
        assertEquals("Impossible to find the path  from a given start point", exception.getMessage());
    }

    @Test
    @DisplayName("Вызов ошибки, когда одна из заданных точек является стеной")
    void testStartOrEndPointIsWall() {
        int[][] grid = new int[][]{
            {0,1,1},
            {1,1,1}
        };
        Maze maze = MazeHelper.convertToMaze(grid);
        Solver solver = new BFS();
        assertThrows(IllegalArgumentException.class, () ->
            solver.solve(maze, new Coordinate(0,0), new Coordinate(0,1)));
        assertThrows(IllegalArgumentException.class, () ->
            solver.solve(maze, new Coordinate(0,1), new Coordinate(0,0)));
        assertThrows(IllegalArgumentException.class, () ->
            solver.solve(maze, new Coordinate(0,1), new Coordinate(1,1)));
    }
}
