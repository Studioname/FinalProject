package util;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Gets input from the user and validates it
 * @author Conan
 *
 */
public class InputReader {
	Scanner scanner;
	/**
	 * Constructor for InputReader class
	 */
	public InputReader() {
		scanner = new Scanner(System.in);
	}
	
	/**
	 * Returns user input as string
	 * @return
	 */
	public String getInput() {
		return scanner.nextLine();
	}
	
	/**
	 * Returns the next integer entered by the user with input validation to prevent non-ints from being
	 * entered
	 * @return
	 */
	public int getNextInt() {
		int input = -2;
		while (input == -2) {
			try {
				input = scanner.nextInt();
			}
			catch (InputMismatchException e) {
				scanner.nextLine();
				input = -2;
			}
		}
		scanner.nextLine();
		return input;
	}
	
	/**
	 * Returns the next integer entered by the user that falls within the given range. Uses input validation
	 * to prevent the user from entering strings, floats etc
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public int getNextInt(int minRange, int maxRange) {
		int input = minRange -1;
		while (input < minRange || input > maxRange){
			try {
				input = scanner.nextInt();
			}
			catch (InputMismatchException e) {
				System.out.println("Please enter a number between " + minRange + " and " + maxRange);
				scanner.nextLine();
			}
		}
		scanner.nextLine();
		return input;
	}
	
	/**
	 * Returns user input as string
	 * @return
	 */
}