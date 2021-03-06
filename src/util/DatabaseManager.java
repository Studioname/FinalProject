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
import model.Employee;
import model.MainPerformers;
import model.Play;

/**
 * Represents the Database Manager class, managing interactions between the program and the SQL database
 * @author Conan
 *
 */

public class DatabaseManager {
	private Connection conn;
	
//----------------------------------------------------------------------
	//dbm methods
//----------------------------------------------------------------------
	/**
	 * Constructor for the DatabaseManager class
	 */
	public DatabaseManager() {
		conn = null;
	}
	
	/**
	 * Connects class to given dbName at given url. Login credentials are located in credentials.txt
	 * @param dbName
	 * @param url
	 */
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

	/**
	 * Returns a ResultSet from executing a prepared statement
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
//			if (results != null) {
//				int rowcount = 0;
//				if (results.last()) {
//					rowcount = results.getRow();
//					results.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first
//											// element
//				}
//				System.out.println(sql + "\n Success.  Result set has " + rowcount + " rows");
//			} else {
//				System.out.println(sql + "\n Success.  No results returned");
//			}
			return results;
		} catch (SQLException e) {
			System.out.println(sql + "\n failed to run.");
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * Returns an ArrayList of Strings containing the column names from given ResultSet
	 * @param rs
	 * @return
	 */
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
	
	//not sure if this is needed
	/**
	 * Iterates through the given ArrayList and prints the contents
	 * @param names
	 */
	public void printColumnNames(ArrayList<String> names) {
			for (int i = 0; i > names.size(); i++) {
				//we can edit the column names to make them look nicer
				System.out.println(names.get(i));
		} 
	}	      
	
	/**
	 * Closes connection
	 */
	public void close() {
		try {
			conn.close();
			System.out.println("Connection closed.");
		} catch (SQLException e) {
			System.out.println("Connection not closed.");
			e.printStackTrace();
		}
	}
	
//----------------------------------------------------------------------
	//generic methods
//----------------------------------------------------------------------
	
	//generic methods allow different object types to be passed to and returned from them. They can
	//be given different behaviour depending on which objects are passed.
	
	//they are included for ease of use, and will allow people to call a single function 
	//instead of many different ones
	
	//to use these functions, simply pass the resultset from your query and callClass() ie callPlay(),
	//where Class is the table the resultset is from
	
	//i have included the callBooking, callPlay, callCustomer methods in the controller class
	//because that is the class that will be invoking them
	
	/**
	 * Takes a member of Object class, returns generic ArrayList
	 * The type of ArrayList is returns is determined by the type of the object passed to it, 
	 * namely callPlay(), callCustomer() etc. 
	 * @param <E>
	 * @param rs
	 * @param object
	 * @return
	 */
	public <E> ArrayList<E> constructArrayList(ResultSet rs, Object object) {
		if (object instanceof Play) {
			ArrayList<Play> r = new ArrayList<Play>();
			try {
				while (rs.next()) {
					r.add(fetchPlayObject(rs));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return (ArrayList<E>) r;
		}

		else if (object instanceof Booking) {
			ArrayList<Booking> r2 = new ArrayList<Booking>();
			try {
				while (rs.next()) {
					r2.add(fetchBookingObject(rs));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return (ArrayList<E>) r2;
		} else if (object instanceof Customer) {
			ArrayList<Customer> r3 = new ArrayList<Customer>();
			try {
				while (rs.next()) {
					r3.add(fetchCustomerObject(rs));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return (ArrayList<E>) r3;
		} 
		else if (object instanceof Employee) {
            ArrayList<Employee> r4 = new ArrayList<Employee>();
            try {
                while (rs.next()) {
                    r4.add(fetchEmployeeObject(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return (ArrayList<E>) r4;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Takes a generic ArrayList and an Object, and prints ArrayList content depending on the type
	 * of object passed to it
	 */
	public < E > void printBasic(ArrayList< E > arrayList, Object object) {
		if (object instanceof Play) {
			for (int i = 0; i < arrayList.size(); i++) {
				((Play) arrayList.get(i)).printBasicPlayDetails(i);
			}
		}
		else if (object instanceof Booking) {
			for (int i = 0; i < arrayList.size(); i++) {
				((Booking) arrayList.get(i)).printBasicBookingDetails(i);
			}
		}
		else if (object instanceof Customer) {
			for (int i = 0; i < arrayList.size(); i++) {
				((Customer) arrayList.get(i)).printBasicCustomerDetails(i);
			}
		}
		else if (object instanceof Employee) {
            for (int i = 0; i < arrayList.size(); i++) {
                ((Employee) arrayList.get(i)).printBasicEmployeeDetails(i);
            }
        }
	}

//----------------------------------------------------------------------
	//play methods
//----------------------------------------------------------------------
	
	/**
	 * Creates a Play object from a ResultSet object [database query result]
	 * @param rs
	 * @return
	 */
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
	
	/**
	 * Takes an ArrayList of Plays and a date string. Iterates through the passed ArrayList
	 * and adds any Play objects with a matching date to a new ArrayList, which is then returned
	 * @param plays
	 * @param date
	 * @return
	 */
	public ArrayList<Play> searchPlayByDate(ArrayList<Play> plays, String date) {
		ArrayList<Play> result = new ArrayList<Play>();
		for (int i = 0; i < plays.size(); i++) {
			if (plays.get(i).getPlayDate().equals(date)){
				result.add(plays.get(i));
			}
		}
		return result;
	}
	
	/**
	 * Takes an ArrayList of Plays and a string. Iterates through the passed ArrayList
	 * and adds any Play objects with a matching name to a new ArrayList, which is then returned
	 * @param plays
	 * @param name
	 * @return
	 */
	public ArrayList<Play> searchPlayByTitle(ArrayList<Play> plays, String name) {
		ArrayList<Play> result = new ArrayList<Play>();
		for (int i = 0; i < plays.size(); i++) {
			if (plays.get(i).getPlayTitle().equalsIgnoreCase(name)){
				result.add(plays.get(i));
			}
		}
		return result;
	}
	
	/**
	 * 	 * Takes an ArrayList of Plays and a play Id. Iterates through the passed ArrayList
	 * and returns the first Play objects with a matching playId
	 * @param plays
	 * @param id
	 * @return
	 */
	public Play searchPlayById(ArrayList<Play> plays, int id) {
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
	 * Takes a ResultSet object and creates a Booking object from it
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
			return b;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

//----------------------------------------------------------------------
	//booking java queries

	/**
	 * Takes an ArrayList of booking objects and an integer, and iterates through the ArrayList,
	 * returning the first Booking object with a matching bookingId
	 * @param bookings
	 * @param id
	 * @return
	 */
	public Booking searchBookingById(ArrayList<Booking> bookings, int bookingId) {
		for (int i = 0; i < bookings.size(); i++) {
			if (bookings.get(i).getBookingId() == bookingId){
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
			String customerUsername = rs.getString("CustomerUsername");
			String customerPassword = rs.getString("CustomerPassword");
			String customerForename = rs.getString("CustomerForename");
			String customerSurname = rs.getString("CustomerSurname");
			String customerAddress = rs.getString("CustomerAddress");
			String customerTelephone = rs.getString("CustomerTelephone");
			String customerEmail = rs.getString("CustomerEmail");
			String customerPaymentDetails = rs.getString("CustomerPaymentDetails");
			Customer c = new Customer(customerId, customerUsername, customerPassword, customerForename, customerSurname, customerAddress, customerTelephone, customerEmail, customerPaymentDetails);
			return c;
			}
			catch (SQLException e) {
				e.printStackTrace();
				return null;
		}
	}
	
//----------------------------------------------------------------------
	//customer java queries

	/**
	 * Takes an ArrayList of Customer objects and an integer, iterates through the ArrayList and returns
	 * the first Customer object with a matching customerId
	 * @param customers
	 * @param id
	 * @return
	 */
	public Customer searchCustomerById(ArrayList<Customer> customers, int id) {
		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getCustomerId() == id){
				return customers.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Returns a ResultSet object from a database query looking for Customer entries with matching credentials
	 * @param username
	 * @param password
	 * @return
	 */
	public ResultSet searchCustomerByLoginDetails(String username, String password) {
		String str = "SELECT * FROM Customer WHERE CustomerUsername LIKE '" + username + "' AND CustomerPassword LIKE '" + password + "';";
		ResultSet rs = runQuery(str);
		try {
			rs.first();
			}
		catch (SQLException e) {
			e.printStackTrace();
		};
		return rs;
	}
	
	/**
	 * Returns true or false depending on whether there is an entry in the Customer table with a matching
	 * username and password string
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean validateCredentials(String username, String password) {
		ResultSet rs = searchCustomerByLoginDetails(username,password);
		try {
			if (rs == null || !rs.first()) {
				return false;
			}
			else {
				return true;
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

//----------------------------------------------------------------------
	//Employee
//----------------------------------------------------------------------
	
	
    /**
     * Takes a ResultSet object and creates a Employee Object from it
     * @param ResultSet rs - Gotten from searchEmployee();
     * @return returns a Employee object
     **/

    public Employee fetchEmployeeObject(ResultSet rs) {
        try {
            int EmployeeId = rs.getInt("EmployeeId");
            String EmployeeUsername = rs.getString("EmployeeUsername");
            String EmployeePassword = rs.getString("EmployeePassword");
            Employee c = new Employee(EmployeeId, EmployeeUsername, EmployeePassword);
            return c;
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
        }
    }
    
    /**
     * Returns true if there is an entry in the Employee table with matching username and password, false if not
     * @param username
     * @param password
     * @return
     */
    public boolean validateEmployeeCredentials(String username, String password) {
        ResultSet rs = searchEmployeeByLoginDetails(username,password);
        try {
            if (rs == null || !rs.first()) {
                return false;
            }
            else {
                return true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
	
//----------------------------------------------------------------------
	//Main performers
//----------------------------------------------------------------------
    /**
     * Returns a MainPerformers object from a ResultSet
     * @param rs
     * @return
     */
    public MainPerformers fetchMainPerformers(ResultSet rs) {
    	
        MainPerformers mainPerformers = new MainPerformers();
    	try {
    		if (rs == null || !rs.first()) {
				return null;
			}
            int playId = rs.getInt("PlayId");
            mainPerformers.setPlayId(playId);
            rs.beforeFirst();
        	while (rs.next()) {
	            String mainPerformer = rs.getString("MainPerformer"); 
	            mainPerformers.addMainPerformer(mainPerformer);
        	}
        	return mainPerformers;
    	}
	            catch (SQLException e) {
	                e.printStackTrace();
	                return null;
            }
        }
	
//----------------------------------------------------------------------
	//SQL queries
//----------------------------------------------------------------------
	//Play
//----------------------------------------------------------------------
	/**
	 * Adds a play object to the Play table in the database 
	 * @param play
	 * @return
	 */
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
		//System.out.println("Duplicate Play found. Item has not been added to the database");
		return false;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Removes entry from the Play table with matching playId
	 * @param play
	 * @return
	 */
	public ResultSet removePlay(Play play) {
		String str = "DELETE FROM Play WHERE PlayId = " + play.getPlayId()+";";
		return runQuery(str);
	}
	
	/**
	 * Removes all bookings from the Booking table which have the same playId as given play object
	 * @param play
	 * @return
	 */
	public ResultSet removeBookingsByPlayId(Play play) {
		String str = "DELETE FROM Booking WHERE PlayId =" + play.getPlayId() + ";";
		return runQuery(str);
	}
	
	/**
	 * Returns a ResultSet object containing all plays from the Play table which have not yet been performed,
	 * ie, which start at a date later or equal to the current date
	 * @return
	 */
	public ResultSet searchPlay() {
		String str = "SELECT * FROM Play WHERE PlayDate >= CURRENT_DATE;";
		return runQuery(str);
	}
	
	/**
	 * Returns a ResultSet object containing all plays from the Play table
	 * @return
	 */
	public ResultSet searchAllPlay() {
		String str = "SELECT * FROM Play;";
		return runQuery(str);
	}

//----------------------------------------------------------------------
	//Booking
//----------------------------------------------------------------------
	
	/**
	 * Returns a ResultSet object containing all Bookings
	 * @return
	 */
	public ResultSet searchBooking() {
		String str = "SELECT * FROM Booking;";
		return runQuery(str);
	}
	
	/**
	 * Returns a ResultSet object from the Booking table which shares the same BookingId as the given parameter
	 * @param id
	 * @return
	 */
	public ResultSet getBookingById(int id) {
		String str = "SELECT * FROM Booking WHERE BookingId = " + id + ";";
		return runQuery(str);
	}
	
	/**
	 * Invokes seatIsFree() to check whether the seat is free [also precludes double booking.] 
	 * If seatIsFree returns true, adds booking to the database and returns true. Else returns false.
	 * @param booking
	 * @return
	 */
	public boolean addBooking(Booking booking) {
		
		if (isSeatFree(booking.getPlayId(), booking.getSeatType(), booking.getSeatNumber())) {
			String str = "INSERT INTO Booking (PlayId, CustomerId, SeatType, SeatNumber, Concession, IsPostal) VALUES (" + booking.getPlayId() + ", " + booking.getCustomerId() + ", " + booking.getSeatType() + ", " + booking.getSeatNumber() + ", " + booking.getConcession() + ", " + booking.getIsPostal() + ");";
			runQuery(str);
			return true;
		}
		//System.out.println("Duplicate booking found. Item has not been added to the database");
		return false;
	}
	
//----------------------------------------------------------------------
	//Customer
//----------------------------------------------------------------------
	
	/**
	 * Returns a ResultSet object containing all entries from the Customer table
	 * @return
	 */
	public ResultSet searchCustomer() {
		String str = "SELECT * FROM Customer;";
		return runQuery(str);
	}
	
	/**
	 * Takes a Customer object and pushes it to the Customer table in the database. Returns true if successful, false if not
	 * @param customer
	 * @return
	 */
	public boolean addCustomer(Customer customer) {
		String test = "SELECT * FROM Customer WHERE CustomerForename LIKE '" + customer.getCustomerForename()+ "' AND CustomerSurname LIKE '" + customer.getCustomerSurname() + "' AND CustomerAddress LIKE '" + customer.getCustomerAddress() + "' AND CustomerTelephone LIKE '" + customer.getCustomerTelephone() + "';";
		ResultSet rs = runQuery(test);
		try {
			if (!rs.next()) {
				String str = "INSERT INTO Customer (CustomerUsername, CustomerPassword, CustomerForename, CustomerSurname, CustomerAddress, CustomerTelephone, CustomerEmail, CustomerPaymentDetails) VALUES ('" + customer.getCustomerUsername() + "', '" + customer.getCustomerPassword() + "', '" + customer.getCustomerForename() + "', '" + customer.getCustomerSurname() + "', '"  + customer.getCustomerAddress() + "', '" + customer.getCustomerTelephone() + "', '" +  customer.getCustomerEmail() + "', '" + customer.getCustomerPaymentDetails() + "');";
				runQuery(str);
				return true;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		//System.out.println("Duplicate Customer found. Item has not been added to the database");
		return false;
	}
	
	/**
	 * Returns a ResultSet object from the Customer table for the customer entry with a customerId matching the given parameter
	 * @param id
	 * @return
	 */
	public ResultSet getCustomerById(int id) {
		String str = "SELECT * FROM Customer WHERE CustomerId = " + id + ";";
		return runQuery(str);
	}
	
//----------------------------------------------------------------------
	//Employee
//----------------------------------------------------------------------
    
    /**
     * Returns a ResultSet containing all entries from the Employee table
     * @return
     */
    public ResultSet searchEmployee() {
        String str = "SELECT * FROM Employee;";
        return runQuery(str);
    }

    /**
     * Takes an Employee object and adds it to the Employee table
     * @param employee
     * @return
     */
    public boolean addEmployee(Employee employee) {
        String test = "SELECT * FROM Employee WHERE EmployeeUsername LIKE '" + employee.getEmployeeUsername() + "' AND EmployeePassword LIKE '" + employee.getEmployeePassword( )+ "';";
        ResultSet rs = runQuery(test);
        try {
            if (!rs.next()) {
                String str = "INSERT INTO Employee (EmployeeUsername, EmployeePassword) VALUES ('" + employee.getEmployeeUsername() + "', '" + employee.getEmployeePassword() + "');";
                runQuery(str);
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println("Duplicate Employee found. Item has not been added to the database");
        return false;
    }

    /**
     * Returns the ResultSet object from the Employee table with a matching employeeId 
     * @param id
     * @return
     */
    public ResultSet getEmployeeById(int id) {
        String str = "SELECT * FROM Employee WHERE EmployeeId = " + id + ";";
        return runQuery(str);
    }
    
    /**
     * Returns a ResultSet object containing the entry in Employee table with matching username and password
     * @param username
     * @param password
     * @return
     */
    public ResultSet searchEmployeeByLoginDetails(String username, String password) {
        String str = "SELECT * FROM Employee WHERE EmployeeUsername LIKE '" + username + "' AND EmployeePassword LIKE '" + password + "';";
        ResultSet rs = runQuery(str);
        try {
            rs.first();
            }
        catch (SQLException e) {
            e.printStackTrace();
        };
        return rs;
    }
    
//----------------------------------------------------------------------
    //MainPerformers
//----------------------------------------------------------------------
    
    public ResultSet searchMainPerformersByPlayId(int playId) {
    	String str = "SELECT * FROM MainPerformer WHERE PlayId = " + playId + ";";
    	ResultSet rs = runQuery(str);
        try {
            rs.first();
            }
        catch (SQLException e) {
            e.printStackTrace();
        };
        return rs;
    }
    
    /**
     * Adds a Main Performer to the MainPerformer table
     * @param playId
     * @param mainPerformer
     * @return
     */
    
    public boolean addPerformer(int playId, String mainPerformer) {
        String test = "SELECT * FROM MainPerformer WHERE PlayId = " + playId + " AND MainPerformer LIKE '" + mainPerformer + "';";
        ResultSet rs = runQuery(test);
        try {
            if (!rs.next()) {
	    	String str = "INSERT INTO MainPerformer (PlayId, MainPerformer) VALUES (" + playId + ", '" +  mainPerformer + "');";
	    	runQuery(str);
	    	return true;
            }
        }
        catch (SQLException e) {
        	e.printStackTrace();
        	return false;
        }
        //System.out.println("Duplicate Main Performer found. Item has not been added to the database");
        return false;
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
	public boolean isSeatFree(int playId, int seatType, int seatNumber) {
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
	
	/**
	 * Returns an ArrayList of wrapped integers containing the numbers of any occupied seats of a given seat type
	 * for a given play, counting upwards from seatNumber to seatNumber + noOfSeats
	 * @param playId
	 * @param seatType
	 * @param seatNumber
	 * @param noOfSeats
	 * @return
	 */
	public ArrayList<Integer> getOccupiedSeats(int playId, int seatType, int seatNumber, int noOfSeats) {
		ArrayList<Integer> occupiedSeats = new ArrayList<>();
		
		for (int i = seatNumber; i < seatNumber + noOfSeats; i++) {
			if (!isSeatFree(playId, seatType, seatNumber)) {
				occupiedSeats.add(i);
			}
		}
		return occupiedSeats;
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
		Play play = new Play();
		return constructArrayList(rs2, play);
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
		Play play = new Play();
		return constructArrayList(rs2, play);
		}
	}

//looking to collect a range of data regarding ticket buying habits and perhaps with a view to introducing more theatregoers
//change ticket
//group booking discount possible
//change shows
//users can delete data