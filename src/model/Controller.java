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
	String[] employeeMenu;
	private final Basket basket;
	String validChars;
	String validPasswordChars;
	int maxStallsSeats;
	int maxCircleSeats;
	Customer customer;
	boolean customerLoggedIn;
	boolean employeeLoggedIn;
	Employee employee;

	public Controller() {
		validChars = "abcdefghijklmnopqrstuvwxyz1234567890_-.";
		validPasswordChars = "abcdefghijklmnopqrstuvwxyz1234567890_-.!£$%^&*()+-=[]{}'#@~,/<>?|\"";

		maxStallsSeats = 120;
		maxCircleSeats = 80;
		running = true;
		customerLoggedIn = false;

		dbm = new DatabaseManager();
		dbm.connect("FinalProject", "jdbc:mysql://127.0.0.1:3306/");

		inputReader = new InputReader();
		basket = new Basket();

		defaultMenu = new String[] { "1. Search all shows", "2. Search by Name", "3. Search by Date",
				"4. Shopping Basket", "5. Employee Login", "6. Logout", "0. Exit" };
		subMenu = new String[] { "1. Add ticket to basket", "2. Return to previous screen" };
		basketMenu = new String[] { "1. Show Basket Contents", "2. Proceed to Checkout", "0. Return to Main Menu" };
		employeeMenu = new String[] { "1. Add show", "2. Remove show", "3. Logout" };
	}

	public void run() {
		while (running) {
			while (employeeLoggedIn) {
				System.out.println("Welcome " + employee.getEmployeeUsername() + "! Please enter your selection");
				printMenu(employeeMenu);
				int employeeMenuSelection = inputReader.getNextInt(0, employeeMenu.length);

				switch (employeeMenuSelection) {
				case 1:

					System.out.println("Title: ");
					String title = inputReader.getInput();
					System.out.println("Type: ");
					int type = inputReader.getInt();
					System.out.println("Description: ");
					String description = inputReader.getInput();
					System.out.println("Time: ");
					String time = inputReader.getInput();
					System.out.println("Date: ");
					String date = inputReader.getInput();
					System.out.println("Duration: ");
					String duration = inputReader.getInput();
					System.out.println("Circle Seat Price: ");
					int circleSeatPrice = inputReader.getInt();
					System.out.println("Stalls Seat Price: ");
					int stallsSeatPrice = inputReader.getInt();
					System.out.println("Language: ");
					String language = inputReader.getInput();
					System.out.println("Musical Accompaniment: ");
					int musicalAccompaniment = inputReader.getInt();
					Play p = new Play(title, type, description, time, date, duration, circleSeatPrice, stallsSeatPrice,
							language, musicalAccompaniment);
					dbm.addPlay(p);
					break;
					
				case 2: 
//					dbm.removePlay();
					break;
				case 3: 
					logout();
					break;
				default:
					System.out.println("Selection not recognised");
				}

			}
			// welcome user, press a key to continue
			printWelcome();

			// menu screen
			printMenu(defaultMenu);
			int defaultMenuSelection = inputReader.getNextInt(0, defaultMenu.length);
			ArrayList<Play> plays = dbm.constructArrayList(dbm.searchPlay(), callPlay());
			switch (defaultMenuSelection) {
			// get all plays
			case 1:
				dbm.printBasic(plays, callPlay());
				System.out.println("Please select a play number.");
				int playSelection = inputReader.getNextInt(1, plays.size());
				Play play = plays.get(playSelection - 1);
				printMenu(subMenu);
				bookingPrompt(play);
				break;
			case 2:
				searchPrompt("name", plays);
				break;
			case 3:
				searchPrompt("date", plays);
				break;
			// shopping basket
			case 4:
				printMenu(basketMenu);
				basket.setBookingPrices(plays);
				basket.applyConcessions();
				int basketMenuSelection = inputReader.getNextInt(0, basketMenu.length);
				switch (basketMenuSelection) {
				// print basket
				case 1:
					basket.printCheckoutDetails();
					break;
				// checkout
				case 2:
					// sneaky short circuit
					if (basket.getSize() == 0) {
						System.out.println("Your basket is empty!");
						break;
					}
					basket.printCheckoutDetails();
					if (!customerLoggedIn) {
						loginPrompt();
					}
					System.out.println("Would you like to complete your purchase?" + '\n' + "1. Yes" + '\n' + "2. No");
					int purchaseSelection = inputReader.getNextInt(1, 2);
					switch (purchaseSelection) {
					case 1:
						System.out.println("Thank you for your purchase. "
								+ basket.getFormattedPrice(basket.getBookingsTotal() + basket.calculatePostage())
								+ " has been deducted from your account. "
								+ "Details of this purchase have been sent to " + customer.getCustomerEmail() + ".");
					case 2:
						break;
					}

					break;
				case 0:
					break;
				}

				break;
			case 5:
				employeeLogin();
				break;
//				//Employee register/login
//				 Scanner sc = new Scanner(System.in);
//				 String sp=" ";
//				 System.out.println("Enter the Username");
//				 String userName = sc.nextLine();
//				 if((userName.contains(sp)) || userName.length()<6){
//				     System.out.println("Invalid Username");
//				     break;
//				 }
//
//				 System.out.println("Enter the Password");
//				 String userPass = sc.nextLine();
//				 if((userPass.contains(sp)) || userPass.length()<8){
//				     System.out.println("Invalid Password");
//				     break;
//				 }
//
//				 System.out.println(userName + " you are Registered Successfully");
//
//				 System.out.println("Enter the Username");
//				 String loginName = sc.nextLine();
//				 System.out.println("Enter the Password");
//				 String loginPass = sc.nextLine();
//
//				 if(userName.equals(loginName) && userPass.equals(loginPass)){
//				     System.out.println("Welcome "+loginName+" you have Logged-in Successfully");
//				 }
//				 else{
//				     System.out.println("Username or password Mismatch");
//				        }
			case 6:
				logout();
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
			if (i != occupiedSeats.size() - 1) {
				str += occupiedSeats.get(i) + ", ";
			} else {
				str += occupiedSeats.get(i);
			}
		}
		return str;
	}

	/*
	 * Gets the playDate from play, records current date then adds seven days to it.
	 * We then compare the playDate to a week from now, and return true if a week
	 * from now is before or equal to the play date.
	 */
	public boolean postageAvailable(Play play) {
		LocalDate playDate = LocalDate.parse(play.getPlayDate());
		LocalDate currentDate = LocalDate.now();
		LocalDate oneWeekFromNow = currentDate.plusDays(7);
		return oneWeekFromNow.isBefore(playDate) || oneWeekFromNow.isEqual(playDate);
	}

	// booking prompt

	public void bookingPrompt(Play play) {
		System.out.println("Would you like to make a booking? Press 1 for yes, 2 for no.");
		int subMenuSelection = inputReader.getNextInt(1, 2);
		switch (subMenuSelection) {
		case 1:
			// we get details so we can create a booking object
			int stallsOrCircle = getSeatType();
			int noOfSeats = getNoOfSeats();
			int seatNumber = getSeatNumber(stallsOrCircle, noOfSeats);
			int[] seatNumbers = checkSeatAvailability(play, stallsOrCircle, seatNumber, noOfSeats);
			int noOfConcessions = getNoOfConcessions(noOfSeats);
			int isPostal = getIsPostal(play);
			if (addToBasketPrompt()) {
				createBookings(play, stallsOrCircle, noOfSeats, seatNumbers, noOfConcessions, isPostal);
				createConcessionaryBookings(noOfConcessions);
			} else {
				break;
			}
		case 2:
			// takes us to main menu
			break;
		default:
			System.out.println("Selection not recognised.");
		}
	}

	public int getSeatType() {
		System.out.println("What type of seat would you like to reserve? Please enter your selection.");
		System.out.println("1. Stalls" + '\n' + "2. Circle");
		return inputReader.getNextInt(1, 2) - 1;
	}

	public int getNoOfSeats() {
		System.out.println("How many seats would you like to reserve?");
		return inputReader.getNextInt();
	}

	public int getSeatNumber(int stallsOrCircle, int noOfSeats) {
		System.out.println("What number seat would you like to reserve from?");
		// depending on whether they want a stalls or circle seat, we set the max
		// seat number they can input by passing the maxSeats variable
		// we subtract the number of seats they want so they can't go over the maximum
		// ie if they want 4 seats, they have to book from seat 116
		switch (stallsOrCircle) {
		case 0:
			return inputReader.getNextInt(1, maxStallsSeats - noOfSeats);
		case 1:
			return inputReader.getNextInt(1, maxCircleSeats - noOfSeats);
		default:
			return -1;
		}
	}

	public void areSeatsOccupied(ArrayList<Integer> occupiedSeats) {
		if (occupiedSeats.size() > 0) {
			if (occupiedSeats.size() == 1) {
				System.out.println("Sorry! Seat " + occupiedSeats.get(0) + " is taken.");
			} else if (occupiedSeats.size() > 1) {
				System.out.println("Sorry! Seats " + getOccupiedSeatNumbers(occupiedSeats) + " are taken.");
			}
		}
	}

	public int[] checkSeatAvailability(Play play, int stallsOrCircle, int seatNumber, int noOfSeats) {
		ArrayList<Integer> occupiedSeats = dbm.getOccupiedSeats(play.getPlayId(), stallsOrCircle, seatNumber,
				noOfSeats);
		while (occupiedSeats.size() > 0) {
			areSeatsOccupied(occupiedSeats);
			seatNumber = getSeatNumber(stallsOrCircle, noOfSeats);
			occupiedSeats = dbm.getOccupiedSeats(play.getPlayId(), stallsOrCircle, seatNumber, noOfSeats);
		}
		// when they have chosen seats which aren't occupied, we make an array of seat
		// numbers
		int[] seatNumbers = new int[noOfSeats];
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
		System.out.println("Do you want the tickets posted?" + '\n' + "1. Yes" + '\n' + "2. No");
		int isPostal = inputReader.getNextInt(1, 2);
		if (isPostal == 1) {
			// check to see if the play falls within 7 days of the current date
			if (postageAvailable(play)) {
				isPostal = 1;
				System.out.println("Postage available. Tickets will arrive within seven days of booking.");
			} else {
				isPostal = 0;
				System.out.println(
						"Sorry, postage is only available for plays being performed seven days or more after the booking date");
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
		case 2:
			return false;
		default:
			return true;
		}
	}

	// both of these need fixing
	public void createConcessionaryBookings(int noOfConcessions) {
		for (int i = 0; i < noOfConcessions; i++) {
			basket.getItem(i).setConcession(1);
		}
	}

	public void createBookings(Play play, int stallsOrCircle, int noOfSeats, int[] seatNumbers, int noOfConcessions,
			int isPostal) {
		for (int i = 0; i < noOfSeats; i++) {
			Booking b = new Booking(play.getPlayId(), stallsOrCircle, seatNumbers[i], 0, isPostal);
			basket.addToBasket(b);
		}
	}

	// we split one function into two so that we can search from result screen
	public ArrayList<Play> searchByNameOrDate(String nameOrDate, ArrayList<Play> plays) {
		System.out.println("What is the " + nameOrDate + " of the show you would you like to search for?");
		String searchTerm = inputReader.getInput();
		switch (nameOrDate) {
		case "name":
			plays = dbm.searchPlayByTitle(plays, searchTerm);
			break;
		case "date":
			plays = dbm.searchPlayByDate(plays, searchTerm);
			break;
		}
		dbm.printBasic(plays, callPlay());
		return plays;
	}

	public void searchPrompt(String nameOrDate, ArrayList<Play> allPlays) {
		ArrayList<Play> results = searchByNameOrDate(nameOrDate, allPlays);
		System.out.println("Select a play number, enter -1 to search again by " + nameOrDate
				+ ", or enter 0 to go to the previous screen");
		int selection = inputReader.getNextInt(-1, results.size());
		if (selection == -1) {
			searchByNameOrDate(nameOrDate, allPlays);
		} else if (selection == 0) {
			return;
		} else {
			Play play = results.get(selection - 1);
			bookingPrompt(play);
		}
	}

	public void loginPrompt() {
		System.out.println("To proceed with the purchase, please login." + '\n' + "1. Login" + '\n' + "2. Register"
				+ '\n' + "0. Return to Main Menu");
		int loginMenuSelection = inputReader.getNextInt(0, 2);
		switch (loginMenuSelection) {
		case 1:
			customerLogin();
			break;
		case 2:
			registerCustomer();
			System.out.println("Thank you for registering. Please login to continue");
			customerLogin();
			break;
		case 0:
			break;
		}
	}

	public void customerLogin() {
		while (!customerLoggedIn) {
			System.out.println("Please enter your username");
			String username = inputReader.getInput();
			System.out.println("Please enter your password");
			String password = inputReader.getInput();
			if (dbm.validateCredentials(username, password)) {
				customerLoggedIn = true;
				customer = dbm.fetchCustomerObject(dbm.searchCustomerByLoginDetails(username, password));
			} else {
				loginPrompt();
			}
		}
	}

	public void registerCustomer() {
		// we get customer details, create customer object and push to db
		System.out.println("Please enter your first name");
		String forename = inputReader.getInput();
		System.out.println("Please enter your second name");
		String surname = inputReader.getInput();
		System.out.println("Please enter your address");
		String address = inputReader.getInput();
		System.out.println("Please enter your telephone number");
		String telephone = inputReader.getInput();
		System.out.println("Please enter your email address");
		String email = inputReader.getInput();
		System.out.println("Please enter a username");
		String username = inputReader.getInput();
		System.out.println("Please enter a password");
		String password = inputReader.getInput();
		System.out.println("Please enter your credit card number");
		String paymentDetails = inputReader.getInput();
		Customer c = new Customer(username, password, forename, surname, address, telephone, email, paymentDetails);
		dbm.addCustomer(c);
	}

	public void logout() {
		if (customerLoggedIn || employeeLoggedIn) {
			customerLoggedIn = false;
			employeeLoggedIn = false;
			customer = null;
			employee = null;
			System.out.println("User logged out successfully.");
		} else {
			System.out.println("You are not logged in!");
		}
	}

	// employee
	public void employeeLogin() {
		while (!employeeLoggedIn) {
			System.out.println("Please enter your username");
			String username = inputReader.getInput();
			System.out.println("Please enter your password");
			String password = inputReader.getInput();
			if (dbm.validateEmployeeCredentials(username, password)) {
				employeeLoggedIn = true;
				employee = dbm.fetchEmployeeObject(dbm.searchEmployeeByLoginDetails(username, password));
			} else {
				return;
			}
		}
	}

	// these are helper methods used to call methods in the dbm class
	// some of those methods behave according to the type of object passed to them,
	// so these methods
	// pass an empty object
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

	public Employee callEmployee() {
		Employee employee = new Employee();
		return employee;
	}
}