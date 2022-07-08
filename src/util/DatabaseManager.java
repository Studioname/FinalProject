package util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Booking;
import model.Customer;
import model.Play;

public class DatabaseManager {
	private Connection conn;
	
//----------------------------------------------------------------------
	//general methods
//----------------------------------------------------------------------
	public DatabaseManager() {
		conn = null;
	}

	public void connect(String dbName, String url) {

		try {
			Scanner s = new Scanner(new File("credentials.txt"));
			String uname = s.nextLine().trim();
			String pword = s.nextLine().trim();
			conn = DriverManager.getConnection(url + dbName, uname, pword);
		}
		catch (IOException e) {
			System.out.println("File error");
			e.printStackTrace();}
		catch (SQLException e) {
			System.out.println("Connection failed.");
			e.printStackTrace();
			return;
		}

		if (conn != null) {
			System.out.println("Connection established.");
		} else {
			System.out.println("Connection null still.");
		}
	}

	/*
	 * 4. Prepare a query statement to run - DONE :) 5. Execute query - DONE
	 */

	public ResultSet runQuery(String sql) {
		if (sql.equals("")) {
			return null;
		}
		PreparedStatement pst = null;
		try {
			conn.setAutoCommit(true);
			pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, // allows us to move forward and back in
																				// the ResultSet
					ResultSet.CONCUR_UPDATABLE);
			pst.execute();
			ResultSet results = pst.getResultSet();
			if (results != null) {
				int rowcount = 0;
				if (results.last()) {
					rowcount = results.getRow();
					results.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first
											// element
				}
				System.out.println(sql + "\n Success.  Result set has " + rowcount + " rows");
			} else {
				System.out.println(sql + "\n Success.  No results returned");
			}
			return results;
		} catch (SQLException e) {
			System.out.println(sql + "\n failed to run.");
			e.printStackTrace();
			return null;
		}

	}
	
	public void printQueryResult(ResultSet rs) {
		try {
			if (rs != null) {
				int rowcount = 0;
				if (rs.last()) {
					rowcount = rs.getRow();
					rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first
											// element
				}
				System.out.println("\n Success.  Result set has " + rowcount + " rows");
			} else {
				System.out.println("\n Success.  No results returned");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 5. Process Results
	
	public ArrayList<String> getColumnNames(ResultSet rs) {
		try {
			ResultSetMetaData rsMetaData = rs.getMetaData();
			ArrayList<String> names = new ArrayList<String>();
			// while there is another row
			int count = rsMetaData.getColumnCount();
		    for(int i = 1; i <= count; i++) {
		    	names.add(rsMetaData.getColumnName(i));
		    } 
		    return names;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void printColumnNames(ArrayList<String> names) {
			for (int i = 0; i > names.size(); i++) {
				//we can edit the column names to make them look nicer
				System.out.println(names.get(i));
		} 
	}	      

	public void close() {
		try {
			conn.close();
			System.out.println("Connection closed.");
		} catch (SQLException e) {
			System.out.println("Connection not closed.");
			e.printStackTrace();
		}
	}
	
	//input validation - prevent duplicates from being entered, or entries with null value


//----------------------------------------------------------------------
	//play methods
//----------------------------------------------------------------------
	
	public Play fetchPlayObject(ResultSet rs) {
		try {
			int playId = rs.getInt("PlayId");
			String playTitle = rs.getString("PlayTitle");
			int playType = rs.getInt("PlayType");
			String playDescription = rs.getString("PlayDescription");
			String playTime = rs.getString("PlayTime");
			String playDate = rs.getString("PlayDate");
			String playDuration = rs.getString("PlayDuration");
			int playCirclePrice = rs.getInt("PlayCirclePrice");
			int playStallsPrice = rs.getInt("PlayStallsPrice");
			String playLanguage = rs.getString("PlayLanguage");
			int playMusicalAccompaniment = rs.getInt("PlayMusicalAccompaniment");
			Play p = new Play(playId, playTitle, playType, playDescription, playTime, playDate, playDuration, playCirclePrice, playStallsPrice, playLanguage, playMusicalAccompaniment);
			return p;
			}
			catch (SQLException e) {
				e.printStackTrace();
				return null;
		}
	}

	public ArrayList<Play> constructPlayArrayList() {
		ResultSet rs = searchPlay();
		ArrayList<Play> results = new ArrayList<>();
		try {
			while (rs.next()) {
				results.add(fetchPlayObject(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<Play> constructPlayArrayList(ResultSet rs) {
		ArrayList<Play> results = new ArrayList<>();
		try {
			while (rs.next()) {
				results.add(fetchPlayObject(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public void printPlayDetails(Play play) {
		play.printPlayDetails();
	}
	
	public void printPlays(ArrayList<Play> plays) {
		for (int i = 0; i < plays.size(); i++) {
			plays.get(i).printPlayDetails();
		}
	}
	
	public void printPlaysBasic(ArrayList<Play> plays) {
		for (int i = 0; i < plays.size(); i++) {
			plays.get(i).printBasicPlayDetails();
		}
	}

//----------------------------------------------------------------------
	//play java queries
	
	public ArrayList<Play> searchPlayByDate(ArrayList<Play> plays, String date) {
		ArrayList<Play> result = new ArrayList<Play>();
		for (int i = 0; i < plays.size(); i++) {
			if (plays.get(i).getPlayDate().equals(date)){
				result.add(plays.get(i));
			}
		}
		return result;
	}
	
	public ArrayList<Play> searchPlayByTitle(ArrayList<Play> plays, String name) {
		ArrayList<Play> result = new ArrayList<Play>();
		for (int i = 0; i < plays.size(); i++) {
			if (plays.get(i).getPlayTitle().equals(name)){
				result.add(plays.get(i));
			}
		}
		return result;
	}
	
	public Play searchPlayById(ArrayList<Play> plays, int id) {
		ArrayList<Play> result = new ArrayList<Play>();
		for (int i = 0; i < plays.size(); i++) {
			if (plays.get(i).getPlayId() == id){
				return plays.get(i);
			}
		}
		return null;
	}

//----------------------------------------------------------------------
	//booking methods
//----------------------------------------------------------------------
	/**
	 * Takes a ResultSet object and creates a Booking Object from it
	 * @param ResultSet rs - Gotten from searchBooking();
	 * @return returns a booking object
	 */
	public Booking fetchBookingObject(ResultSet rs) {
		try {
			int bookingId = rs.getInt("BookingId");
			int playId = rs.getInt("playId");
			int customerId = rs.getInt("CustomerId");
			int seatType = rs.getInt("SeatType");
			int seatNumber = rs.getInt("SeatNumber");
			int concession = rs.getInt("Concession");
			int isPostal = rs.getInt("isPostal");
			
			Booking b = new Booking(bookingId, playId, customerId, seatType, seatNumber, concession, isPostal);
			//b.setCustomer(fetchCustomerObject(getCustomerById(customerId)));
			return b;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Creates an ArrayList of Booking objects and returns it. We can perform Java operations on this full list.
	 * @return
	 */
	public ArrayList<Booking> constructBookingArrayList() {
		ResultSet rs = searchBooking();
		ArrayList<Booking> results = new ArrayList<>();
		try {
			while (rs.next()) {
				results.add(fetchBookingObject(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<Booking> constructBookingArrayList(ResultSet rs) {
		ArrayList<Booking> results = new ArrayList<>();
		try {
			while (rs.next()) {
				results.add(fetchBookingObject(rs));
			}
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public void printBookingDetails(Booking booking) {
		booking.printBookingDetails();
	}
	
	public void printBookings(ArrayList<Booking> bookings) {
		for (int i = 0; i < bookings.size(); i++) {
			bookings.get(i).printBookingDetails();
		}
	}
	
	public void printBookingsBasic(ArrayList<Booking> bookings) {
		for (int i = 0; i < bookings.size(); i++) {
			bookings.get(i).printBasicBookingDetails(i);
		}
	}

//----------------------------------------------------------------------
	//booking java queries

	public Booking searchBookingById(ArrayList<Booking> bookings, int id) {
		ArrayList<Booking> result = new ArrayList<Booking>();
		for (int i = 0; i < bookings.size(); i++) {
			if (bookings.get(i).getBookingId() == id){
				return bookings.get(i);
			}
		}
		return null;
	}

//----------------------------------------------------------------------
	//Customer methods
//----------------------------------------------------------------------
	
	/**
	 * Takes a ResultSet object and creates a Customer Object from it
	 * @param ResultSet rs - Gotten from searchCustomer();
	 * @return returns a customer object
	 */
	public Customer fetchCustomerObject(ResultSet rs) {
		try {
			int customerId = rs.getInt("CustomerId");
			String customerForename = rs.getString("CustomerForename");
			String customerSurname = rs.getString("CustomerSurname");
			String customerAddress = rs.getString("CustomerAddress");
			String customerTelephone = rs.getString("CustomerTelephone");
			String customerEmail = rs.getString("CustomerEmail");
			String customerPaymentDetails = rs.getString("CustomerPaymentDetails");
			
			Customer c = new Customer(customerId, customerForename, customerSurname, customerAddress, customerTelephone, customerEmail, customerPaymentDetails);
			return c;
			}
			catch (SQLException e) {
				e.printStackTrace();
				return null;
		}
	}
	
	/**
	 * Creates an ArrayList of Customer objects and returns it. We can perform Java operations on this full list.
	 * @return
	 */
	public ArrayList<Customer> constructCustomerArrayList() {
		ResultSet rs = searchCustomer();
		ArrayList<Customer> results = new ArrayList<>();
		try {
			while (rs.next()) {
				results.add(fetchCustomerObject(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public ArrayList<Customer> constructCustomerArrayList(ResultSet rs) {
		ArrayList<Customer> results = new ArrayList<>();
		try {
			while (rs.next()) {
				results.add(fetchCustomerObject(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public void printCustomerDetails(Customer customer) {
		customer.printCustomerDetails();
	}
	
	public void printCustomerArrayList(ArrayList<Customer> customers) {
		for (int i = 0; i < customers.size(); i++) {
			customers.get(i).printCustomerDetails();
		}
	}
	
	public void printCustomerArrayListBasic(ArrayList<Customer> customers) {
		for (int i = 0; i < customers.size(); i++) {
			customers.get(i).printBasicCustomerDetails(i);
		}
	}

//----------------------------------------------------------------------
	//customer java queries

	public Customer searchCustomerById(ArrayList<Customer> customers, int id) {
		ArrayList<Customer> result = new ArrayList<Customer>();
		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getCustomerId() == id){
				return customers.get(i);
			}
		}
		return null;
	}
	
//----------------------------------------------------------------------
	//SQL queries
//----------------------------------------------------------------------
	//Play
//----------------------------------------------------------------------
	public boolean addPlay(Play play) {

		//validation
		String test = "SELECT * FROM Play WHERE PlayType = " + play.getPlayType() + " AND PlayTitle LIKE '" + play.getPlayTitle() + "' AND PlayTime = '" + play.getPlayTime() + "' AND PlayDate = '" + play.getPlayDate() + "' AND PlayDuration = '" + play.getPlayDuration() + "' AND PlayLanguage LIKE '" + play.getPlayLanguage() + "' AND PlayMusicalAccompaniment = " + play.getPlayMusicalAccompaniment() + ";";
		ResultSet rs = runQuery(test);
		try {
		if (!rs.next()) {
			String str = "INSERT INTO Play (PlayType, PlayTitle, PlayDescription, PlayTime, PlayDate, PlayDuration, PlayCirclePrice, PlayStallsPrice, PlayLanguage, PlayMusicalAccompaniment) VALUES (" + play.getPlayType() + ", '" + play.getPlayTitle() + "', '" + play.getPlayDescription() + "', '" + play.getPlayTime() + "', '" + play.getPlayDate() + "', '" + play.getPlayDuration() + "', " + play.getPlayCirclePrice() + ", " + play.getPlayStallsPrice() + ", '" + play.getPlayLanguage() + "', " + play.getPlayMusicalAccompaniment() + ");";
			runQuery(str);
			return true;
			
		}
		System.out.println("Duplicate Play found. Item has not been added to the database");
		return false;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ResultSet searchPlay() {
		String str = "SELECT * FROM Play;";
		return runQuery(str);
	}

//----------------------------------------------------------------------
	//Booking
//----------------------------------------------------------------------
	public ResultSet searchBooking() {
		String str = "SELECT * FROM Booking;";
		return runQuery(str);
	}
	
	public ResultSet getBookingById(int id) {
		String str = "SELECT * FROM Booking WHERE BookingId = " + id + ";";
		return runQuery(str);
	}
	
	/*
	 * Invokes seatIsFree() to check whether the seat is free [also precludes identical booking.] 
	 * If seatIsFree returns true, adds booking to the database and returns true. Else returns false.
	 */
	public boolean addBooking(Booking booking) {
		
		if (seatIsFree(booking.getPlayId(), booking.getSeatType(), booking.getSeatNumber())) {
			String str = "INSERT INTO Booking (PlayId, CustomerId, SeatType, SeatNumber, Concession, IsPostal) VALUES (" + booking.getPlayId() + ", " + booking.getCustomerId() + ", " + booking.getSeatType() + ", " + booking.getSeatNumber() + ", " + booking.getConcession() + ", " + booking.getIsPostal() + ");";
			runQuery(str);
			return true;
		}
		System.out.println("Duplicate booking found. Item has not been added to the database");
		return false;
	}
	
//----------------------------------------------------------------------
	//Customer
//----------------------------------------------------------------------
	
	public ResultSet searchCustomer() {
		String str = "SELECT * FROM Customer;";
		return runQuery(str);
	}
	
	public boolean addCustomer(Customer customer) {
		String test = "SELECT * FROM Customer WHERE CustomerForename LIKE '" + customer.getCustomerForename()+ "' AND CustomerSurname LIKE '" + customer.getCustomerSurname() + "' AND CustomerAddress LIKE '" + customer.getCustomerAddress() + "' AND CustomerTelephone LIKE '" + customer.getCustomerTelephone() + "';";
		ResultSet rs = runQuery(test);
		try {
			if (!rs.next()) {
				String str = "INSERT INTO Customer (CustomerForename, CustomerSurname, CustomerAddress, CustomerTelephone, CustomerEmail, CustomerPaymentDetails) VALUES ('" + customer.getCustomerForename() + "', '" + customer.getCustomerSurname() + "', '"  + customer.getCustomerAddress() + "', '" + customer.getCustomerTelephone() + "', '" +  customer.getCustomerEmail() + "', '" + customer.getCustomerPaymentDetails() + "');";
				runQuery(str);
				return true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Duplicate Customer found. Item has not been added to the database");
		return false;
	}
	
	public ResultSet getCustomerById(int id) {
		String str = "SELECT * FROM Customer WHERE CustomerId = " + id + ";";
		return runQuery(str);
	}
	
//----------------------------------------------------------------------
	//General
//----------------------------------------------------------------------
	
	/**
	 * Returns a boolean whether the seatType and seatNumber for given showId is free or booked - true for free, false for booked
	 * @param showId
	 * @param seatType
	 * @param seatNumber
	 * @return
	 */
	public boolean seatIsFree(int playId, int seatType, int seatNumber) {
		String str = "SELECT * FROM Booking WHERE PlayId = " + playId + " AND SeatType = " + seatType + " AND SeatNumber = " + seatNumber + ";";
		ResultSet rs = runQuery(str);
		try {
			return !rs.next();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * Represents a custom search of the database. Takes an ArrayList of column names derived from meta data, 
	 * the user input for which column name to search by, and the String value to search for.
	 */
	public ArrayList<Play> customSearch(ArrayList<String> columnNames, int userSelection, String value){
		printColumnNames(columnNames);
		//record int input from user
		String str = "SELECT * FROM Play WHERE " + columnNames.get(userSelection) + " LIKE '" + value + "';";
		ResultSet rs2 = runQuery(str);
		
		return constructPlayArrayList(rs2);
	}
	
	/*
	 * Represents a custom search of the database. Takes an ArrayList of column names derived from meta data, 
	 * the user input for which column name to search by, and the int value to search for.
	 */
	public ArrayList<Play> customSearch(ArrayList<String> columnNames, int userSelection, int value){
		printColumnNames(columnNames);
		//record int input from user
		String str = "SELECT * FROM Play WHERE " + columnNames.get(userSelection) + " = '" + value + "';";
		ResultSet rs2 = runQuery(str);
		return constructPlayArrayList(rs2);
		}
	}
//date time filtering

//SELECT * 
//FROM MyTable 
//WHERE CheckDate >= '2009-10-01' AND CheckDate < '2010-01-01';


//contact details

//looking to collect a range of data regarding ticket buying habits and perhaps with a view to introducing more theatregoers
//change ticket
//group booking discount possible
//change shows
//users can delete data

//Runnable r = () -> setNum(1);
//r.run();