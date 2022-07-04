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
}
