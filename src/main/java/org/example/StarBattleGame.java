package org.example;

public class StarBattleGame {

    private int[][] regions; // 2D array representing the regions of the board
    private boolean[][] stars; // 2D array tracking where stars are placed (true = star)
    private int k = 2; // Number of stars per row/column/region

    //Constructor: Initializes a new Star Battle game with the given region layout.
    public StarBattleGame(int[][] regions) {
        this.regions = regions;
        this.stars = new boolean[regions.length][regions[0].length];
    }
    //Checks whether a given position (row, col) is within the boundaries of the board.
    private boolean isBound(int row, int col) {
        return row >= 0 && row < regions.length && col >= 0 && col < regions[0].length;
    }
    //Attempts to place a star at the specified position.
    //Validates that the position is within bounds, not already occupied,
    //and not adjacent (including diagonals) to another star.
    public boolean placeStar(int row, int col) {
        if (!isBound(row, col)) {
            System.out.println("out of Bounds");
            return false;
        }
        if (stars[row][col]) {
            System.out.println("we have this");
            return false;
        }
        //Check surrounding 8 cells for adjacent stars
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = row + dr, nc = col + dc;
                if (isBound(nr, nc) && stars[nr][nc]) {
                    System.out.println("Invalid: Adjacent to another star");
                    return false;
                }
            }
        }
        // Place star if valid
        stars[row][col] = true;
        System.out.println("✅ Valid move: Star placed at (" + (row + 1) + "," + (col + 1) + ")");
        return true;
    }

    // Check if the puzzle is completely solved
    public boolean isSolved() {
        // 1. Check each row
        for (int row =0; row < regions.length; row++) {
            int count = 0;
            for (int col =0; col < regions[0].length; col++) {
                if (stars[row][col]){
                    count++;
                }
            }
            if (count !=k){
                System.out.println("Row " + row + " has " + count + " stars (should be " + k + ")");
                return false;
            }
        }
        // 2. Check each column
        for (int col =0; col < regions[0].length; col++) {
            int count = 0;
            for (int row =0; row < regions.length; row++) {
                if (stars[row][col]){
                    count++;
                }
            }
            if (count !=k){
                System.out.println(" Column " + col + " has " + count + " stars (should be " + k + ")");
                return false;
            }
        }
        // 3. Check each region
        // Find the maximum region ID
        int maxRegion = 0;
        for (int row =0; row < regions.length; row++) {
            for (int col =0; col < regions[0].length; col++) {
                if(maxRegion < regions[row][col]){
                    maxRegion = regions[row][col];
                }
            }
        }
        // Check stars count in each region
        for (int region=0; region <= maxRegion; region++) {
            int count = 0;
            for (int row =0; row < regions.length; row++) {
                for (int col =0; col < regions[0].length; col++) {
                    if(regions[row][col] == region &&  stars[row][col]){
                        count++;
                    }
                }
            }
            if(count !=k){
                System.out.println(" Region " + region + " has " + count + " stars (should be " + k + ")");
                return false;
            }
        }
        // All rules passed
        return true;
    }

    /**
     * Prints the current state of the game board to the console.
     * Stars are represented by '*' and empty cells by '.'.
     */
    public void printBoard() {
        for(int row = 0; row < regions.length; row++) {
            for(int col = 0; col < regions[0].length; col++) {
                System.out.print(stars[row][col] ? "*"+ "    " : regions[row][col] + "    ");
            }
            System.out.println();
        }
    }


    // Checks if placing a star here is valid WITHOUT modifying the board
    private boolean canPlaceStar(int row, int col) {
        if (!isBound(row, col)) return false;
        if (stars[row][col]) return false;

        // Check adjacent cells (existing code)
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int nr = row + dr, nc = col + dc;
                if (isBound(nr, nc) && stars[nr][nc]) return false;
            }
        }

        // -------- Check row --------
        int countRow = 0;
        for (int c = 0; c < regions[0].length; c++) {
            if (stars[row][c]) countRow++;
        }
        if (countRow >= k) return false;

        // -------- Check column --------
        int countCol = 0;
        for (int r = 0; r < regions.length; r++) {
            if (stars[r][col]) countCol++;
        }
        if (countCol >= k) return false;

        // -------- Check region --------
        int regionId = regions[row][col];
        int countRegion = 0;
        for (int r = 0; r < regions.length; r++) {
            for (int c = 0; c < regions[0].length; c++) {
                if (regions[r][c] == regionId && stars[r][c]) countRegion++;
            }
        }
        if (countRegion >= k) return false;

        return true;
    }


    // Solve the board row by row
    private boolean solveRow(int row) {
        if (row == regions.length) { // all rows processed
            return isSolved(); // check if the puzzle is solved
        }

        return tryStarsInRow(row, 0, 0);
    }

    // Try placing k stars in the given row
    private boolean tryStarsInRow(int row, int colStart, int starsPlaced) {
        if (starsPlaced == k) { // placed all k stars in this row
            return solveRow(row + 1); // move to the next row
        }

        for (int col = colStart; col < regions[0].length; col++) {
            if (canPlaceStar(row, col)) { // check if we can place a star here
                stars[row][col] = true; // place star
                if (tryStarsInRow(row, col + 1, starsPlaced + 1)) {
                    return true; // solution found
                }
                stars[row][col] = false; // undo / backtrack
            }
        }

        return false; // no valid placement found in this configuration
    }

    // Main solver
    public void solve() {
        System.out.println("🔍 Starting optimized solving for k>1...\n");
        if (solveRow(0)) {
            System.out.println("🎉 Puzzle solved!");
        } else {
            System.out.println("❌ No solution found.");
        }
        printBoard();
    }



}





