package model;

import model.Employee;
import model.MainPerformers;
import model.Booking;
import model.Play;
import model.Basket;
import util.DatabaseManager;
import util.FileManager;
import util.InputReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

/**
 * Represents the Controller class. This class takes the other classes in model and turns them into a program
 * @author Conan
 *
 */

public class Controller {
	DatabaseManager dbm;
	InputReader inputReader;
	boolean running;
	String[] defaultMenu;
	String[] subMenu;
	String[] basketMenu;
	String[] employeeMenu;
	String[] addPerformersMenu;
	private final Basket basket;
	String validUsernameChars;
	String validPasswordChars;
	int maxStallsSeats;
	int maxCircleSeats;
	Customer customer;
	boolean customerLoggedIn;
	boolean employeeLoggedIn;
	Employee employee;
	
	/**
	 * Constructor for Controller class
	 */
	public Controller() {
		validUsernameChars = "abcdefghijklmnopqrstuvwxyz1234567890_-.";
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
		basketMenu = new String[] { "1. Show Basket Contents", "2. Proceed to Checkout", "0. Return to Main Menu" };
		employeeMenu = new String[] { "1. Add play", "2. Remove play", "3. Add Main Performer", "4. Logout" };
		addPerformersMenu = new String[] {"1. Browse a list of plays to add Main Performers to", "2. Add Main Performers by Play Id", "0. Return to Employee Menu"};
	}
	
	/**
	 * Runs the Theatre Booking program
	 */
	public void run() {
		while (running) {
			while (employeeLoggedIn) {
				System.out.println("Welcome " + employee.getEmployeeUsername() + "! Please enter your selection");
				printMenu(employeeMenu);
				int employeeMenuSelection = inputReader.getNextInt(0, employeeMenu.length);
				ArrayList<Play> plays = dbm.constructArrayList(dbm.searchPlay(), callPlay());
				switch (employeeMenuSelection) {
				case 1:
					createPlay();
					break;
				case 2:
					removePlay();
					break;
				case 3: 
					printMenu(addPerformersMenu);
					int addPerformersMenuSelection = inputReader.getNextInt(0, addPerformersMenu.length);
					switch (addPerformersMenuSelection) {
					case 0: break;
					case 1: 
						Play play = selectPlay(plays);
						System.out.println("What is the name of the Main Performer you would like to add?");
						String name = inputReader.getInput();
						dbm.addPerformer(play.getPlayId(), name);
						break;
					case 2:
						System.out.println("What is the Id of the play you would like to add a Main Performer to?");
						int playSelection = inputReader.getNextInt(1, plays.size());
						System.out.println("What is the name of the Main Performer you would like to add?");
						String name2 = inputReader.getInput();
						dbm.addPerformer(playSelection, name2);
						break;
					}
					break;
					//add by play select
					//add by play id
					//dbm.addMainPerformer(, validPasswordChars)
				case 4:
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
				bookingPrompt(selectPlay(plays));
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
						basket.setCustomer(customer);
						FileManager f = new FileManager();
						f.createFile();
						f.writeToFile(basket.getPurchaseDetails());
						basket.clearBasket();
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

	/**
	 * Prints the contents of a String array
	 * @param play
	 * @return
	 */
	public void printMenu(String[] subMenu) {
		for (int i = 0; i < subMenu.length; i++) {
			System.out.println(subMenu[i]);
		}
	}
	
	/**
	 * Returns a play selected from a list by the user
	 */
	public Play selectPlay(ArrayList<Play> plays) {
		dbm.printBasic(plays, callPlay());
		System.out.println("Please select a play number.");
		int playSelection = inputReader.getNextInt(1, plays.size());
		Play play = plays.get(playSelection - 1);
		play.printPlayDetails();
		fetchAndPrintMainPerformers(play);
		return play;
	}
	
	/**
	 * Welcomes the user
	 */
	public void printWelcome() {
		System.out.println();
		System.out.println("Welcome to the Theatre Royal!");
	}

	/**
	 * Returns an String containing the contents of a formatted ArrayList making it mid sentence friendly.
	 * @param occupiedSeats
	 * @return
	 */
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

	/**
	 * Takes a play argument, and collects information from the user to make a booking for it
	 * @param play
	 */
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
				basket.createBookings(play, stallsOrCircle, noOfSeats, seatNumbers, noOfConcessions, isPostal);
				basket.createConcessionaryBookings(noOfConcessions);
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

	/**
	 * Returns information collected from the user regarding what type of seats they want to book
	 * @return
	 */
	public int getSeatType() {
		System.out.println("What type of seat would you like to reserve? Please enter your selection.");
		System.out.println("1. Stalls" + '\n' + "2. Circle");
		return inputReader.getNextInt(1, 2) - 1;
	}

	/**
	 * Returns information collected from the user regarding how many seats they want to book
	 * @return
	 */
	public int getNoOfSeats() {
		System.out.println("How many seats would you like to reserve?");
		return inputReader.getNextInt();
	}

	/**
	 * Returns information collected from the user regarding what seat number they want to reserve from
	 * @param stallsOrCircle
	 * @param noOfSeats
	 * @return
	 */
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

	/**
	 * Prints to the console what seat numbers [if any] are occupied from the given list
	 * @param occupiedSeats
	 */
	public void areSeatsOccupied(ArrayList<Integer> occupiedSeats) {
		if (occupiedSeats.size() > 0) {
			if (occupiedSeats.size() == 1) {
				System.out.println("Sorry! Seat " + occupiedSeats.get(0) + " is taken.");
			} else if (occupiedSeats.size() > 1) {
				System.out.println("Sorry! Seats " + getOccupiedSeatNumbers(occupiedSeats) + " are taken.");
			}
		}
	}

	/**
	 * Returns an array of integers containing non-occupied seat numbers. The range starts at the user
	 * selected seat number, and ends at seatNumber + noOfSeats
	 * are 
	 * @param play
	 * @param stallsOrCircle
	 * @param seatNumber
	 * @param noOfSeats
	 * @return
	 */
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

	/**
	 * Returns information collected from the user regarding the number of tickets that are concessionary
	 * @param noOfSeats
	 * @return
	 */
	public int getNoOfConcessions(int noOfSeats) {
		System.out.println("Enter the number of concessions. OAP, Student, etc");
		return inputReader.getNextInt(0, noOfSeats);
	}

	/**
	 * Returns information collected from the user regarding whether they want the tickets to be posted,
	 * and whether they can be posted.
	 * @param play
	 * @return
	 */
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

	/**
	 * Returns information collected from the user regarding whether they want to add selected tickets to the basket
	 * @return
	 */
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



	/**
	 * Prints and returns an array list of Play objects with a property (name or date) matching an input string
	 * @param nameOrDate
	 * @param plays
	 * @return
	 */
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

	/**
	 * Presents the post name/date search menu screen to the user, allowing them to search again
	 * if so desired
	 * @param nameOrDate
	 * @param allPlays
	 */
	public void searchPrompt(String nameOrDate, ArrayList<Play> allPlays) {
		ArrayList<Play> results = searchByNameOrDate(nameOrDate, allPlays);
		if (results.size() == 0) {
			System.out.println("No performances found with that " + nameOrDate + ". Returning to Main Menu");
			return;
		}
		System.out.println("Select a play number, enter -1 to search again by " + nameOrDate
				+ ", or enter 0 to go to the previous screen");
		int selection = inputReader.getNextInt(-1, results.size());
		if (selection == -1) {
			searchByNameOrDate(nameOrDate, allPlays);
		} else if (selection == 0) {
			return;
		} else {
			Play play = results.get(selection - 1);
			play.printPlayDetails();
			bookingPrompt(play);
		}
	}
	
	/*
	 * Prompts the user to login or register, guiding them through the process
	 */
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

	/**
	 * Searches the Customer table for a row with given username and password, logs them in if the
	 * search returns a positive result, else returns them to the login screen
	 */
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

	/**
	 * Collects information from the user to build a Customer object and pushes it to the database
	 */
	public void registerCustomer() {
		System.out.println("Please enter your first name");
		String forename = inputReader.getInput();
		System.out.println("Please enter your second name");
		String surname = inputReader.getInput();
		System.out.println("Please enter your address");
		String address = inputReader.getInput();
		System.out.println("Please enter your telephone number");
		String telephone = inputReader.getInput();
		System.out.println("Please enter a valid email address");
		String email = "";
		while (!validateCustomerEmail(email)) {
			email = inputReader.getInput();
		}
		//validation
		System.out.println("Please enter a username, using a combination of letters, numbers, and -, . or _");
		String username = " ";
		while (!validateCustomerUsername(username)) {
			username = inputReader.getInput();
		}
		System.out.println("Please enter a password, using a combination of letters, numbers and symbols");
		String password = " ";
		while (!validateCustomerPassword(password)) {
			password = inputReader.getInput();
		}
		System.out.println("Please enter your credit card number");
		String paymentDetails = inputReader.getInput();
		Customer c = new Customer(username, password, forename, surname, address, telephone, email, paymentDetails);
		dbm.addCustomer(c);
	}

	/**
	 * Logs out all employees and customers at the current terminal
	 */
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

	/**
	 * Takes employee username and password, querying the Employee table for a matching row.
	 * If there is a positive result logs the employee in, else returns them to the employee menu
	 */
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

	/**
	 * Collects information from the user [employee] and uses it to make a Play object, which is then
	 * pushed to the database [Play table] 
	 */
	public void createPlay() {
		System.out.println("Please enter a title:");
		String title = inputReader.getInput();
		System.out.println("Please enter a type:" + '\n' + "1. Theatre" + '\n' + "2. Musical" + '\n' + "3. Opera" + '\n' + "4. Concert");
		int type = inputReader.getNextInt(1, 4) - 1;
		System.out.println("Please enter a description:");
		String description = inputReader.getInput();
		System.out.println("Please enter a time, using the 24hr format HH:MM:SS");
		String time = inputReader.getInput();
		System.out.println("Please enter a date, using the format YYYY-MM-DD");
		String date = inputReader.getInput();
		System.out.println("Please enter a duration, using the 24hr format HH:MM:SS");
		String duration = inputReader.getInput();
		System.out.println("Please enter a price for Stalls Seats as a whole number, in pence:");
		int stallsSeatPrice = inputReader.getNextInt();
		System.out.println("Please enter a price for Circle Seats as a whole number, in pence:");
		int circleSeatPrice = inputReader.getNextInt();
		System.out.println("Please enter a language:");
		String language = inputReader.getInput();
		System.out.println("Will the play have Musical Accompaniment?" + '\n' + "1. Yes" + '\n' + "2. No");
		int musicalAccompaniment = inputReader.getNextInt(1, 2)-1;
		Play p = new Play(title, type, description, time, date, duration, circleSeatPrice, stallsSeatPrice, language,
				musicalAccompaniment);
		dbm.addPlay(p);
		System.out.println("" + p.getPlayTitle() + " has been added to the database");
	}

	/**
	 * Guides the user [employee] through the process of removing a play from the database [Play table]
	 */
	public void removePlay() {
		ArrayList<Play> plays = dbm.constructArrayList(dbm.searchPlay(), callPlay());
		dbm.printBasic(plays, callPlay());
		System.out.println("What is the name of the play you would like to remove?");
		String searchTitle = inputReader.getInput();
		ArrayList<Play> results = new ArrayList<>();
		for (int i = 0; i < plays.size(); i++) {
			if (plays.get(i).getPlayTitle().equalsIgnoreCase(searchTitle) || plays.get(i).getPlayTitle().toLowerCase().contains(searchTitle.toLowerCase())) {
				results.add(plays.get(i));
			}
		}
		//if we don't find any plays with that name the function exits
		if (results.size() == 0) {
			System.out.println("No plays with that name found");
			return;
		}
		System.out.println("Select a play from the following list, or enter 0 to exit:");
		dbm.printBasic(results, callPlay());
		int playSelection = inputReader.getNextInt(0, results.size());
		
		Play play = results.get(playSelection - 1);
		play.printPlayDetails();
		System.out.println("" + '\n' + "Please select an option:" + '\n' + "1. Delete play" + '\n' + "2. Return to Employee Menu");
		int deleteSelectionChoice = inputReader.getNextInt(1, 2);
		switch (playSelection) {
		case 1:
			System.out.println("Are you sure you would like to delete this play?" + '\n' + "1. Yes" + '\n' + "2.No");
			int confirmSelection = inputReader.getNextInt(1, 2);
			switch (confirmSelection) {
			case 1:
				dbm.removeBookingsByPlayId(play);
				dbm.removePlay(play);
				System.out.println("Play deleted.");
			case 2:
				break;
			default: 
				break;
			}
		case 2:
			break;
		}
	}
	
	/**
	 * Takes the passed string, turns it to char array, iterates through it to see whether it contains any
	 * characters NOT found in the validUsernameChars string
	 * @param string
	 * @return
	 */
	public boolean validateCustomerUsername(String string) {
		char[] chars = string.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!validUsernameChars.contains("" + chars[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Takes the passed string, turns it to char array, iterates through it to see whether it contains any
	 * characters NOT found in the validPasswordChars string
	 * @param string
	 * @return
	 */
	public boolean validateCustomerPassword(String string) {
		char[] chars = string.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (!validPasswordChars.contains("" + chars[i])) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Takes a string and returns whether it contains an "@" and a ".", which is primitive email validation.
	 * @param string
	 * @return
	 */
	public boolean validateCustomerEmail(String string) {
		return string.contains("@") && string.contains(".");
	}
	
	/**
	 * Fetches a MainPerformers object using a playId and prints the contents
	 */
	public void fetchAndPrintMainPerformers(Play play) {
		MainPerformers mainPerformers = dbm.fetchMainPerformers(dbm.searchMainPerformersByPlayId(play.getPlayId()));
		if (mainPerformers != null) {
			mainPerformers.printMainPerformers();
		}
	}

	/**
	 * Helper method to use with Generic methods in the dbm class, returns an empty Play object
	 * @return
	 */
	public Play callPlay() {
		Play play = new Play();
		return play;
	}
	
	/**
	 * Helper method to use with Generic methods in the dbm class, returns an empty Booking object
	 * @return
	 */
	public Booking callBooking() {
		Booking booking = new Booking();
		return booking;
	}
	
	/**
	 * Helper method to use with Generic methods in the dbm class, returns an empty Customer object
	 * @return
	 */
	public Customer callCustomer() {
		Customer customer = new Customer();
		return customer;
	}
	
	/**
	 * Helper method to use with Generic methods in the dbm class, returns an empty Employee object
	 * @return
	 */
	public Employee callEmployee() {
		Employee employee = new Employee();
		return employee;
	}
}