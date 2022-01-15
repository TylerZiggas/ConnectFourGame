package edu.umsl;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConnectFour {
    public static void main(String[] args) {

        String currentPlayer = "RED";
        boolean retry = false;
        boolean turn = false;
        boolean winner = false;
        int[] fullColumnCheck = new int[7];
        int[][] connectFourBoard = new int[6][7]; // initialize board
        int column = 0;

        do {
            do {
                do {
                    try {
                        retry = false;
                        Scanner input = new Scanner(System.in);
                        System.out.println("\n" + currentPlayer + ", please input what column you would like to drop your piece (0-6): ");
                        column = input.nextInt();
                        if (column <= 6 && column >= 0) {
                            retry = fullColumn(fullColumnCheck, column); // Checking for if the column is full
                        }
                    } catch (InputMismatchException ex) {
                        System.out.println("That is not an integer.");
                        retry = true;
                    }
                }while (retry); // Makes sure that a number was input
                if (column < 0 || column > 6) {
                    System.out.println("That is not within the necessary parameters of 0 through 6.");
                }
            } while (column < 0 || column > 6); // Makes sure the input was valid

            setBoard(connectFourBoard, turn, column); // Setting up the board
            printBoard(connectFourBoard); // Printing the board
            winner = isWinner(connectFourBoard); // Checking if there was a winner

            if (winner) { // We don't want to change the player so break immediately
                break;
            }
            if (!turn) { // If 0, then player 1 just went, switch to player 2
               currentPlayer = "YELLOW";
               turn = true;
            } else { // If 1, then player 2 just went, switch to player 1
               currentPlayer = "RED";
               turn = false;
            }

        } while(!winner); // Infinite loop until winner is found
        System.out.println("\nThe winner is " + currentPlayer + "!");
    }

    public static boolean fullColumn(int[] fullColumnCheck, int column) {
        fullColumnCheck[column]++; // Add 1 to column, keep track of how many pieces have been dropped per column
            if (fullColumnCheck[column] >= 7) { // Column is full
                System.out.println("That column is full, please choose another column to drop your piece in.");
                return true;
            }
        return false;
    }

    public static void setBoard(int[][] connectFourBoard, boolean turn, int column) {
        for (int rowCounter = 5; rowCounter > -1; rowCounter--){ // Placing the piece as low as it can go
            if (connectFourBoard[rowCounter][column] == 0) { // If it is empty
                if (!turn){
                    connectFourBoard[rowCounter][column] = 1;
                } else {
                    connectFourBoard[rowCounter][column] = 2;
                }
                break;
            }
        }
    }

    public static void printBoard(int[][] connectFourBoard) {
        final String RED = "\u001B[31m";
        final String YELLOW = "\u001B[33m";
        final String RESETCOLOR = "\u001B[0m";

        System.out.println("  0    1    2    3    4    5    6");

        for (int rowCounter = 0; rowCounter < 6; rowCounter++) { // Prints the board for the game
            for (int columnCounter = 0; columnCounter < 7; columnCounter++) {
               if (connectFourBoard[rowCounter][columnCounter] == 0) { // Prints empty since no piece is in this yet
                    System.out.print("|   |");
               } else if (connectFourBoard[rowCounter][columnCounter] == 1){ // When it is 1 it prints R since it was Red's piece
                    System.out.print("| "+ RED + "R" + RESETCOLOR + " |");
               } else { // When it is 2 it prints Y since it was the Yellow's piece
                   System.out.print("| "+ YELLOW + "Y" + RESETCOLOR +" |");
               }
            }
            System.out.print("\n");
        }
    }

    public static boolean isWinner(int[][] connectFourBoard) { // Checks for the win conditions
        int redWinCounter = 0;
        int yellowWinCounter = 0;

        for (int rowCheck = 5; rowCheck > -1; rowCheck--) { // Check for a winner horizontally
            for (int horizontalCheck = 0; horizontalCheck < 7; horizontalCheck++) {
                if (connectFourBoard[rowCheck][horizontalCheck] == 1) { // Counter if player 1 might win horizontally
                    redWinCounter += 1;
                    yellowWinCounter = 0;
                    if (redWinCounter == 4) {
                        return true;
                    }
                } else if (connectFourBoard[rowCheck][horizontalCheck] == 2) { // Counter if player 2 might win horizontally
                    yellowWinCounter += 1;
                    redWinCounter = 0;
                    if (yellowWinCounter == 4) {
                        return true;
                    }
                } else {
                    yellowWinCounter = 0;
                    redWinCounter = 0;
                }
            }
        }
        for (int columnCheck = 6; columnCheck > -1; columnCheck--) { // Check for a winner vertically
            for (int verticalCheck = 5; verticalCheck > -1; verticalCheck--) {
                if (connectFourBoard[verticalCheck][columnCheck] == 1) { // Counter if player 1 might win vertically
                    redWinCounter += 1;
                    yellowWinCounter = 0;
                    if (redWinCounter == 4) {
                        return true;
                    }
                } else if (connectFourBoard[verticalCheck][columnCheck] == 2) { // Counter if player 2 might win vertically
                    yellowWinCounter += 1;
                    redWinCounter = 0;
                    if (yellowWinCounter == 4) {
                        return true;
                    }
                } else {
                    yellowWinCounter = 0;
                    redWinCounter = 0;
                }
            }
        }
        for (int diagonalRow = 5; diagonalRow > 2; diagonalRow--) { // Check for a winner diagonally
            for (int diagonalColumn = 0; diagonalColumn < 4; diagonalColumn++) {
                if (connectFourBoard[diagonalRow][diagonalColumn] == 1) {
                    if (connectFourBoard[diagonalRow - 1][diagonalColumn + 1] == 1 && connectFourBoard[diagonalRow - 2][diagonalColumn + 2] == 1 && connectFourBoard[diagonalRow - 3][diagonalColumn + 3] == 1) {
                        return true;
                    }
                }
                if (connectFourBoard[diagonalRow][diagonalColumn] == 2) {
                    if (connectFourBoard[diagonalRow - 1][diagonalColumn + 1] == 2 && connectFourBoard[diagonalRow - 2][diagonalColumn + 2] == 2 && connectFourBoard[diagonalRow - 3][diagonalColumn + 3] == 2) {
                        return true;
                    }
                }
            }
        }
        for (int reverseRow = 2; reverseRow > -1; reverseRow--) {  //Check for a winner diagonally but in reverse
            for (int reverseColumn = 3; reverseColumn > -1; reverseColumn--) {
                if (connectFourBoard[reverseRow][reverseColumn] == 1) {
                   if (connectFourBoard[reverseRow + 1][reverseColumn + 1] == 1 && connectFourBoard[reverseRow + 2][reverseColumn + 2] == 1 && connectFourBoard[reverseRow + 3][reverseColumn + 3] == 1) {
                        return true;
                    }
                }
                if (connectFourBoard[reverseColumn][reverseColumn] == 2) {
                     if (connectFourBoard[reverseRow + 1][reverseColumn + 1] == 2 && connectFourBoard[reverseRow + 2][reverseColumn + 2] == 2 && connectFourBoard[reverseRow + 3][reverseColumn + 3] == 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}