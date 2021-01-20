package src.util;
/**
 * This class includes algorithms usable in a game of tictactoe (short)
 */
public class Algorithms {
    /**
     * minmax algorithm that works as the games ai by finding the most optimal move(short)
     * minmax calls itself recursively on every single possible move and returns the value of the worst move possible in that branch
     * so that the move that leads to the best possible outcome can be chosen(long)
     * @param board is the board of the game
     * @param player char indicating the player on the board
     * @param ai char indicating the ai on the board
     * @param empty char indicating an empty space on the board
     * @param depth how many recursions into the minmax we are
     * @param isMaximizing indicates if it's the maximizing players turn or not
     * @param howManyToWin how many in a row is required for a victory
     * @param alpha alpha value for the alpha-beta pruning algorithm
     * @param beta beta value for the alpha-beta pruning algorithm
     * @return value of the best move found in this branch of the minmax tree
     */
    public static int minmax(char [][] board, char player, char ai, char empty, int depth, boolean isMaximizing, int howManyToWin, int alpha, int beta) {
        //settings
        int maxDepth = 5;
        
        //gets a score for the current state of the board
        int score = winChecker(board, howManyToWin, player, ai, empty);
        
        //checks for terminal states or maxDepth
        if (score == -1000 || score == 1000 || score == 0 ||  depth == maxDepth) {
            return score;
        }

        //checks if it is maximizing or minimizing players turn
        if (isMaximizing) {
            int bestScore = -10000;
            outMax:
            //goes through the board
            for (int y = 0; y < board.length; y++) {
                for (int x = 0; x < board.length; x++) {
                    //places a move on every empty place one at the time
                    if (board[y][x] == empty) {
                        board[y][x] = ai;
                        //sends that move to minmax
                        score = minmax(board, player, ai, empty, depth +1, false, howManyToWin, alpha, beta);
                        //resets the move
                        board[y][x] = empty;
                        //tests the move leads to a better outcome than any move previously tested
                        if (score > bestScore) {
                            bestScore = score;   
                        }
                        //alpha-beta pruning for not so horrible performance
                        if (alpha < bestScore) {
                            alpha = bestScore;
                        }   
                        if (beta <= alpha) {
                            break outMax;
                        }
                    }
                }
            }
            return bestScore;
        }
        //same for minimizing player
        else {
            int bestScore = 10000;
            outMin:
            for (int y = 0; y < board.length; y++) {
                for (int x = 0; x < board.length; x++) {
                    if (board[y][x] == empty) {
                        board[y][x] = player;
                        score = minmax(board, player, ai, empty, depth +1, true, howManyToWin, alpha, beta);
                        board[y][x] = empty;
                        if (score < bestScore) {
                            bestScore = score;
                        }
                        //alpha-beta pruning
                        if (beta > bestScore) {
                            beta = bestScore;
                        }
                        if (beta <= alpha) {
                            break outMin;
                        }
                    }
                }
            }
        return bestScore;
        }
    }
    /**
     * Checks if the player or ai has won the game and return an in value indicating either an end state
     * or if game has not ended outputs which player has a longer row and both are doing equally well favours the ai . (short)
     * Output is an int because this winChecker was designed to be used together with a minmax algorithm
     * and considers the ai to be the maximizing player and the player to be minimizing. (long)
     * @param board is the board of the game
     * @param howManyToWin how many in a row is required for a victory
     * @param player char indicating the player on the board
     * @param ai char indicating the ai on the board
     * @param empty char indicating an empty space on the board
     * @return int value indicating the state of the board
     */
    public static int winChecker(char [][] board,int howManyToWin, char player, char ai, char empty) {
        //output settings
        int outputWhenPlayerWins = -1000;
        int outputWhenAiWins = 1000;
        int outputWhenTie = 0;
        

        int output = 0;
        int counterPlayerHorizontal = 0;
        int counterAiHorizontal = 0;
        int counterPlayerVertical = 0;
        int counterAiVertical = 0;
        int emptyCounter = 0;
        int longestPlayerRow = 0;
        int longestAiRow = 0;
        int counterPlayerDiagonalLeft = 0;
        int counterAiDiagonalLeft = 0;
        int counterPlayerDiagonalRight = 0;
        int counterAiDiagonalRight = 0;

        //Checks for vertical and horizontal victories
        out:
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                //if player char is found
                if (board[y][x] == player) {
                    //adds one to the appropriate counter
                    counterPlayerHorizontal++;
                    
                    //resets the same ai counter
                    //and checks if that counter had reached it all time high
                    if (counterAiHorizontal > longestAiRow) {
                        longestAiRow = counterAiHorizontal;
                    }
                    counterAiHorizontal = 0;   
                }
                else if (board[y][x] == ai) {
                    counterAiHorizontal++;
                    if (counterPlayerHorizontal > longestPlayerRow) {
                        longestPlayerRow = counterPlayerHorizontal;
                    }  
                    counterPlayerHorizontal = 0; 
                }
                if (board[x][y] == player) {
                    counterPlayerVertical++;
                    if (counterAiVertical > longestAiRow) {
                        longestAiRow = counterAiVertical;
                    }
                    counterAiVertical = 0;
                }
                else if (board[x][y] == ai) {
                    counterAiVertical++; 
                    if (counterPlayerVertical > longestPlayerRow) {
                        longestPlayerRow = counterPlayerVertical;
                    }  
                    counterPlayerVertical = 0;
                }
                if (board[y][x] == empty) {
                    emptyCounter++;
                }
                if (counterPlayerHorizontal == howManyToWin || counterPlayerVertical == howManyToWin) {
                    output = outputWhenPlayerWins;
                    break out;
                }
                else if (counterAiHorizontal == howManyToWin || counterAiVertical == howManyToWin) {
                    output = outputWhenAiWins;
                    break out;
                }
            }
        //saves the longest rows for minmax purposes
        if (counterPlayerVertical > longestPlayerRow) {
            longestPlayerRow = counterPlayerVertical;
        }
        if (counterPlayerHorizontal > longestPlayerRow) {
            longestPlayerRow = counterPlayerHorizontal;
        }
        if (counterAiHorizontal > longestAiRow) {
            longestAiRow = counterAiHorizontal;
        }  
        if (counterAiVertical > longestAiRow) {
            longestAiRow = counterAiVertical;
        }
        //resets counters when changing rows/columns
        counterAiHorizontal = 0;
        counterPlayerHorizontal = 0;
        counterPlayerVertical = 0;
        counterAiVertical = 0;
        }

        //Checks diagonal starting from the upmost left corner of the board
        diagonalLeft:
        for (int y = 0; y <= board.length - howManyToWin; y++) {
            for (int x = 0; x <= board.length - howManyToWin; x++) {
                for (int z = 0; z < howManyToWin; z++) {
                    if (board [y + z][x + z] == player) {
                        counterPlayerDiagonalLeft++;
                        if (counterAiDiagonalLeft > longestAiRow) {
                            longestAiRow = counterAiDiagonalLeft;
                        }
                        counterAiDiagonalLeft = 0;
                        if (counterPlayerDiagonalLeft == howManyToWin) {
                            output = outputWhenPlayerWins;
                            break diagonalLeft;
                        }
                    }
                    else if (board [y + z][x + z] == ai) {
                        counterAiDiagonalLeft++;
                        if (counterPlayerDiagonalLeft > longestPlayerRow) {
                            longestPlayerRow = counterPlayerDiagonalLeft;
                        }
                        counterPlayerDiagonalLeft = 0;
                        if (counterAiDiagonalLeft == howManyToWin) {
                            output = outputWhenAiWins;
                            break diagonalLeft;
                        }
                    }
                    else {
                        counterPlayerDiagonalLeft = 0;
                        counterAiDiagonalLeft = 0;
                    }
                }
            }
        }
        //Checks diagonal starting from the upmost right corner of the board
        diagonalRight:
        for (int y = 0; y <= board.length - howManyToWin; y++) {
            for (int x = board.length - 1; x >= howManyToWin - 1; x--) {
                for (int z = 0; z < howManyToWin; z++) {
                    if (board [y + z][x - z] == player) {
                        counterPlayerDiagonalRight++;
                        if (counterAiDiagonalRight > longestAiRow) {
                            longestAiRow = counterAiDiagonalRight;
                        }
                        counterAiDiagonalRight = 0;
                        if (counterPlayerDiagonalRight == howManyToWin) {
                            output = outputWhenPlayerWins;
                            break diagonalRight;
                        }
                    }
                    else if (board [y + z][x - z] == ai) {
                        counterAiDiagonalRight++;
                        counterPlayerDiagonalRight = 0;
                        if (counterAiDiagonalRight == howManyToWin) {
                            output = outputWhenAiWins;
                            break diagonalRight;
                        }
                    }
                    else {
                        counterPlayerDiagonalRight = 0;
                        counterAiDiagonalRight = 0;
                    }
                }
            }    
        }
        
        //if no winners
        if (output == 0) {
            //check for ties
            if (emptyCounter == 0) {
                output = outputWhenTie;
            }
            //if game has not ended outputs which player has a longer row and both are doing equally well favours ai for a more aggressive ai
            else if (longestAiRow >= longestPlayerRow) {
                output = longestAiRow;
            }
            else {
                output = -longestPlayerRow;
            }
            
        }
        return output;
    }
}
