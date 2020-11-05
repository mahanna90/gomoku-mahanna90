package com.codecool.fiveinarow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public class FiveInARow {

    private static int numRows;
    private static int numCols;
    private static String gameMode;

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

    public static String selectGameMode(){
        String input;
        while (true){
            Scanner userInput = new Scanner(System.in);
            System.out.println("Please choose game mode:");
            System.out.println("1: 2 player mode ");
            System.out.println("2: AI-player mode ");
            System.out.println("3: player-AI mode");
            try{
                input = userInput.nextLine();
                if (input.equals("1") || input.equals("2") || input.equals("3")){
                    break;

                }else{
                    System.out.println("Invalid input!");
                }
            }
            catch (Exception e){
                System.out.println("Invalid input!");
            }
        }
        return input;
    }

    public static void main(String[] args){
        clearScreen();
        gameMode = selectGameMode();
        clearScreen();
        numRows = getBoardHeight();
        numCols = getBoardWidth();

        Game game = new Game(numRows, numCols);
        clearScreen();
        game.printBoard();

        if (gameMode.equals("1")){
            game.play(5);
        }else if(gameMode.equals("2")){
            game.enableAi(1, 5);
        }else if(gameMode.equals("3")){
            game.enableAi(2, 5);
        }
    }
}
