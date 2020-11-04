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

        numRows = getBoardHeight();
        numCols = getBoardWidth();
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please choose game mode:");
        System.out.println("1: 2 player mode ");
        System.out.println("2: AI-player mode ");
        System.out.println("3: player-AI mode");
        String input = userInput.nextLine();

        Game game = new Game(numRows, numCols);
        if (input.equals("1")){
                    game.play(5);
        }else if(input.equals("2")){
                    game.enableAi(1, 5);
        }else if(input.equals("3")){
            game.enableAi(2, 5);
        }
    }
}
