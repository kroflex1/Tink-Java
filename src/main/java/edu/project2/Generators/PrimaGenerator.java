package edu.project2.Generators;

import edu.project2.CellType;
import edu.project2.Coordinate;
import edu.project2.Maze;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.jetbrains.annotations.Nullable;

public class PrimaGenerator implements Generator {

    private Maze maze;

    @Override
    public Maze generate(int width, int height) {
        maze = new Maze(height, width);
        Coordinate startPoint = chooseRandomStartCoordinate(height, width);
        Set<Coordinate> anchorPoints = new HashSet<>();
        anchorPoints.add(startPoint);

        while (!anchorPoints.isEmpty()) {
            Coordinate point = getRandomPoint(anchorPoints);
            anchorPoints.remove(point);
            maze.setPointType(point, CellType.PASSAGE);
            tryConnectTwoPassage(point);
            anchorPoints.addAll(getTwoPointAwayWallPoints(point));
        }
        encloseMazeWithWall();
        return maze;
    }

    private void tryConnectTwoPassage(Coordinate passagePoint) {
        Coordinate otherPassagePoint = getRandomTwoPointAwayPassagePoint(passagePoint);
        if (otherPassagePoint == null) {
            return;
        }
        Coordinate midPoint = getMidpointBetweenTwoPoints(passagePoint, otherPassagePoint);
        maze.setPointType(midPoint, CellType.PASSAGE);
    }

    @Nullable
    private Coordinate getRandomTwoPointAwayPassagePoint(Coordinate startPoint) {
        List<Coordinate> points = new ArrayList<>();
        for (var currentPoint : maze.getNeighboringPoints(startPoint, 2)) {
            if (maze.getPointType(currentPoint) == CellType.PASSAGE) {
                points.add(currentPoint);
            }
        }
        if (points.isEmpty()) {
            return null;
        }
        return points.get(new Random().nextInt(points.size()));
    }

    private List<Coordinate> getTwoPointAwayWallPoints(Coordinate startPoint) {
        List<Coordinate> points = new ArrayList<>();
        for (Coordinate currentPoint : maze.getNeighboringPoints(startPoint, 2)) {
            if (this.maze.getPointType(currentPoint) == CellType.WALL) {
                points.add(currentPoint);
            }
        }
        return points;
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

    private Coordinate chooseRandomStartCoordinate(int height, int width) {
        Random random = new Random();
        return new Coordinate(random.nextInt(height), random.nextInt(width));
    }

    private Coordinate getMidpointBetweenTwoPoints(Coordinate firstPoint, Coordinate secondPoint) {
        return new Coordinate((firstPoint.row() + secondPoint.row()) / 2, (firstPoint.col() + secondPoint.col()) / 2);
    }

    private void encloseMazeWithWall() {
        for (int row = 0; row < maze.getHeight(); ++row) {
            maze.setPointType(row, 0, CellType.WALL);
            maze.setPointType(row, maze.getWidth() - 1, CellType.WALL);
        }
        for (int column = 0; column < maze.getWidth(); ++column) {
            maze.setPointType(0, column, CellType.WALL);
            maze.setPointType(maze.getHeight() - 1, column, CellType.WALL);
        }
    }
}
