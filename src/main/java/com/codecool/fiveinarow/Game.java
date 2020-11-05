package com.codecool.fiveinarow;

import java.util.*;

public class Game implements GameInterface {

    private int[][] board;

    public String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"};
    public String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};

    public Map<String, Integer> rows = new HashMap<>();
    public Map<String, Integer> cols = new HashMap<>();

    int[][] winningCoords;
    int[][] possibleMoves;

    public Game(int nRows, int nCols) {
        board = new int[nRows][nCols];
        setCoords(nRows, nCols);
    }

    public void setCoords(int nRows, int nCols) {
        for (int i = 0; i < nRows; i++) {
            rows.put(letters[i], i);
        }
        for (int i = 0; i < nCols; i++) {
            cols.put(numbers[i], i);
        }
    }

    public boolean isValidInput(String input) {
        try {
            String row = input.substring(0, 1).toLowerCase();
            String col = input.substring(1);
            return (rows.containsKey(row) && cols.containsKey(col));

        } catch (Exception e) {
            System.out.println("Invalid input!");
            return false;
        }
    }

    public int[] translateCoords(String input) {
        if (isValidInput(input)) {
            String row = input.substring(0, 1).toLowerCase();
            String col = input.substring(1);
            int coord1 = rows.get(row);
            int coord2 = cols.get(col);
            int[] coords = {coord1, coord2};
            return coords;
        } else {
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
        String currentPlayer;
        if (player == 1) {
            currentPlayer = "X";
        } else {
            currentPlayer = "O";
        }
        while (true) {
            Scanner userInput = new Scanner(System.in);
            System.out.println(currentPlayer + "'s turn. " + "Enter coordinates: ");
            inputCoords = userInput.nextLine();
            if (inputCoords.toLowerCase().equals("quit") || inputCoords.toLowerCase().equals("q")) {
                System.out.println("Good Bye!");
                System.exit(0);
            } else if (inputCoords.length() < 2 || inputCoords.length() > 3) {
                System.out.println("Invalid input!");
            } else {
                coords = translateCoords(inputCoords);
                if (coords != null) {
                    if (board[coords[0]][coords[1]] == 0) {
                        break;
                    } else {
                        System.out.println("Invalid input! This place is already taken!");
                    }
                } else {
                    System.out.println("Invalid input!");
                }
            }
        }
        return coords;
    }

    public int getOtherPlayer (int player){
        int otherPlayer;
        if (player == 1) {
            otherPlayer = 2;
        } else {
            otherPlayer = 1;
        }
        return otherPlayer;
    }

    public int[] getAiMove(int player) {
        int[] coords;
        int otherPlayer = getOtherPlayer(player);
        if (getStrategicMove(otherPlayer, 4) != null) {
            coords = getStrategicMove(otherPlayer, 4);
        } else if (getStrategicMove(otherPlayer, 3) != null) {
            coords = getStrategicMove(otherPlayer, 3);
        } else if (getStrategicMove(player, 4) != null) {
            coords = getStrategicMove(player, 4);
        } else if (getStrategicMove(player, 3) != null){
            coords = getStrategicMove(player, 3);
        } else if (getStrategicMove(player, 2) != null) {
            coords = getStrategicMove(player, 2);
        } else if (getStrategicMove(player, 1) != null) {
            coords = getStrategicMove(player, 1);
        } else {
            coords = getRandomMove();
        }
        return coords;
    }

    public int[] getRandomMove(){
        while (true) {
            int col = (int) ((Math.random() * (board.length - 1)) + 1);
            int row = (int) ((Math.random() * (board.length - 1)) + 1);
            if (board[row][col] == 0) {
                int[] coords = {row, col};
                return coords;
            } else if (isFull()) {
                return null;
            }
        }
    }

    public int[] getStrategicMove(int player, int howMany){
        int[][] playedCoords;
        possibleMoves = new int[0][0];

        if (isHorizontalWin(player, howMany)){
            playedCoords = winningCoords;
            int[] firstCoord = playedCoords[0];
            int[] lastCoord = playedCoords[howMany-1];
            int[] leftNeighbor = {firstCoord[0], (firstCoord[1]-1)};
            int[] rightNeighbor = {lastCoord[0], (lastCoord[1]+1)};
            savePossibleMoves(leftNeighbor);
            savePossibleMoves(rightNeighbor);
        }
        if (isVerticalWin(player, howMany)){
            playedCoords = winningCoords;
            int[] firstCoord = playedCoords[0];
            int[] lastCoord = playedCoords[howMany-1];
            int[] topNeighbor = {firstCoord[0]-1, (firstCoord[1])};
            int[] bottomNeighbor = {lastCoord[0]+1, (lastCoord[1])};
            savePossibleMoves(topNeighbor);
            savePossibleMoves(bottomNeighbor);
        }
        if (leftDiagonalWin(player, howMany)){
            playedCoords = winningCoords;
            int[] firstCoord = playedCoords[0];
            int[] lastCoord = playedCoords[howMany-1];
            int[] topRightNeighbor = {firstCoord[0]-1, (firstCoord[1]+1)};
            int[] bottomLeftNeighbor = {lastCoord[0]+1, (lastCoord[1]-1)};
            savePossibleMoves(topRightNeighbor);
            savePossibleMoves(bottomLeftNeighbor);
        }
        if (rightDiagonalWin(player, howMany)) {
            playedCoords = winningCoords;
            int[] firstCoord = playedCoords[0];
            int[] lastCoord = playedCoords[howMany-1];
            int[] topLeftNeighbor = {firstCoord[0]-1, (firstCoord[1]-1)};
            int[] bottomRightNeighbor = {lastCoord[0]+1, (lastCoord[1]+1)};
            savePossibleMoves(topLeftNeighbor);
            savePossibleMoves(bottomRightNeighbor);
        }
        System.out.println(Arrays.deepToString(possibleMoves));
        if (possibleMoves.length > 0) {
            int[] coords = getRandomCoord(possibleMoves);
            return coords;
        } else {
            return null;
        }
    }

    public void savePossibleMoves(int[] move){
        if (isInBoard(move) && (isNotTaken(move))) {
            int[][] newArray = new int[possibleMoves.length+1][2];
            for (int i = 0; i < possibleMoves.length; i++) {
                newArray[i] = possibleMoves[i];
            }
            newArray[possibleMoves.length][0] = move[0];
            newArray[possibleMoves.length][1] = move[1];
            possibleMoves = newArray;
        }
    }

    public int[] getRandomCoord(int[][] validCoords){
        int index = (int) (Math.random() * validCoords.length);
        return validCoords[index];
    }

    public boolean isInBoard(int[] coords) {
        return (coords[0] >= 0 && coords[0] < board.length) && (coords[1] >= 0 && coords[1] < board[0].length);
    }

    public boolean isNotTaken(int[] coords){
        return board[coords[0]][coords[1]] == 0;
    }


    public void mark(int player, int row, int col) {
        board[row][col] = player;
    }

    public boolean isHorizontalWin(int player, int howMany) {
        int count = 0;
        winningCoords = new int[howMany][2];
        int index = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == player && count < howMany) {
                    count++;
                    winningCoords[index][0] = i;
                    winningCoords[index][1] = j;
                    index++;
                    if (count >= howMany) {
                        return true;
                    }
                } else if (board[i][j] != player) {
                    count = 0;
                    winningCoords = new int[howMany][2];
                    index = 0;
                }
            }
        }
        return false;
    }

    public boolean isVerticalWin(int player, int howMany) {
        int count = 0;
        winningCoords = new int[howMany][2];
        int index = 0;
        for (int i = 0; i < board[0].length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[j][i] == player && count < howMany) {
                    count++;
                    winningCoords[index][0] = j;
                    winningCoords[index][1] = i;
                    index++;
                    if (count >= howMany) {
                        return true;
                    }
                } else if (board[j][i] != player) {
                    count = 0;
                    winningCoords = new int[howMany][2];
                    index = 0;
                }
            }
        }
        return false;
    }

    public boolean leftDiagonalWin(int player, int howMany){
        winningCoords = new int[howMany][2];
        int index = 0;
        int length = board.length;
        int diagonalLines = (length + length) - 1;
        int itemsInDiagonal = 0;
        int midPoint = (diagonalLines / 2) + 1;

        for (int i = 1; i <= diagonalLines; i++) {
            int rowIndex;
            int columnIndex;
            int count = 0;
            if (i <= midPoint) {
                itemsInDiagonal++;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (i - j) - 1;
                    columnIndex = j;

                    if (board[rowIndex][columnIndex] == player){
                        count++;
                        winningCoords[index][0] = rowIndex;
                        winningCoords[index][1] = columnIndex;
                        index++;
                        if (count >= howMany) {
                            return true;
                        }
                    } else {
                        count = 0;
                        winningCoords = new int[howMany][2];
                        index = 0;
                    }
                }
            } else {
                itemsInDiagonal--;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (length - 1) - j;
                    columnIndex = (i - length) + j;

                    if (board[rowIndex][columnIndex] == player){
                        count++;
                        winningCoords[index][0] = rowIndex;
                        winningCoords[index][1] = columnIndex;
                        index++;
                        if (count >= howMany) {
                            return true;
                        }
                    } else {
                        count = 0;
                        winningCoords = new int[howMany][2];
                        index = 0;
                    }
                }
            }
        }
        return false;
    }

    public boolean rightDiagonalWin(int player, int howMany){
        winningCoords = new int[howMany][2];
        int index = 0;
        int length = board.length;
        int diagonalLines = (length + length) - 1;
        int itemsInDiagonal = 0;
        int midPoint = (diagonalLines / 2) + 1;

        for (int i = 1; i <= diagonalLines; i++) {
            int rowIndex;
            int columnIndex;
            int count = 0;
            if (i <= midPoint) {
                itemsInDiagonal++;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = (length - i) + j;
                    columnIndex = j;

                    if (board[rowIndex][columnIndex] == player){
                        count++;
                        winningCoords[index][0] = rowIndex;
                        winningCoords[index][1] = columnIndex;
                        index++;
                        if (count >= howMany) {
                            return true;
                        }
                    } else {
                        count = 0;
                        winningCoords = new int[howMany][2];
                        index = 0;
                    }
                }
            } else {
                itemsInDiagonal--;
                for (int j = 0; j < itemsInDiagonal; j++) {
                    rowIndex = j;
                    columnIndex = (i - length) + j;

                    if (board[rowIndex][columnIndex] == player){
                        count++;
                        winningCoords[index][0] = rowIndex;
                        winningCoords[index][1] = columnIndex;
                        index++;
                        if (count >= howMany) {
                            return true;
                        }
                    } else {
                        count = 0;
                        winningCoords = new int[howMany][2];
                        index = 0;
                    }
                }
            }
        }
        return false;
    }

    public boolean hasWon(int player, int howMany) {
        return isHorizontalWin(player, howMany) || isVerticalWin(player, howMany) || leftDiagonalWin(player, howMany) || rightDiagonalWin(player, howMany);
    }

    public boolean isFull() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean gameOver(int player, int howMany) {
        if (hasWon(player, howMany)) {
            printResult(player);
            return true;
        } else if (isFull()) {
            System.out.println("It's a tie!");
            return true;
        }
        return false;
    }

    public void printBoard() {
        System.out.print(" " + " ");
        for (int k = 0; k < board[0].length; k++) {
            if (numbers[k].length() > 1) {
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
        if (player == 1) {
            System.out.println("X has won!");
        } else {
            System.out.println("O has won!");
        }
    }

    public void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            System.out.println("Something went wrong");
        }
    }


    public void enableAi(int player, int howMany) {
        if (player == 1) {
            while (true) {
                int[] coords1 = getAiMove(1);
                if (coords1 != null) {
                    mark(1, coords1[0], coords1[1]);
                    FiveInARow.clearScreen();
                    printBoard();
                    sleep(2);
                    if (gameOver(1, howMany)) {
                        break;
                    }
                } else {
                    System.out.println("It's a tie!");
                }
                int[] coords2 = getMove(2);
                mark(2, coords2[0], coords2[1]);
                FiveInARow.clearScreen();
                printBoard();
                sleep(2);
                if (gameOver(2, howMany)) {
                    break;
                }
            }

        } else if (player == 2) {
            while (true) {
                int[] coords1 = getMove(1);
                mark(1, coords1[0], coords1[1]);
                FiveInARow.clearScreen();
                printBoard();
                sleep(2);
                if (gameOver(1, howMany)) {
                    break;
                }

                int[] coords2 = getAiMove(2);
                if (coords2 != null) {
                    mark(2, coords2[0], coords2[1]);
                    FiveInARow.clearScreen();
                    printBoard();
                    sleep(2);
                    if (gameOver(2, howMany)) {
                        break;
                    }
                } else {
                    System.out.println("It's a tie!");
                }

            }
        }
    }


    public void play(int howMany) {
        while (true) {
            int[] coords1 = getMove(1);
            mark(1, coords1[0], coords1[1]);
            FiveInARow.clearScreen();
            printBoard();
            if (gameOver(1, howMany)) {
                break;
            }

            int[] coords2 = getMove(2);
            mark(2, coords2[0], coords2[1]);
            FiveInARow.clearScreen();
            printBoard();
            if (gameOver(2, howMany)) {
                break;
            }
        }
        System.out.println("Game over!");
    }
}
