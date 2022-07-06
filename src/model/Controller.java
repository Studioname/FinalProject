package model;

import model.Employee;
import model.MainPerformer;
import util.DatabaseManager;
import util.InputReader;
import util.DatabaseManager;
import model.Booking;
import model.Play;
import java.util.ArrayList;
import model.Basket;

public class Controller {
	DatabaseManager dbm;
	InputReader inputReader;
	boolean running = true;
	String[] defaultMenu;
	String[] subMenu;
	String[] searchBySubMenu;
	String[] previousScreen;
	int menuSelection;
	Basket basket;
	
	public Controller(){
		dbm = new DatabaseManager();
		inputReader = new InputReader();
		String[] defaultMenu = {"1. Search all shows", "2. Search by Name", "3. Search by Date", "4. Checkout", "0. Exit"};
		String[] subMenu = {"1. Add ticket to basket", "2. Return to previous screen"};
		basket = new Basket();
	}
	public void run() {
		while (running) {
			//welcome user, press a key to continue
			
			//menu screen
			printMenu(defaultMenu);
			menuSelection = getUserSelection(defaultMenu);
			switch (menuSelection) {
			//get all plays
			case 1: 
				ArrayList<Play> plays = dbm.constructPlayArrayList();
				dbm.printPlayArrayListBasic(plays);
				System.out.println("Please select a play #");
				Play play = plays.get(getMenuSelection());
				//some kind of function to display play details
				//call_function();
				printMenu(subMenu);
				menuSelection = 0;
				//while user selection is out of range
				menuSelection = getUserSelection(subMenu);
				switch(menuSelection) {
					case 1: break;
					//basket.addToBasket();
					case 2: break;
					default: System.out.println("Selection not recognised.");
				}
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
//	public void searchByName(String name) {
//		System.out.println("What is the name of the play you want to look for?");
//		String input = inputReader.nextLine();
//		ArrayList<Play> plays = dbm.constructPlayArrayList();
//		dbm.printPlayArrayListDetails(plays);
//	}
	
	public int getMenuSelection() {
		int userInput = inputReader.getNextInt();
		inputReader.nextLine();
		return userInput;
	}
	
	public void printMenu(String[] subMenu) {
		for (int i = 0; i < subMenu.length; i++) {
			System.out.println(subMenu[i]);
		}
	}
	
	public int getUserSelection(String[] menu) {
		while (menuSelection < 0 || menuSelection > menu.length) {
			System.out.println("Please enter a value between 0 and " + menu.length);
			menuSelection = getMenuSelection();
		}
		return menuSelection;
	}
}