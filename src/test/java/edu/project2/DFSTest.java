package edu.project2;

import edu.project2.Solvers.DFS;
import edu.project2.Solvers.Solver;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DFSTest {
    static Arguments[] mazes() {
        int[][] firstGrid = new int[][] {
            {0, 0, 0}
        };
        List<Coordinate> pathForFirstGrid = List.of(
            new Coordinate(0, 0),
            new Coordinate(0, 1),
            new Coordinate(0, 2)
        );

        int[][] secondGrid = new int[][] {
            {0},
            {0},
            {0}
        };
        List<Coordinate> pathForSecondGrid = List.of(
            new Coordinate(0, 0),
            new Coordinate(1, 0),
            new Coordinate(2, 0)
        );

        int[][] thirdGrid = new int[][] {
            {0, 1, 1},
            {0, 0, 1},
            {1, 0, 0}
        };
        List<Coordinate> pathForThirdGrid = List.of(
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
        List<Coordinate> pathForFourthGrid1 = List.of(
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
        List<Coordinate> pathForFourthGrid2 = List.of(
            new Coordinate(0, 0),
            new Coordinate(1, 0),
            new Coordinate(2, 0),
            new Coordinate(3, 0),
            new Coordinate(4, 0),
            new Coordinate(4, 1),
            new Coordinate(4, 2),
            new Coordinate(3, 2),
            new Coordinate(2, 2),
            new Coordinate(2, 3),
            new Coordinate(2, 4),
            new Coordinate(3, 4),
            new Coordinate(4, 4)
        );

        return new Arguments[] {
            Arguments.of(MazeHelper.convertToMaze(firstGrid), List.of(pathForFirstGrid)),
            Arguments.of(MazeHelper.convertToMaze(secondGrid), List.of(pathForSecondGrid)),
            Arguments.of(MazeHelper.convertToMaze(thirdGrid), List.of(pathForThirdGrid)),
            Arguments.of(MazeHelper.convertToMaze(fourthGrid), List.of(pathForFourthGrid1, pathForFourthGrid2))
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
    @DisplayName("Нахождение пути")
    void testFindPath(Maze maze, List<List<Coordinate>> paths) {
        Solver solver = new DFS();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);
        boolean isSame = false;
        for (List<Coordinate> currentPath : paths) {
            var x = solver.solve(maze, start, end);
            isSame = currentPath.equals(x);
            if (isSame) {
                break;
            }
        }
        assertTrue(isSame);
    }

    @ParameterizedTest
    @MethodSource("unattainablePath")
    @DisplayName("Вызов ошибки, при невозможности достичь другой точки")
    void testImpossibleToPavePath(Maze maze) {
        Solver solver = new DFS();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(maze.getHeight() - 1, maze.getWidth() - 1);
        Exception exception = assertThrows(RuntimeException.class, () ->
            solver.solve(maze, start, end));
        assertEquals("Impossible to find the path  from a given start point", exception.getMessage());
    }

    @Test
    @DisplayName("Вызов ошибки, когда одна из заданных точек является стеной")
    void testStartOrEndPointIsWall() {
        int[][] grid = new int[][] {
            {0, 1, 1},
            {1, 1, 1}
        };
        Maze maze = MazeHelper.convertToMaze(grid);
        Solver solver = new DFS();
        assertThrows(IllegalArgumentException.class, () ->
            solver.solve(maze, new Coordinate(0, 0), new Coordinate(0, 1)));
        assertThrows(IllegalArgumentException.class, () ->
            solver.solve(maze, new Coordinate(0, 1), new Coordinate(0, 0)));
        assertThrows(IllegalArgumentException.class, () ->
            solver.solve(maze, new Coordinate(0, 1), new Coordinate(1, 1)));
    }
}
