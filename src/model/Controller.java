package model;

import model.Employee;
import model.MainPerformer;
import model.Booking;
import model.Play;
import model.Basket;
import util.DatabaseManager;
import util.InputReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;



public class Controller {
	DatabaseManager dbm;
	InputReader inputReader;
	boolean running;
	String[] defaultMenu;
	String[] subMenu;
	String[] searchBySubMenu;
	String[] basketMenu;
	String[] previousMenu;
	String[] currentMenu;
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
		defaultMenu = new String[] { "1. Search all shows", "2. Search by Name", "3. Search by Date", "4. Shopping Basket", "5. Employee Login",
				"0. Exit" };
		subMenu = new String []{ "1. Add ticket to basket", "2. Return to previous screen" };
		basketMenu = new String[] {"1. Show Basket Contents", "2. Proceed to Checkout"};
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
				System.out.println("Please select a play number.");
				int playSelection = inputReader.getNextInt();
				Play play = plays.get(playSelection-1);
				printMenu(subMenu);
				//while user selection is out of range
				
				//we need to ask them if they want to book a seat
				bookingPrompt(play);
				break;
			//not yet implemented
			case 2: 
				plays = dbm.constructArrayList(dbm.searchPlay(), callPlay());
				System.out.println("What is the name of the show you would you like to search for?");
				String showName = inputReader.getInput();
				plays = dbm.searchPlayByTitle(plays, showName);
				dbm.printBasic(plays, callPlay());
				System.out.println("Select a play number, or enter 0 to go to the previous screen");
				int searchByNameSelection = inputReader.getNextInt();
				if (searchByNameSelection != 0) {
					play = plays.get(searchByNameSelection-1);
					bookingPrompt(play);
				}
				else {
					break;
				}
				break;
				//ask user if they want to add to basket - need a function for it
			//not yet implemented
			case 3:
				plays = dbm.constructArrayList(dbm.searchPlay(), callPlay());
				System.out.println("What is the date of the show you would you like to search for?" + '\n' + 
						"Please use YYYY-MM-DD format.");
				String showDate = inputReader.getInput();
				plays = dbm.searchPlayByDate(plays, showDate);
				dbm.printBasic(plays, callPlay());
				System.out.println("Select a play number, or enter 0 to go to the previous screen");
				int searchByDateSelection = inputReader.getNextInt();
				if (searchByDateSelection != 0) {
					play = plays.get(searchByDateSelection-1);
					bookingPrompt(play);
				}
				else {
					break;
				}
				//ask user if they want to add to basket - need a function for it
				break;
			//shopping basket
			case 4:
				printMenu(basketMenu);
				int basketMenuSelection = inputReader.getNextInt(0, basketMenu.length);
				switch (basketMenuSelection) {
					//print basket
					case 1: basket.printBasketContents();
					//checkout
					case 2: break;
				}
				
				break;
			case 5:
				//Employee register/login
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
			case 0:
				running = false;
				break;
			default:
				System.out.println("Selection not recognised");
					}
				}
		
			}
//ladder of functions allowing users to go back and forth between menus
//	public void searchByName(String name) {
//		System.out.println("What is the name of the play you want to look for?");
//		String input = inputReader.nextLine();
//		ArrayList<Play> plays = dbm.constructPlayArrayList();
//		dbm.printPlayArrayListDetails(plays);
//	}


	public void printMenu(String[] subMenu) {
		for (int i = 0; i < subMenu.length; i++) {
			System.out.println(subMenu[i]);
		}
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
	
	/*
	 * Gets the playDate from play, records current date then adds seven days to it. We then compare
	 * the playDate to a week from now, and return true if a week from now is before or equal to the 
	 * play date.
	 */
	public boolean postageAvailable(Play play) {
		LocalDate playDate = LocalDate.parse(play.getPlayDate());
		LocalDate currentDate = LocalDate.now();
		LocalDate oneWeekFromNow = currentDate.plusDays(7);
		return oneWeekFromNow.isBefore(playDate) || oneWeekFromNow.isEqual(playDate);	
	}
	
	//booking prompt
	
	public void bookingPrompt(Play play){
		System.out.println("Would you like to make a booking? Press 1 for yes, 2 for no.");
		int subMenuSelection = inputReader.getNextInt();
		switch(subMenuSelection) {
			case 1:
				//we get details so we can create a booking object
				int stallsOrCircle = getSeatType();
				int noOfSeats = getNoOfSeats();
				int seatNumber = getSeatNumber(stallsOrCircle, noOfSeats);
                int [] seatNumbers = checkSeatAvailability(play, stallsOrCircle, seatNumber, noOfSeats);
				int noOfConcessions = getNoOfConcessions(noOfSeats);
				int isPostal = getIsPostal(play);
				if (addToBasketPrompt()) {
					createConcessionaryBookings(play, stallsOrCircle, noOfSeats, seatNumbers, noOfConcessions, isPostal);
					createBookings(play, stallsOrCircle, noOfSeats, seatNumbers, noOfConcessions, isPostal);
				} 
				else {
					break;}
			case 2:
				//takes us to main menu
				return;
			default: 
				System.out.println("Selection not recognised.");
		}
	}
	
	public int getSeatType() {
		System.out.println("What type of seat would you like to reserve? Please enter your selection.");
		System.out.println("1. Stalls" + '\n' + "2. Circle");
		return inputReader.getNextInt(1,2)-1;
	}
	
	public int getNoOfSeats() {
		System.out.println("How many seats would you like to reserve?");
		return inputReader.getNextInt();
	}
	
	public int getSeatNumber(int stallsOrCircle, int noOfSeats) {
		System.out.println("What number seat would you like to reserve from?");
		//depending on whether they want a stalls or circle seat, we set the max
		//seat number they can input by passing the maxSeats variable
		//we subtract the number of seats they want so they can't go over the maximum
		//ie if they want 4 seats, they have to book from seat 116
		switch (stallsOrCircle) {
			case 0: return inputReader.getNextInt(1, maxStallsSeats - noOfSeats);
			case 1: return inputReader.getNextInt(1, maxCircleSeats - noOfSeats);
			default: return -1;
		}
	}
	
	public void areSeatsOccupied(ArrayList<Integer> occupiedSeats) {
		if (occupiedSeats.size() > 0) {
			if (occupiedSeats.size() == 1) {
				System.out.println("Sorry! Seat " + occupiedSeats.get(0) + " is taken.");
			}
			else if (occupiedSeats.size() > 1) {
				System.out.println("Sorry! Seats " + getOccupiedSeatNumbers(occupiedSeats) + " are taken.");
			}
		}
	}
	
	public int[] checkSeatAvailability(Play play, int stallsOrCircle, int seatNumber, int noOfSeats) {
		ArrayList<Integer> occupiedSeats = dbm.getOccupiedSeats(play.getPlayId(), stallsOrCircle, seatNumber, noOfSeats);
		while (occupiedSeats.size() > 0) {
			areSeatsOccupied(occupiedSeats);
			seatNumber = getSeatNumber(stallsOrCircle, noOfSeats);
			occupiedSeats = dbm.getOccupiedSeats(play.getPlayId(), stallsOrCircle, seatNumber, noOfSeats);
		}
		
		//needs work
		
		//when they have chosen seats which aren't occupied, we make an array of seat numbers
		int [] seatNumbers = new int[noOfSeats];
		for (int i = 0; i < noOfSeats; i++) {
			seatNumbers[i] = seatNumber + i;
		}
		return seatNumbers;
	}
	
	public int getNoOfConcessions(int noOfSeats) {
		System.out.println("Enter the number of concessions. OAP, Student, etc");
		return inputReader.getNextInt(0, noOfSeats);
	}
	
	public int getIsPostal(Play play) {
		System.out.println("Do you want the tickets posted?");
		int isPostal = inputReader.getNextInt();
		if (isPostal == 1) {
			//check to see if the play falls within 7 days of the current date
			if (postageAvailable(play)) {
				isPostal = 1;
				System.out.println("Postage available. Tickets will arrive within seven days of booking.");
			}
			else {
				isPostal = 0;
				System.out.println("Sorry, postage is only available for plays being performed seven days or more after the booking date");
			}
		}
		return isPostal;
	}
	
	public boolean addToBasketPrompt() {
		System.out.println("Add tickets to basket?" + '\n' + "1. Yes" + '\n' + "2. No");
		int choice = inputReader.getNextInt(1, 2);
		switch (choice) {
		case 1: 
			System.out.println("Tickets added to basket.");
			return true;
		case 2: return false;
		default: return false;
		}
	}
	
	//both of these need fixing
	public void createConcessionaryBookings(Play play, int stallsOrCircle, int noOfSeats, int[] seatNumbers, int noOfConcessions, int isPostal) {
		for (int i = 0; i < seatNumbers.length; i++) {
			System.out.println("" + seatNumbers[i]);
		}
		for (int i = 0; i < noOfConcessions; i++) {
			Booking b = new Booking(play.getPlayId(), stallsOrCircle, seatNumbers[i], 1, isPostal);
			b.printBasicBookingDetails(i);
			basket.addToBasket(b);
		}
	}
	public void createBookings(Play play, int stallsOrCircle, int noOfSeats, int[] seatNumbers, int noOfConcessions, int isPostal) {
		for (int i = noOfConcessions; i < noOfSeats - noOfConcessions; i++) {
			Booking b = new Booking(play.getPlayId(), stallsOrCircle, seatNumbers[i], 0, isPostal);
			b.printBasicBookingDetails(i);
			basket.addToBasket(b);
		}
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