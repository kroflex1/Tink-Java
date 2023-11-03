package edu.project2;

import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PrimaGenerator implements Generator {

    private Set<Coordinate> anchorPoints;
    private Maze maze;

    @Override
    public Maze generate(int height, int width) {
        this.maze = new Maze(height + 2, width + 2);
        fillMazeWithWalls(this.maze);
        this.anchorPoints = new HashSet<>();
        Coordinate startPoint = chooseRandomStartingCoordinate(this.maze.getHeight(), this.maze.getWidth());
        maze.setPointType(startPoint, CellType.PASSAGE);

        this.anchorPoints.add(startPoint);
        while (!this.anchorPoints.isEmpty()) {
            Coordinate currentPoint = getRandomPointFromAnchorPoints();
            Coordinate newAnchorPoint = getRandomTwoPointAwayWallPoint(currentPoint);
            if (newAnchorPoint != null) {
                Coordinate passagePoint = getRandomTwoPointAwayPassagePoint(newAnchorPoint);
                Coordinate toPassagePoint =
                    new Coordinate(
                        newAnchorPoint.row() - passagePoint.row(),
                        newAnchorPoint.col() - passagePoint.col()
                    );
                this.maze.setPointType(newAnchorPoint, CellType.PASSAGE);
                this.maze.setPointType(toPassagePoint, CellType.PASSAGE);
                this.anchorPoints.add(newAnchorPoint);
            } else {
                this.anchorPoints.remove(currentPoint);
            }
        }

        return this.maze;
    }

    @Nullable
    private Coordinate getRandomTwoPointAwayWallPoint(Coordinate startPoint) {
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

    private boolean isCoordinateWithinBoundary(Coordinate coordinate) {
        return coordinate.row() >= 1 && coordinate.row() < this.maze.getHeight() - 1 &&
            coordinate.col() >= 0 && coordinate.col() < this.maze.getWidth() - 1;
    }

    private List<Coordinate> getPointsThatTwoPointAwayFromCertainPoint(Coordinate startPoint) {
        List<Coordinate> points = new ArrayList<>();
        for (int rowOffset = -2; rowOffset <= 2; rowOffset += 2) {
            for (int columnOffset = -2; columnOffset <= 2; columnOffset += 2) {
                Coordinate currentCoordinate =
                    new Coordinate(startPoint.row() + rowOffset, startPoint.col() + columnOffset);
                if (Math.abs(rowOffset) != Math.abs(columnOffset) && isCoordinateWithinBoundary(currentCoordinate)) {
                    points.add(currentCoordinate);
                }
            }
        }
        return points;
    }

    private Coordinate getRandomPointFromAnchorPoints() {
        int size = anchorPoints.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for (Coordinate currentCoordinate : this.anchorPoints) {
            if (i == item) {
                return currentCoordinate;
            }
            i++;
        }
        throw new IllegalArgumentException("Set is empty");
    }

    private Coordinate chooseRandomStartingCoordinate(int height, int width) {
        Random random = new Random();
        return new Coordinate(random.nextInt(1, height), random.nextInt(1, width));
    }

    private void fillMazeWithWalls(Maze maze) {
        for (int row = 0; row < maze.getHeight(); ++row) {
            for (int column = 0; column < maze.getWidth(); ++column) {
                maze.setPointType(row, column, CellType.WALL);
            }
        }
    }
}
