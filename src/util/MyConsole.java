package src.util;
import java.io.Console; 
/**
 * This class takes input from the user using console. (short)
 */
public class MyConsole {
    /**
     * This method returns an int value that has been asked from the user and checks that the input is correct.(short)
     * A correct input is a number between or same as the min and max values and it's a int number.
     * It also displays a  error message, depending how the input was wrong. (long)
     * @param min Smallest the output can be
     * @param max Largest the output can be
     * @return an int value that has been asked from the user
     */
    public static int readInt(int min, int max) {

        Console c = System.console();
        int output = 0;

        boolean noNumberFound;
        boolean inputNotMinMax;

        do {
            noNumberFound = false;
            inputNotMinMax = false;
            try {
                output = Integer.parseInt(c.readLine());
                if (!(output >= min && output <= max)) {
                    System.out.println("Number outside of the desired range. Please try again:");
                    inputNotMinMax = true;
                }
            }
            catch(NumberFormatException e) {
                noNumberFound = true;
                System.out.println("please give a proper number:");
            }
        } while (noNumberFound || inputNotMinMax); 
        return output;
    }
}