package util;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputReader {
	Scanner scanner;
	public InputReader() {
		scanner = new Scanner(System.in);
	}
	public String getInput() {
		return scanner.nextLine();
	}
	public int getNextInt() {
		int input = -2;
		while (input == -1) {
			try {
				input = scanner.nextInt();
			}
			catch (InputMismatchException e) {
				scanner.nextLine();
				input = -1;
			}
		}
		return input;
	}
	public int getNextInt(int minRange, int maxRange) {
		int input = -2;
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
	public String nextLine() {
		return scanner.nextLine();
	}
}