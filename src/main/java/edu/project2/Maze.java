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

    public List<Coordinate> getNeighboringPoints(Coordinate point) {
        List<Coordinate> points = new ArrayList<>();
        for (int rowOffset = -2; rowOffset <= 2; rowOffset += 2) {
            for (int columnOffset = -2; columnOffset <= 2; columnOffset += 2) {
                Coordinate currentPoint = new Coordinate(point.row() + rowOffset, point.col() + columnOffset);
                if (Math.abs(rowOffset) != Math.abs(columnOffset) && isPointInBoundary(currentPoint)) {
                    points.add(currentPoint);
                }
            }
        }
        return points;
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
}
