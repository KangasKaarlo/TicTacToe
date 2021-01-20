package src.util;
/**
 * Class focused on the visual output of the game (short)
 */
public class Ui {
    /**
     * Prints out an 2d array with guide numbers for all the rows and columns. (short)
     * @param board is the 2D array to be printed
     */
    public static void printBoard(char[][] board) {

        System.out.print("  ");
        for (int v = 0; v < board.length; v++) {
            //Prints guide numbers for the vertical lines.
            System.out.print(v+1 );
            if (v < 9) {
                System.out.print(" ");
            }
        }
        System.out.println("");
        for (int y = 0; y < board.length; y++) {
            //Prints guide numbers for the horizontal lines.
            System.out.print(y+1);
            if (y < 9) {
                System.out.print(" ");
            }
            //Prints the board itself.
            for (int x = 0; x < board[y].length; x++) {
                System.out.print(board[y][x]);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
}
