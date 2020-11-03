package com.codecool.fiveinarow;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public class FiveInARow {

    public static void main(String[] args){

        Game game = new Game(10, 10);
        int[][] board = game.getBoard();
//        System.out.println(Arrays.deepToString(board));
        game.printBoard();

//        game.enableAi(1);
//        game.enableAi(2);
//        game.play(5);
    }
}
