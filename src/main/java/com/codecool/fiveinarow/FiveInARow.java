package com.codecool.fiveinarow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public class FiveInARow {

    private static int numRows;
    private static int numCols;

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int getBoardHeight(){
        while (true) {
            Scanner rowsInput = new Scanner(System.in);
            System.out.println("Enter number of rows (10-15): ");
            try {
                numRows = rowsInput.nextInt();
                if (numRows >= 10 && numRows <= 15) {
                    break;
                } else {
                    System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }
        return numRows;
    }

    public static int getBoardWidth(){
        while (true) {
            Scanner rowsInput = new Scanner(System.in);
            System.out.println("Enter number of columns (10-15): ");
            try {
                numCols = rowsInput.nextInt();
                if (numCols >= 10 && numRows <= 15) {
                    break;
                } else {
                    System.out.println("Invalid input!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }
        return numRows;
    }

    public static void main(String[] args){
        clearScreen();
        numRows = getBoardHeight();
        numCols = getBoardWidth();

        Game game = new Game(numRows, numCols);
        clearScreen();
        game.printBoard();

        game.play(5, "pvp");

//        game.enableAi(1);
//        game.enableAi(2);

    }
}
