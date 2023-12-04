package edu.project2.Renders;

import edu.project2.CellType;
import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.List;

public class SimpleRenderer implements Renderer {
    @Override
    public String render(Maze maze) {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < maze.getHeight(); ++row) {
            for (int column = 0; column < maze.getWidth(); ++column) {
                result.append(getCellDisplay(maze.getPointType(row, column)));
            }
            if (row != maze.getHeight() - 1) {
                result.append('\n');
            }
        }
        return result.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        maze.addPath(path);
        return render(maze);
    }

    private String getCellDisplay(CellType cellType) {
        return switch (cellType) {
            case CellType.PASSAGE -> " ";
            case CellType.WALL -> "▉";
            case CellType.PATH -> "·";
        };
    }

}
