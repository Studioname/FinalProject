package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
	
	public void writeToFile(String string) {
		 try {
		      FileWriter fileWriter = new FileWriter("PurchaseDetails.txt");
		      fileWriter.write("" + string);
		      fileWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
}
