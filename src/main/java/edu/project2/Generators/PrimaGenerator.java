package edu.project2.Generators;

import edu.project2.CellType;
import edu.project2.Coordinate;
import edu.project2.Maze;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PrimaGenerator implements Generator {

    private Maze maze;
    private Set<Coordinate> anchorPoints;

    @Override
    public Maze generate(int height, int width) {
        maze = new Maze(height + 2, width + 2);
        anchorPoints = new HashSet<>();
        fillMazeWithWalls();
        fillMazeWithPaths();

        return maze;
    }

    private void fillMazeWithPaths() {
        Coordinate startPoint = chooseRandomStartingCoordinate(maze.getHeight(), maze.getWidth());
        maze.setPointType(startPoint, CellType.PASSAGE);
        anchorPoints.add(startPoint);

        while (!anchorPoints.isEmpty()) {
            startPoint = getRandomPoint(anchorPoints);
            Coordinate potentialAnchorPoint = getRandomTwoPointAwayAnchorPoint(startPoint);
            if (potentialAnchorPoint != null) {
                Coordinate passagePoint = getRandomTwoPointAwayPassagePoint(potentialAnchorPoint);
                Coordinate toPassagePoint = getMidpointBetweenTwoPoints(potentialAnchorPoint, passagePoint);
                maze.setPointType(toPassagePoint, CellType.PASSAGE);
                maze.setPointType(potentialAnchorPoint, CellType.PASSAGE);
                anchorPoints.add(potentialAnchorPoint);
            } else {
                anchorPoints.remove(startPoint);
            }
        }
    }

    @Nullable
    private Coordinate getRandomTwoPointAwayAnchorPoint(Coordinate startPoint) {
        List<Coordinate> points = new ArrayList<>();
        for (Coordinate currentPoint : getPointsThatTwoPointAwayFromCertainPoint(startPoint)) {
            if (this.maze.getPointType(currentPoint) == CellType.WALL) {
                points.add(currentPoint);
            }
        }
        if (points.isEmpty()) {
            return null;
        }
        return points.get(new Random().nextInt(points.size()));
    }

    private Coordinate getRandomTwoPointAwayPassagePoint(Coordinate startPoint) {
        List<Coordinate> points = new ArrayList<>();
        for (Coordinate currentPoint : getPointsThatTwoPointAwayFromCertainPoint(startPoint)) {
            if (this.maze.getPointType(currentPoint) == CellType.PASSAGE) {
                points.add(currentPoint);
            }
        }
        return points.get(new Random().nextInt(points.size()));
    }

    private List<Coordinate> getPointsThatTwoPointAwayFromCertainPoint(Coordinate startPoint) {
        List<Coordinate> points = new ArrayList<>();
        for (int rowOffset = -2; rowOffset <= 2; rowOffset += 2) {
            for (int columnOffset = -2; columnOffset <= 2; columnOffset += 2) {
                Coordinate currentCoordinate =
                    new Coordinate(startPoint.row() + rowOffset, startPoint.col() + columnOffset);
                if (Math.abs(rowOffset) != Math.abs(columnOffset) && isPointWithinBoundary(currentCoordinate)) {
                    points.add(currentCoordinate);
                }
            }
        }
        return points;
    }

    private boolean isPointWithinBoundary(Coordinate coordinate) {
        return coordinate.row() >= 1 && coordinate.row() < this.maze.getHeight() - 1 &&
            coordinate.col() >= 1 && coordinate.col() < this.maze.getWidth() - 1;
    }

    private Coordinate getRandomPoint(Set<Coordinate> points) {
        int item = new Random().nextInt(points.size());
        int i = 0;
        for (Coordinate currentCoordinate : points) {
            if (i == item) {
                return currentCoordinate;
            }
            i++;
        }
        throw new IllegalArgumentException("Set is empty");
    }

    private Coordinate getMidpointBetweenTwoPoints(Coordinate firstPoint, Coordinate secondPoint) {
        return new Coordinate((firstPoint.row() + secondPoint.row()) / 2, (firstPoint.col() + secondPoint.col()) / 2);
    }

    private Coordinate chooseRandomStartingCoordinate(int height, int width) {
        Random random = new Random();
        return new Coordinate(random.nextInt(1, height - 1), random.nextInt(1, width - 1));
    }

    private void fillMazeWithWalls() {
        for (int row = 0; row < maze.getHeight(); ++row) {
            for (int column = 0; column < maze.getWidth(); ++column) {
                maze.setPointType(row, column, CellType.WALL);
            }
        }
    }
}
