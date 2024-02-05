package edu.project2;

 class MazeHelper {
    public static Maze convertToMaze(int[][] grid) {
        Maze maze = new Maze(grid.length, grid[0].length);
        for (int row = 0; row < maze.getHeight(); ++row) {
            for (int column = 0; column < maze.getWidth(); ++column) {
                maze.setPointType(row, column, convertIntToCellType(grid[row][column]));
            }
        }
        return maze;
    }

    private static CellType convertIntToCellType(int value) {
        return value == 0 ? CellType.PASSAGE : CellType.WALL;
    }
}
