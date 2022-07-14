package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class can create and write to a file located in the root folder of this program
 * @author Conan
 *
 */
public class FileManager {
	
	/**
	 * Constructor for class FileReader
	 */
	public FileManager() {
		
	}
	
	/**
	 * Creates a file in the main directory called PurchaseDetails.txt
	 */
	public void createFile() {
		try {
		      File purchaseDetails = new File("PurchaseDetails.txt");
		      if (purchaseDetails.createNewFile()) {
		        System.out.println("File created: " + purchaseDetails.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		}
	
	/**
	 * Writes passed string to PurchaseDetails.txt
	 * @param string
	 */
	public void writeToFile(String string) {
		 try {
		      FileWriter fileWriter = new FileWriter("PurchaseDetails.txt");
		      fileWriter.write("" + string);
		      fileWriter.close();
		      System.out.println("Thank you for your purchase. A text file containing the purchase details of this transaction has been added to to the root folder of this program.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
}
