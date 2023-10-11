package edu.hw1;

public class Task8 {

    private static final int AMOUNT_OF_ROWS = 8;
    private static final int AMOUNT_OF_COLUMNS = 8;
    private static final int MAX_OFFSET = 2;

    public static boolean knightBoardCapture(int[][] table) {
        if (table.length != AMOUNT_OF_ROWS || table[0].length != AMOUNT_OF_COLUMNS) {
            throw new IllegalArgumentException("The table should be 8x8 in size");
        }
        for (int row = 0; row < table.length; ++row) {
            for (int column = 0; column < table[0].length; ++column) {
                if (table[row][column] != 0 && table[row][column] != 1) {
                    throw new IllegalArgumentException("The numbers on the table must be equal to zero or one");
                }
                if (table[row][column] == 1) {
                    if (isHorseColliding(table, row, column)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isHorseColliding(int[][] table, int horseRow, int horseColumn) {
        int rows = table.length;
        int columns = table[0].length;
        for (int rowOffset = -MAX_OFFSET; rowOffset <= MAX_OFFSET; ++rowOffset) {
            for (int columnOffset = -MAX_OFFSET; columnOffset <= MAX_OFFSET; ++columnOffset) {
                if (Math.abs(rowOffset) == Math.abs(columnOffset) || rowOffset == 0 || columnOffset == 0) {
                    continue;
                }
                int currentRow = horseRow + rowOffset;
                int currentColumn = horseColumn + columnOffset;
                if (currentRow >= 0 && currentRow < rows && currentColumn >= 0 && currentColumn < columns) {
                    if (table[currentRow][currentColumn] == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Task8() {
    }
}
