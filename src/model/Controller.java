package model;

import model.Employee;
import model.MainPerformer;
import util.DatabaseManager;
import util.InputReader;
import util.DatabaseManager;
import model.Booking;
import model.Play;
import model.Basket;

public class Controller {
	DatabaseManager dbm;
	InputReader inputReader;
	boolean running = true;
	public Controller(){
		dbm = new DatabaseManager();
		inputReader = new InputReader();
	}
	public void run() {
		while (running) {
			//welcome user, press a key to continue
			
			//menu screen
			String[] defaultMenu = {"1. Search all shows", "2. Search by Name", "3. Search by Date",};
			for (int i = 0; i < defaultMenu.length; i++) {
				System.out.println("Menu choices");
			}
			int userInput = inputReader.getNextInt();
			inputReader.nextLine();
			switch (userInput) {
			//get all plays
			case 1: dbm.printResult(dbm.getPlays());
			//not yet implemented
			case 2: 
				//submenu - press 1 to add to basket, 2 to return to main menu
				break;
			//not yet implemented
			case 3: 
				//submenu - press 1 to add to basket, 2 to return to main menu
				break;
			case 4: 
				//
			}
			//submenus
			
			
		}
	}
}