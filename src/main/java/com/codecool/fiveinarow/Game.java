package com.codecool.fiveinarow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game implements GameInterface {

    private int[][] board;

    public String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
    public String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};

    public Map<String, Integer> rows = new HashMap<>();
    public Map<String, Integer> cols = new HashMap<>();

    public Game(int nRows, int nCols) {
        board = new int[nRows][nCols];
        setCoords();
    }

    public void setCoords(){
        for (int i = 0; i < letters.length; i++){
            rows.put(letters[i], i);
        }
        for (int i = 0; i < numbers.length; i++){
            cols.put(numbers[i], i);
        }
    }

    public boolean isValidInput(String input){
        try {
            String row = input.substring(0, 1).toLowerCase();
            String col = input.substring(1);
            return (rows.containsKey(row) && cols.containsKey(col));

        } catch (Exception e) {
            System.out.println("Invalid input!");
            return false;
        }
    }

    public int[] translateCoords(String input){
        if (isValidInput(input)){
            String row = input.substring(0, 1).toLowerCase();
            String col = input.substring(1);
            int coord1 = rows.get(row);
            int coord2 = cols.get(col);
            int[] coords = {coord1, coord2};
            return coords;
        } else{
            return null;
        }
    }


    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int[] getMove(int player) {
        String inputCoords;
        int[] coords;
        while (true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter coordinates: ");
            inputCoords = userInput.nextLine();
            if (inputCoords.length() < 2 || inputCoords.length() > 3){
                System.out.println("Invalid input!");
            } else {
                coords = translateCoords(inputCoords);
                if (coords != null){
                    if (board[coords[0]][coords[1]] == 0){
                        break;
                    } else {
                        System.out.println("Invalid input! This place is already taken!");
                    }
                } else {
                    System.out.println("Invalid input!");
                }
            }
        }
        System.out.println(Arrays.toString(coords));
        return coords;
    }

    public int[] getAiMove(int player) {
        return null;
    }

    public void mark(int player, int row, int col) {
        board[row][col] = player;
    }

    public boolean hasWon(int player, int howMany) {
        return false;
    }

    public boolean isFull() {
        return false;
    }

    public void printBoard() {
        System.out.print(" " + " ");
        for (int k = 0; k < board[0].length; k++) {
            if (numbers[k].length()>1){
                System.out.print(numbers[k] + " ");
            } else {
                System.out.print(numbers[k] + " " + " ");
            }
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(letters[i].toUpperCase() + " ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print((board[i][j] == 0 ? "." : board[i][j] == 1 ? "X" : "O") + " " + " ");
            }
            System.out.println();
        }
    }

    public void printResult(int player) {
    }

    public void enableAi(int player) {
    }

    public void play(int howMany) {
        int i = 0;
        while (i < 5) {
            int[] coords1 = getMove(1);
            mark(1, coords1[0], coords1[1]);
            // clear
            printBoard();
            // check win, check full, break if game over

            int[] coords2 = getMove(2);
            mark(2, coords2[0], coords2[1]);
            // clear
            printBoard();
            // check win, check full, break if game over
            i++;
        }

        // print result
    }
}
