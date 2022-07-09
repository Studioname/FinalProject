package util;
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
		int input = scanner.nextInt();
		scanner.nextLine();
		return input;
	}
	public int getNextInt(int maxRange) {
		int input = 0;
		while (input <= 0 || input > maxRange){
		  input = scanner.nextInt();
		}
		scanner.nextLine();
		return input;
	}
	public String nextLine() {
		return scanner.nextLine();
	}
}