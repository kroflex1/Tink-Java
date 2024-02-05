package edu.project2;

import edu.project2.Renders.Renderer;
import edu.project2.Renders.SimpleRenderer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleRendererTest {
    static Arguments[] mazes() {
        int[][] firstGrid = new int[][] {
            {0, 1, 1},
            {0, 0, 1},
            {1, 0, 0}
        };
        String firstMazeRender = " ▉▉\n  ▉\n▉  ";

        int[][] secondGrid = new int[][] {
            {0, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 1, 1, 1},
            {0, 1, 1, 1},
        };
        String secondMazeRender = " ▉▉▉\n    \n ▉▉▉\n ▉▉▉";
        return new Arguments[] {
            Arguments.of(MazeHelper.convertToMaze(firstGrid), firstMazeRender),
            Arguments.of(MazeHelper.convertToMaze(secondGrid), secondMazeRender)
        };
    }

    static Arguments[] mazesWithPath() {
        int[][] firstGrid = new int[][] {
            {0, 1, 1},
            {0, 0, 1},
            {1, 0, 0}
        };
        List<Coordinate> firstMazePath = List.of(new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(1, 1));
        String firstMazeRender = "·▉▉\n··▉\n▉  ";

        int[][] secondGrid = new int[][] {
            {0, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 1, 1, 1},
            {0, 1, 1, 1},
        };
        List<Coordinate> secondMazePath =
            List.of(new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(1, 3));
        String secondMazeRender = " ▉▉▉\n····\n ▉▉▉\n ▉▉▉";
        return new Arguments[] {
            Arguments.of(MazeHelper.convertToMaze(firstGrid), firstMazePath, firstMazeRender),
            Arguments.of(MazeHelper.convertToMaze(secondGrid), secondMazePath, secondMazeRender)
        };
    }

    @ParameterizedTest
    @MethodSource("mazesWithPath")
    void testMazeWithPathRender(Maze maze, List<Coordinate> path, String exceptRender) {
        Renderer simpleRenderer = new SimpleRenderer();
        String result = simpleRenderer.render(maze, path);
        assertEquals(exceptRender, result);
    }
}
