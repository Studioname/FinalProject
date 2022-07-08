package model;

import model.Employee;
import model.MainPerformer;
import util.DatabaseManager;
import util.InputReader;
import model.Booking;
import model.Play;
import java.util.ArrayList;
import java.util.Scanner;

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
	String validChars;
	String validPasswordChars;
	
	public Controller() {
		validChars = "abcdefghijklmnopqrstuvwxyz1234567890-.";
		validPasswordChars = "abcdefghijklmnopqrstuvwxyz1234567890-.!£$%^&*()+-=[]{}'#@~,/<>?|\"";
		
		dbm = new DatabaseManager();
		inputReader = new InputReader();
		String[] defaultMenu = { "1. Search all shows", "2. Search by Name", "3. Search by Date", "4. Checkout", "5. Employee Login",
				"0. Exit" };
		String[] subMenu = { "1. Add ticket to basket", "2. Return to previous screen" };
		basket = new Basket();
	}

	public void run() {
		Scanner scan = new Scanner(System.in);

		
		while (running) {
			//welcome user, press a key to continue
			printWelcome();
			
			//menu screen
			printMenu(defaultMenu);
			menuSelection = getUserSelection(defaultMenu);
			switch (menuSelection) {
			//get all plays
			case 1: 
				ArrayList<Play> plays = dbm.constructPlayArrayList();
				dbm.printBasic(plays, callPlay());
				System.out.println("Please select a play #");
				Play play = plays.get(getMenuSelection());
				//some kind of function to display play details
				//call_function();
				printMenu(subMenu);
				menuSelection = 0;
				//while user selection is out of range
				menuSelection = getUserSelection(subMenu);
				switch(menuSelection) {
					case 1://sub menu 1 - add to basket
						
		            System.out.println("Enter a seat you want to reserve");
                    int res = scan.nextInt();
                    System.out.print("Enter the quantity: ");
                    int quantity = scan.nextInt();
                    
                    if(res > 0) {
                        System.out.println(res + " have been added to basket.");
                    }
                    else {
                        System.out.println("Sorry! The seat is unavailable");
                    }
                    //basket.addToBasket(res, quantity);
		            
		            
		            break;
					//basket.addToBasket();
					case 2: break;
					default: System.out.println("Selection not recognised.");
				}
			//not yet implemented
			case 2: 
				//submenu - press 1 to add to basket, 2 to return to main menu
				break;
			//not yet implemented
			case 3: //Employee register/login
				        Scanner sc = new Scanner(System.in);
				        String sp=" ";
				        System.out.println("Enter the Username");
				        String userName = sc.nextLine();
				        if((userName.contains(sp)) || userName.length()<6){
				            System.out.println("Invalid Username");
				            return;
				        }

				        System.out.println("Enter the Password");
				        String userPass = sc.nextLine();
				        if((userPass.contains(sp)) || userPass.length()<8){
				            System.out.println("Invalid Password");
				            return;
				        }

				        System.out.println(userName + " you are Registered Successfully");

				        System.out.println("Enter the Username");
				        String loginName = sc.nextLine();
				        System.out.println("Enter the Password");
				        String loginPass = sc.nextLine();

				        if(userName.equals(loginName) && userPass.equals(loginPass)){
				            System.out.println("Welcome "+loginName+" you have Logged-in Successfully");
				        }
				        else{
				            System.out.println("Username or password Mismatch");
				        }
				    }
			
				break;
			}
			//submenus
			
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
	
	public void printWelcome() {
		System.out.println();
		System.out.println("Welcome to the Theatre Royal!");
		System.out.println();
	}
	
	
	//these are helper methods used to call methods in the dbm class
	//some of those methods behave according to the type of object passed to them, so these methods
	//pass an empty object
	public Play callPlay() {
		Play play = new Play();
		return play;
	}
	public Booking callBooking() {
		Booking booking = new Booking();
		return booking;
	}
	public Customer callCustomer() {
		Customer customer = new Customer();
		return customer;
	}
}