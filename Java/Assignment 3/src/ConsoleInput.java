//do not mind the implementation. Only the JavaDoc is relevant for you.

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.lang.IllegalArgumentException;
/**
 * This class implements methods that allow users to choose one or more choices
 * of strings from an given array.
 *
 * @author Patric Plattner &lt;patric.plattner@hci.uni-hannover.de&gt;
 * @version 28.04.2021
 */
public class ConsoleInput {
    private static Scanner scanner_;

    private static void initScanner() {
        if (scanner_ == null) {
            ConsoleInput.scanner_ = new Scanner(System.in);
        }
    }

    private static void forceReinitScanner() {
        ConsoleInput.scanner_ = new Scanner(System.in);
    }

    /**
     * This method takes a list of strings, presents them to the user via the
     * command line and lets them choose one.
     *
     * This method will always return a not null string, if the arguments are
     * valid. The arguments are valid, if both are not null and the array is not
     * empty and does not contain null values.
     *
     * The user is presented with all options and is forced to chose one of
     * them. The method will continue asking if the user enters an invalid
     * string, therefore no null will be returned.
     *
     * @author Patric Plattner &lt;patric.plattner@hci.uni-hannover.de&gt;
     * @param arr Choices presented to the user. Cannot be null or empty.
     *            All elements cannot be null.
     * @param msg Message to present to the user.
     *
     * @return String that the user chose. Cannot be null
     */
    public static String getChoice(String[] arr, String msg) {
        ConsoleInput.initScanner();
        if (arr == null || msg == null) {
            throw new
                    IllegalArgumentException("Neither arguments can be null.");
        }
        if (arr.length == 0) {
            throw new IllegalArgumentException("Arr cannot be empty.");
        }
        for (String str : arr) {
            if (str == null) {
                throw new IllegalArgumentException(
                        "Arr cannot contain a null element.");
            }
        }
        String result = null;
        do {
            System.out.println(msg);
            System.out.println("Choices:");
            for (String str : arr) {
                System.out.print("\"" + str + "\" ");
            }
            System.out.print("\nChoose one:");
            if (ConsoleInput.scanner_.hasNextLine()) {
                result = ConsoleInput.scanner_.nextLine();
                boolean found = false;
                for (String str : arr) {
                    if (result.equals(str))
                        found = true;
                }
                if (!found) {
                    System.out.
                            println("Please enter one of the given choices.");
                    result = null;
                }
            } else {
                ConsoleInput.forceReinitScanner();
            }
        } while(result == null);
        return result;
    }

    /**
     * This method takes a list of strings, presents them to the user via the
     * command line and lets them choose multiple.
     *
     * This method will let the user choose multiple values from the input array
     * as often as they want, so each element in the input array can be chosen
     * either zero times, one time or even multiple times.
     *
     * This method will always return a valid string array that does not contain
     * null values and is properly sized for the entered values, if the
     * arguments are valid. The arguments are valid, if both are not null and
     * the array is not empty and does not contain null values or "done".
     *
     * The user is presented with all options and is forced to chose one of
     * them to add to the list until they type "done". The method will continue
     * asking if the user enters an invalid string, therefore no unwanted
     * Elements will be returned.
     *
     * @author Patric Plattner &lt;patric.plattner@hci.uni-hannover.de&gt;
     * @param arr Choices presented to the user. Cannot be null or empty.
     *            No element can be null or "done".
     * @param msg Message to present to the user.
     *
     * @return Array of Strings that the user chose. Cannot be null.
     */
    public static String[] getMultipleChoices(String[] arr, String msg) {
        ConsoleInput.initScanner();
        if (arr == null || msg == null) {
            throw new
                    IllegalArgumentException("Neither arguments can be null.");
        }
        if (arr.length == 0) {
            throw new IllegalArgumentException("Arr cannot be empty.");
        }
        for (String str : arr) {
            if (str == null) {
                throw new IllegalArgumentException(
                        "Arr cannot contain a null element.");
            } else if (str.equals("done")) {
                throw new IllegalArgumentException(
                        "Arr cannot contain \"done\".");
            }
        }
        ArrayList<String> result = new ArrayList<String>();
        boolean repeat = true;
        do {
            System.out.println(msg);
            System.out.println("Choose one of the following:");
            for (String str : arr) {
                System.out.print("\"" + str + "\" ");
            }
            System.out.println("\nYour current choices:");
            for (String str : result) {
                System.out.print("\"" + str + "\" ");
            }
            System.out.print("\nEnter yout choice to add it to the list, or \"done\" to cancel:");
            if (ConsoleInput.scanner_.hasNextLine()) {
                String currentResult = ConsoleInput.scanner_.nextLine();
                boolean found = false;
                if (currentResult.equals("done")) {
                    found = true;
                    repeat = false;
                }
                for (String str : arr) {
                    if (currentResult.equals(str)){
                        found = true;
                        result.add(currentResult);
                        Collections.sort(result);
                        Collections.reverse(result);
                        break;
                    }
                }
                if (!found) {
                    System.out.
                            println("Please enter one of the given choices.");
                }
            } else {
                ConsoleInput.forceReinitScanner();
            }
        } while(repeat);
        return result.toArray(new String[0]);
    }
}
