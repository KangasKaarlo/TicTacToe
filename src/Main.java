package src;

import src.util.*;
/**
 * A single player tictactoe that is played against an Ai opponent.
 * 
 * @author Kaarlo Kangas
 * @version 2020.1214
 * @since 15.0.1
 */
public class Main {
    static char [][] board;

    //characters for board icons
    static char player = 'X';
    static char ai = 'O';
    static char empty = '-';

    //booleans that keep the game running
    static boolean playerWon = false;
    static boolean aiWon = false;
    static boolean boardFull = false;
    static boolean playerMadeTheLastTurn = false;

    //settings asked from the player
    static int howManyToWin;
    static int aiDifficulty;
    
    /**
     * 
     * @param args
     */
    public static void main(String [] args) {
        
        initializeGame();
        
        while (!(playerWon || aiWon || boardFull)) {
            if (playerMadeTheLastTurn) {
                aiTurn();
            }
            else {
                playerTurn();
            }
            Ui.printBoard(board); 
        }
    }
    /**
     * Initializes the game by asking setting from the players, creating the board and filling it with char empty (short)
     */
    public static void initializeGame() {
        System.out.println("Welcome to my game of TicTacToe.");
        System.out.println("You'll be playing against an ai.");
        //asks for the size of the gameboard
        System.out.println("Please give the size of the board (min 3 max 15):");

        //Initializes the board and sets the correct win condition.
        int boardSize = MyConsole.readInt(3, 15);
        board = new char [boardSize][boardSize];
        
        if (boardSize >= 10) {
            System.out.println("How many in a row do you need for a victory?(min 5)");
            howManyToWin = MyConsole.readInt(5, boardSize);
        }
        else {
            System.out.println("How many in a row do you need for a victory?(min 3):");
            howManyToWin = MyConsole.readInt(3, boardSize);
        }

        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                board[y][x] = empty;
            }
        }

        //Asks the player for a difficulty setting.
        System.out.println("Please select difficulty between 1 and 5:");
        if (boardSize > 7) {
            System.out.println("please note that on larger boards the more difficult ai will take longer to make its move.");
            if (boardSize > 10) {
                System.out.println("like waaaaaay longer.");
            }
            System.out.println("So it's recommended to play on a board less than 7 if you're planning to tackle the more challenging difficulties.");
        }
        aiDifficulty = MyConsole.readInt(1,5) - 1;
        Ui.printBoard(board);
    }
    /**
     * Checks if the game should continue to run
     */
    public static void checkWin() {
        int outcome = Algorithms.winChecker(board, howManyToWin, player, ai, empty);
        if (outcome == -1000) {
            playerWon = true;
            System.out.println("You won!");
        }
        else if (outcome == 1000) {
            aiWon = true;
            System.out.println("You lost!");
        }
        else if (outcome == 0) {
            boardFull = true;
            System.out.println("Tie!");
        }
    }
    /**
     * Asks player for their move, places it on the board and checks if the game is at an end state (short)
     */
    public static void playerTurn() {
        int y;
        int x;
        System.out.println("Your turn!");
        do {
            System.out.println("column:");
            x = MyConsole.readInt(1, board.length) -1;
            System.out.println("row:");
            y = MyConsole.readInt(1, board.length) -1;    
            if (!(board[y][x] == empty)) {
                System.out.println("That spot is not free. Please try again:");
            }
        } while (!(board[y][x] == empty));
        
        board [y][x] = player;
        checkWin();
        playerMadeTheLastTurn = true;
    }
    /**
     * Ai  decides if it should make its turn using the minmax algorithm (aiMovieSmart()) or make a completely random move (aiMoveDumb()) (short)
     * The choice is based on the difficulty given by the player at the initializeGame method.
     * Method calculates random number between 2-5 and if the number is equal or smaller than aiDifficulty, aiMoveSmart is used. 
     * Therefore the chance for the aiMoveSmart is 0% in difficulty 1 and increases by 25% each difficulty ending to 100% in difficulty 5 (long)
     */
    public static void aiTurn() {
        if (aiDifficulty >= (int)(Math.random() *  4) + 1) {
            aiMoveSmart();
        } else {
          aiMoveDumb();  
        } 
        playerMadeTheLastTurn = false;
    }
    /**
     * Calls minmax on every possible move to determine their likely outcome and places the ai move to the best score found (short)
     */
    public static void aiMoveSmart() {
        int score;
        int bestScore = -10000;
        int alpha = -10000;
        int beta = 10000;
        int [] bestMove = new int [2];

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                if (board[y][x] == empty) {
                    board[y][x] = ai;
                    score = Algorithms.minmax(board, player, ai, empty, 0, false, howManyToWin, alpha, beta);
                    board[y][x] = empty;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = y;
                        bestMove[1] = x;
                    }
                }
            }
        }
        board[bestMove[0]][bestMove[1]] = ai;
        checkWin();
    }
    /**
     * Randomly picks an empty position from the board  and places ai char in it
     */
    public static void aiMoveDumb() {
        //Checks how many possible moves there is.
        int emptyCounter = 0;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                if (board[y][x] == empty) {
                    emptyCounter++;
                }
            }
        }
        
        //Selects randomly where to put the move.
        int aiMove = (int)(Math.random() * emptyCounter + 1);
        
        //places the move.
        emptyCounter = 0;
        out:
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                if (board[y][x] == empty) {
                    emptyCounter++;
                    if (emptyCounter == aiMove) {
                        board[y][x] = ai;
                        break out;
                    }
                }
            }
        }
        checkWin();
    }
}