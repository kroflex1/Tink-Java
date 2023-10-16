package edu.hw1;

public class Task8 {

    private static final int AMOUNT_OF_ROWS = 8;
    private static final int AMOUNT_OF_COLUMNS = 8;
    private static final int MAX_OFFSET = 2;
    private static final String ERROR_SIZE_MESSAGE =
        String.format("The table should be %dx%d in size", AMOUNT_OF_ROWS, AMOUNT_OF_COLUMNS);

    public static boolean knightBoardCapture(int[][] table) {
        checkTable(table);
        for (int row = 0; row < table.length; ++row) {
            for (int column = 0; column < table[0].length; ++column) {
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
        for (int rowOffset = -MAX_OFFSET; rowOffset <= MAX_OFFSET; ++rowOffset) {
            for (int columnOffset = -MAX_OFFSET; columnOffset <= MAX_OFFSET; ++columnOffset) {
                if (Math.abs(rowOffset) == Math.abs(columnOffset) || rowOffset == 0 || columnOffset == 0) {
                    continue;
                }
                int currentRow = horseRow + rowOffset;
                int currentColumn = horseColumn + columnOffset;
                if (currentRow >= 0 && currentRow < AMOUNT_OF_ROWS && currentColumn >= 0
                    && currentColumn < AMOUNT_OF_COLUMNS) {
                    if (table[currentRow][currentColumn] == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void checkTable(int[][] table) {
        if (table.length != AMOUNT_OF_ROWS) {
            throw new IllegalArgumentException(ERROR_SIZE_MESSAGE);
        }
        for (int row = 0; row < AMOUNT_OF_ROWS; ++row) {
            if (table[row].length != AMOUNT_OF_COLUMNS) {
                throw new IllegalArgumentException(ERROR_SIZE_MESSAGE);
            }
            for (int column = 0; column < AMOUNT_OF_COLUMNS; ++column) {
                if (table[row][column] != 1 && table[row][column] != 0) {
                    throw new IllegalArgumentException("The numbers on the table must be equal to zero or one");
                }
            }

        }
    }

    private Task8() {
    }
}
