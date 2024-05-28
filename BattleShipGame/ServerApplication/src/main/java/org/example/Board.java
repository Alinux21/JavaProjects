package org.example;

import java.util.Random;

public class Board {
    private final int SIZE = 10;
    private final String[][] grid;
    private final Random random = new Random();

    public Board() {
        grid = new String[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = "🌊"; // '🌊' represents water/empty space
            }
        }

        //place ships of size 2, 3, 4, and 5
        placeRandomShip(2, "Ship2");
        placeRandomShip(3, "Ship3");
        placeRandomShip(4, "Ship4");
        placeRandomShip(5, "Ship5");
    }

    private void placeRandomShip(int size, String shipType) {
        boolean placed = false;
        while (!placed) {
            int x = random.nextInt(SIZE);
            int y = random.nextInt(SIZE);
            boolean horizontal = random.nextBoolean();
            placed = placeShip(x, y, size, horizontal, shipType);
        }
    }

    public String[][] getGrid() {
        return grid;
    }

    private boolean placeShip(int x, int y, int size, boolean horizontal, String shipType) {
        //check if the ship goes off the board
        if (horizontal) {
            if (y + size > SIZE) {
                return false;
            }
        } else {
            if (x + size > SIZE) {
                return false;
            }
        }

        //check if the ship overlaps another ship
        for (int i = 0; i < size; i++) {
            if (horizontal) {
                if (!grid[x][y + i].equals("🌊")) {
                    return false;
                }
            } else {
                if (!grid[x + i][y].equals("🌊")) {
                    return false;
                }
            }
        }

        //place the ship
        for (int i = 0; i < size; i++) {
            if (horizontal) {
                grid[x][y + i] = shipType;
            } else {
                grid[x + i][y] = shipType;
            }
        }

        return true;
    }

    public String getMaskedBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append("    "); //space for pretty format

        //column labels
        for (int i = 0; i < SIZE; i++) {
            sb.append(" ");
            sb.append((char)('A' + i));
            sb.append("  ");
        }
        sb.append('\n');

        //row labels
        for (int i = 0; i < SIZE; i++) {

            sb.append(String.format("%2d  ", i + 1)); //row label


            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j].equals("❌")) {
                    sb.append("❌");
                } else if (grid[i][j].equals("💨")) {
                    sb.append("💨");
                } else if (grid[i][j].startsWith("Ship")) {
                    sb.append("🌊");
                } else {
                    sb.append(grid[i][j]);
                }
                sb.append("  ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("    "); //space for pretty format

        // Print column labels
        for (int i = 0; i < SIZE; i++) {
            sb.append(" ");
            sb.append((char)('A' + i));
            sb.append("  ");
        }
        sb.append('\n');

        for (int i = 0; i < SIZE; i++) {

            sb.append(String.format("%2d  ", i + 1));

            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j].startsWith("Ship")) {
                    sb.append("⛵");
                } else {
                    sb.append(grid[i][j]);
                }
                sb.append("  ");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}