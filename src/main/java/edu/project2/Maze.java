package edu.project2;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    private final int height;
    private final int width;
    private CellType[][] grid;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new CellType[height][width];
        for (int row = 0; row < height; ++row) {
            for (int column = 0; column < width; ++column) {
                setPointType(row, column, CellType.WALL);
            }
        }
    }

    public List<Coordinate> getNeighboringPoints(Coordinate point, int distance) throws IllegalArgumentException {
        if (!isPointInBoundary(point)) {
            throw new IllegalArgumentException(getMessageForOutOfBoundaryPoint(point));
        }
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance must be greater than zero");
        }
        List<Coordinate> points = new ArrayList<>();
        for (int rowOffset = -distance; rowOffset <= distance; rowOffset += distance) {
            for (int columnOffset = -distance; columnOffset <= distance; columnOffset += distance) {
                Coordinate currentPoint = new Coordinate(point.row() + rowOffset, point.col() + columnOffset);
                if (Math.abs(rowOffset) != Math.abs(columnOffset) && isPointInBoundary(currentPoint)) {
                    points.add(currentPoint);
                }
            }
        }
        return points;
    }

    public List<Coordinate> getNeighboringPoints(Coordinate point) {
        return getNeighboringPoints(point, 1);
    }

    public void addPath(List<Coordinate> path) throws IllegalArgumentException {
        for (Coordinate currentPoint : path) {
            if (!isPointInBoundary(currentPoint)) {
                throw new IllegalArgumentException(getMessageForOutOfBoundaryPoint(currentPoint));
            }
            if (getPointType(currentPoint) == CellType.WALL) {
                throw new IllegalArgumentException(String.format(
                    "Point(%d; %d), can't build a path through a wall",
                    currentPoint.row(),
                    currentPoint.col()
                ));
            }
            setPointType(currentPoint, CellType.PATH);
        }
    }


    public boolean isPointInBoundary(Coordinate point) {
        return point.row() >= 0 && point.row() < height && point.col() >= 0 && point.col() < width;
    }

    public void setPointType(Coordinate coordinate, CellType cellType) {
        this.setPointType(coordinate.row(), coordinate.col(), cellType);
    }

    public void setPointType(int row, int column, CellType cellType) {
        this.grid[row][column] = cellType;
    }

    public CellType getPointType(Coordinate coordinate) {
        return this.getPointType(coordinate.row(), coordinate.col());
    }

    public CellType getPointType(int row, int column) {
        return this.grid[row][column];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private String getMessageForOutOfBoundaryPoint(Coordinate point) {
        return String.format(
            "Point(%d; %d) is located outside boundaries of maze",
            point.row(),
            point.col()
        );
    }
}
