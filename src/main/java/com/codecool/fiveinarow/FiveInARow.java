package com.codecool.fiveinarow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public class FiveInARow {

    private static int numRows;
    private static int numCols;

    public static int getBoardHeight(){
        while (true) {
            Scanner rowsInput = new Scanner(System.in);
            System.out.println("Enter number of rows (10-15): ");
            try {
                numRows = rowsInput.nextInt();
                if (numRows >= 10 && numRows <= 15) {
                    break;
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
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }
        return numRows;
    }

    public static void main(String[] args){

        numRows = getBoardHeight();
        numCols = getBoardWidth();

        Game game = new Game(numRows, numCols);
        game.printBoard();

//        game.enableAi(1);
//        game.enableAi(2);
//        game.play(5);
    }
}
