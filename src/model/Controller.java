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
	boolean running;
	String[] defaultMenu;
	String[] subMenu;
	String[] searchBySubMenu;
	String[] previousScreen;
	Basket basket;
	String validChars;
	String validPasswordChars;
	int maxStallsSeats;
	int maxCircleSeats;
	
	public Controller() {
		validChars = "abcdefghijklmnopqrstuvwxyz1234567890_-.";
		validPasswordChars = "abcdefghijklmnopqrstuvwxyz1234567890_-.!£$%^&*()+-=[]{}'#@~,/<>?|\"";
		maxStallsSeats = 120;
		maxCircleSeats = 80;
		running = true;
		
		dbm = new DatabaseManager();
		dbm.connect("FinalProject", "jdbc:mysql://127.0.0.1:3306/");
		inputReader = new InputReader();
		defaultMenu = new String[] { "1. Search all shows", "2. Search by Name", "3. Search by Date", "4. Checkout", "5. Employee Login",
				"0. Exit" };
		subMenu = new String []{ "1. Add ticket to basket", "2. Return to previous screen" };
		basket = new Basket();
	}

	public void run() {
		Scanner scan = new Scanner(System.in);
		while (running) {
			//welcome user, press a key to continue
			printWelcome();
			
			//menu screen
			printMenu(defaultMenu);
			int defaultMenuSelection = scan.nextInt();// getUserSelection(defaultMenu.length);
			switch (defaultMenuSelection) {
			//get all plays
			case 1: 
				ArrayList<Play> plays = dbm.constructArrayList(dbm.searchPlay(), callPlay());
				dbm.printBasic(plays, callPlay());
				System.out.println("Please select a play #");
				int playSelection = getMenuSelection();
				Play play = plays.get(playSelection);
				printMenu(subMenu);
				//while user selection is out of range
				int subMenuSelection = getUserSelection(subMenu.length);
				//we need to ask them if they want to book a seat
				System.out.println("Would you like to make a booking? Press 1 for yes, 2 for no.");
				switch(subMenuSelection) {
					case 1:
						System.out.println("What type of seat would you like to reserve? Please enter your selection.");
						System.out.println("1. Stalls\\n2. Circle");
						//we need a second variable to hold the seat type, since we will be reassigning
						//menuSelection when we ask how many seats they want
						int stallsOrCircle = getUserSelection(2);
						System.out.print("Enter the quantity: ");
	                    int noOfSeats = scan.nextInt();
						System.out.println("What number seat would you like?");
						//depending on whether they want a stalls or circle seat, we set the max
						//seat number they can input by passing the maxSeats variable
						//we subtract the number of seats they want so they can't go over the maximum
						//ie if they want 4 seats, they have to book from seat 116
						int seatNumber;
						switch (stallsOrCircle) {
							case 1: seatNumber = getUserSelection(maxStallsSeats - noOfSeats);
							break;
							case 2: seatNumber = getUserSelection(maxCircleSeats = noOfSeats);
							break;
							default: seatNumber = -1;
						}
						//get number of seats they want to reserve
						
	                    //now we know how many seats they want, and what type, we can query the database
						//to see if the seats are free
	                    //if the size of the array list returned by getOccupiedSeats is greater than 0, then one of
	                    //those seats is taken
	                    ArrayList<Integer> occupiedSeats = dbm.getOccupiedSeats(play.getPlayId(), stallsOrCircle, seatNumber, noOfSeats);
						if (occupiedSeats.size() > 0) {
							if (occupiedSeats.size() == 1) {
								System.out.println("Sorry! Seat " + occupiedSeats.get(0) + " is taken.");
							}
							else if (occupiedSeats.size() > 1) {
								System.out.println("Sorry! Seats " + getOccupiedSeatNumbers(occupiedSeats) + " are taken.");
							}
						}
						//else the seats are free and the booking can go ahead
						else {
							//we make an array of seat numbers
							int [] seatNumbers = new int[noOfSeats];
							for (int i = 0; i < noOfSeats; i++) {
								for (int j = seatNumber; j < seatNumber + noOfSeats; j++) {
									seatNumbers[i] = j;
								}
							}
							//we want to get more details from them to create a basic booking object we can add
							//to the shopping basket
							System.out.println("Enter the number of concessions. OAP, Student, etc");
							int noOfConcessions = getUserSelection(noOfSeats);
							System.out.println("Do you want the tickets posted?");
							int isPostal = getUserSelection(noOfSeats);
							if (isPostal == 1) {
								//check to see if the play falls within 7 days of the current date
							}
							//we want to create a user facing booking object
							//we do this with the user facing constructor
							//we do this for each seat they want to book [it counts as a booking]
							for (int i = 0; i < seatNumbers.length; i++) {
								while (noOfConcessions > 0) {
									Booking b = new Booking(play.getPlayId(), stallsOrCircle, seatNumbers[i], 1, isPostal);
									noOfConcessions--;
									i++;
									basket.addToBasket(b);
								}
								Booking b = new Booking(play.getPlayId(), stallsOrCircle, seatNumbers[i], 0, isPostal);
								basket.addToBasket(b);
							}
						}
                    
//                    if(res > 0) {
//                        System.out.println(res + " have been added to basket.");
//                    }
//                    else {
//                        System.out.println("Sorry! The seat is unavailable");
//                    }
                    //basket.addToBasket(res, quantity);
		            
		            
		            break;
					//basket.addToBasket();
					case 2: break;
					default: System.out.println("Selection not recognised.");
				}
			//not yet implemented
			case 2: 
				//submenu - press 1 to add to basket, 2 to return to main menu
				continue;
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
			continue;
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
		return userInput;
	}

	public void printMenu(String[] subMenu) {
		for (int i = 0; i < subMenu.length; i++) {
			System.out.println(subMenu[i]);
		}
	}

	public int getUserSelection(int menuLength) {
		int menuSelection = -1;
		while (menuSelection <= 0 || menuSelection > menuLength) {
			System.out.println("Please enter a value between 1 and " + menuLength);
			menuSelection = getMenuSelection();
		}
		return menuSelection;
	}
	
	public void printWelcome() {
		System.out.println();
		System.out.println("Welcome to the Theatre Royal!");
		System.out.println();
	}
	
	public String getOccupiedSeatNumbers(ArrayList<Integer> occupiedSeats) {
		String str = "";
		for (int i = 0; i < occupiedSeats.size(); i++) {
			if (i != occupiedSeats.size() -1) {
				str += occupiedSeats.get(i) + ", ";
			}
			else {
				str += occupiedSeats.get(i);
			}
		} 
		return str;
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