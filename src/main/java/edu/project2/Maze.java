package edu.project2;

public class Maze {
    private final int height;
    private final int width;
    private CellType[][] grid;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new CellType[height][width];
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
