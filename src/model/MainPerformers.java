package model;

import java.util.ArrayList;

/**
 * Represents the MainPerformer class, an array list of the main performers for a particular play
 * @author Conan
 *
 */

public class MainPerformers {
	int playId;
	ArrayList<String> mainPerformers;
	
	/**
	 * Constructor for MainPerformer class
	 */
	public  MainPerformers() {
		mainPerformers = new ArrayList<String>();
	}
	
	/**
	 * Adds a main performer to mainPerformers
	 * @param performer
	 */
	public void addMainPerformer(String performer) {
		mainPerformers.add(performer);
	}
	
	/**
	 * Gets main performer at index i
	 */
	public String getMainPerformer(int index) {
		return mainPerformers.get(index);
	}
	
	/**
	 * Returns the size of mainPerformers
	 */
	public int getMainPerformersSize() {
		return mainPerformers.size();
	}
	
	/**
	 * Prints the contents of mainPerformers
	 */
	public void printMainPerformers() {
		System.out.print("Main performers: ");
		for (int i = 0; i < mainPerformers.size(); i++) {
			if (i != mainPerformers.size()-1) {
				System.out.print("" + mainPerformers.get(i) + ", ");
			}
			else {
				System.out.print("" + mainPerformers.get(i) + "." + '\n');
			}
		}
		System.out.println("");
	}
	
	/**
	 * Gets the value of playId
	 */
	public int getPlayId() {
		return playId;
	}
	
	/**
	 * Sets the value of playId
	 */
	public void setPlayId(int playId) {
		this.playId = playId;
	}
}
