package nl.sogyo.javaopdrachten;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Position {
    int rowNumber;
    int colNumber;

    public int getRowNumber() {
        return this.rowNumber;
    }

    public int getColNumber() {
        return this.colNumber;
    }

    public void setRowNumber(int rowNum) {
        this.rowNumber = rowNum;
    }

    public void setColNumber(int colNum) {
        this.colNumber = colNum;
    }
}

public class Sudoku {

    public static int bordSize = 9;

    public static int[][] makeBord(Scanner sc) {
        int bord[][] = new int[bordSize][bordSize];
        for (int row = 0; row < bordSize; row++) {
            for (int col = 0; col < bordSize; col++) {
                bord[row][col] = Integer.parseInt(sc.next());
            }
        }
        sc.close();
        return bord;
    }

    // Determines in which block the row/column is: 1:(1-3), 2:(4-6) or 3:(7-9)
    public static int inWhichBlock(int rowOrCol) {
        int block = 0;
        if (rowOrCol < 6) {
            if (rowOrCol < 3) {
                block = 1;
            } else {
                block = 2;
            }
        } else {
            block = 3;
        }
        return block;
    }

    public static boolean numberIsNotInBlock(int[][] bord, int row, int col, int sudokuNumber) {
        int rowBlock = inWhichBlock(row);
        int colBlock = inWhichBlock(col);
        for (int i = ((rowBlock - 1) * 3); i < (rowBlock * 3); i++) {
            for (int j = ((colBlock - 1) * 3); j < (colBlock * 3); j++) {
                if (bord[i][j] == sudokuNumber) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean numberIsNotInRow(int[][] bord, int row, int col, int sudokuNumber) {
        for (int j = 0; j < bordSize; j++) {
            if (bord[row][j] == sudokuNumber) {
                return false;
            }
        }
        return true;
    }

    public static boolean numberIsNotInCol(int[][] bord, int row, int col, int sudokuNumber) {
        for (int i = 0; i < bordSize; i++) {
            if (bord[i][col] == sudokuNumber) {
                return false;
            }
        }
        return true;
    }

    public static boolean numberValidInPos(int[][] bord, int row, int col, int sudokuNumber) {
        return (numberIsNotInBlock(bord, row, col, sudokuNumber) && numberIsNotInRow(bord, row, col, sudokuNumber) && numberIsNotInCol(bord, row, col, sudokuNumber));
    }

    public static boolean posIsEmpty(int[][] bord, int row, int col) {
        return bord[row][col] == 0;
    }

    public static Position findNextEmptyPos(int[][] bord) {
        Position pos = new Position();
        for (int i = 0; i < bordSize; i++) {
            for (int j = 0; j < bordSize; j++) {
                if (posIsEmpty(bord, i, j)) {
                    pos.setRowNumber(i);
                    pos.setColNumber(j);
                    return pos;
                }
            }
        }
        return null;
    }

    public static boolean solveSudoku(int[][] bord) {
        Position pos = findNextEmptyPos(bord);
        if (pos == null) {
            return true;
        }
        for (int sudokuNumber = 1; sudokuNumber < bordSize + 1; sudokuNumber++) {
            if (numberValidInPos(bord, pos.getRowNumber(), pos.getColNumber(), sudokuNumber)) {
                bord[pos.getRowNumber()][pos.getColNumber()] = sudokuNumber;
                if (solveSudoku(bord)) {
                    return true;
                } else {
                    bord[pos.getRowNumber()][pos.getColNumber()] = 0;
                }
            }
        }
        return false;
    }

    public static void printBord(int[][] bord) {
        System.out.print("\n");
        for (int row = 0; row < bordSize; row++) {
            System.out.print(" ");
            for (int col = 0; col < bordSize; col++) {
                if (col == 3 || col == 6) {
                    System.out.print(" |");
                }
                if (bord[row][col] == 0) {
                    System.out.print("  ");
                } else {
                    System.out.print(" " + bord[row][col]);
                }
            }
            System.out.print("\n");
            if (row == 2 || row == 5) {
                System.out.print("  ---------------------\n");
            }
        }
        System.out.print("\n");
    }

    public static void main(String[] args) throws FileNotFoundException {

        long startTime = System.nanoTime();

        File file = new File("src/main/java/nl/sogyo/javaopdrachten/sudokuOpdr.txt");
        Scanner sc = new Scanner(file);
        int[][] bord = makeBord(sc);
        printBord(bord);
        if (solveSudoku(bord)) {
            System.out.println("Solved:");
            printBord(bord);
        } else {
            System.out.println("No solution...");
        }

        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;

        System.out.println("Execution time in nanoseconds: " + timeElapsed);
        System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);
    }
}
