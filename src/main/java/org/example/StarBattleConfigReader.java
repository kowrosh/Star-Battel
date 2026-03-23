package org.example;
import tools.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Scanner;

public class StarBattleConfigReader {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // Read JSON file into BoardConfig
            BoardConfig config = mapper.readValue(new File("src/main/resources/config_starbattle.json"), BoardConfig.class);

            // Create a 10x10 board initialized with -1
            int[][] board = new int[10][10];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    board[i][j] = -1;
                }
            }

            // Populate the board
            for (Cell cell : config.board) {
                if (cell.r >= 0 && cell.r < 10 && cell.c >= 0 && cell.c < 10) {
                    board[cell.r][cell.c] = cell.f;
                } else {
                    System.out.printf("Warning: Invalid coordinates (r=%d, c=%d)%n", cell.r, cell.c);
                }
            }
            // Create a new StarBattleGame using the given board layout
            StarBattleGame game = new StarBattleGame(board);
            Scanner sc = new Scanner(System.in);
            game.solve();





// Print final board
            //game.printBoard();

//            System.out.println("Welcome to Star Battle!");
//            System.out.println("Enter row and column to place a star (use -1 to quit).");


            // Main game loop – runs until the user decides to quit
//            while(true) {
//                game.printBoard();
//                System.out.print("Row (1–10 or -1 to exit): ");
//                int r = sc.nextInt();
//                // If user enters -1, exit the game loop
//                if(r == -1){
//                    break;
//                }
//                System.out.print("Column (1–10): ");
//                int c = sc.nextInt();
//                // Adjust input so the board starts from 1 instead of 0
//                r -= 1;
//                c -= 1;
//                // Attempt to place a star at the given coordinates
//                game.placeStar(r, c);
//            }
//            if (game.isSolved()) {
//                System.out.println("Star Battle has been solved!");
//            }
//            else {
//                System.out.println("Star Battle has been not solved!");
//            }
//            System.out.println("goodbye");

            // Print the board
//            for (int r = 0; r < 10; r++) {
//                for (int c = 0; c < 10; c++) {
//                    System.out.printf("%2d ", board[r][c]);
//                }
//                System.out.println();
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
