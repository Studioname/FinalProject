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
import model.Play;

public class DatabaseManager {
	private Connection conn;

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

	// 6. Process Results
	
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
	//standard method
	public ArrayList<Play> constructPlayArrayList() {
		ResultSet rs = searchPlays();
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
	//overloaded method
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
	//prints whole object details
	public void printPlayDetails(Play play) {
		play.printPlayDetails();
	}
	public void printPlayArrayListDetails(ArrayList<Play> plays) {
		for (int i = 0; i < plays.size(); i++) {
			plays.get(i).printPlayDetails();
		}
	}
	//prints partial object details
	public void printBasicPlayDetails(ArrayList<Play> plays) {
		for (int i = 0; i < plays.size(); i++) {
			plays.get(i).printBasicPlayDetails(i);
		}
	}
	//queries
	public ResultSet searchPlays() {
		String str = "SELECT * FROM Play";
		return runQuery(str);
	}
	public ArrayList<Play> searchByDate(ArrayList<Play> plays, String date) {
		ArrayList<Play> result = new ArrayList<Play>();
		for (int i = 0; i < plays.size(); i++) {
			if (plays.get(i).getPlayDate().equals(date)){
				result.add(plays.get(i));
			}
		}
		return result;
	}
	public ArrayList<Play> searchByTitle(ArrayList<Play> plays, String name) {
		ArrayList<Play> result = new ArrayList<Play>();
		for (int i = 0; i < plays.size(); i++) {
			if (plays.get(i).getPlayTitle().equals(name)){
				result.add(plays.get(i));
			}
		}
		return result;
	}
	
	public ArrayList<Play> customSearch(ArrayList<String> columnNames, int userSelection){
		printColumnNames(columnNames);
		//record int input from user
		String str = "SELECT * FROM Play WHERE " + columnNames.get(userSelection) + " != NULL ORDER BY " +  columnNames.get(userSelection) + " ASC;";
		ResultSet rs2 = runQuery(str);
		return constructPlayArrayList(rs2);
	}
	
	public ArrayList<Play> customSearch(ArrayList<String> columnNames, int userSelection, String value){
		printColumnNames(columnNames);
		//record int input from user
		String str = "SELECT * FROM Play WHERE " + columnNames.get(userSelection) + " LIKE '" + value + "';";
		ResultSet rs2 = runQuery(str);
		return constructPlayArrayList(rs2);
	}
	
	//needs work and copy pasting on above method
	
	public ArrayList<Play> customSearch(ArrayList<String> columnNames, int userSelection, int value){
		printColumnNames(columnNames);
		//record int input from user
		String str = "SELECT * FROM Play WHERE " + columnNames.get(userSelection) + " = " + value + ";";
		ResultSet rs2 = runQuery(str);
		return constructPlayArrayList(rs2);
	}
	
	//Select PlayTitle, PlayStallsPrice from  FinalProject.Play where  PlayStallPrice <=20 order by PlayTitle asc;

//	public void insertPeople(ArrayList<Person> everyone) {
//		// TODO Auto-generated method stub
//		for (Person p : everyone) {
//			String sql = "INSERT INTO Person VALUES(" + "\"" + p.getName() + "\", " + p.getAge() + ")";
//			System.out.println(sql);
//			this.runQuery(sql);
//		}
//
//	}
	
	//queries
	//add show
	public void addPlay(Play play) {
		String str = "INSERT INTO Play (PlayType, PlayTitle, PlayDescription, PlayTime, PlayDate, PlayDuration, PlayCirclePrice, PlayStallsPrice, PlayLanguage, PlayMusicalAccompaniment) VALUES (" + play.getPlayType() + ", '" + play.getPlayTitle() + "', '" + play.getPlayDescription() + "', '" + play.getPlayTime() + "', '" + play.getPlayDate() + "', '" + play.getPlayDuration() + "', " + play.getPlayCirclePrice() + ", " + play.getPlayStallsPrice() + ", '" + play.getFormattedPlayLanguage() + "', " + play.getPlayMusicalAccompaniment() + ");";
		runQuery(str);
	}
	
	public void addBooking(Booking booking) {
		String str = "INSERT INTO Booking (SeatType, SeatNumber, Concession, IsPostal, CustomerId, PlayId) VALUES (" + booking.getShowId() + ", " + booking.getCustomerId() + ", " + booking.getSeatNumber() + ", " + booking.getBookingId() + ", " + "," + booking.getIsPostal() + ", "+ booking.getConcession() + ", " + booking.getPrice() + ")";
	}
	
	public boolean seatIsFree(int showId, int seatType, int seatNumber) {
		String str = "SELECT * FROM Booking WHERE ShowId = " + showId + " AND SeatType = " + seatType + " AND SeatNumber = " + seatNumber + ")";
		return runQuery(str).equals(null) ? true : false;
	}
	
	//custom search
	
	//format as currency
	//CONCAT('£', FORMAT(SUM(Balance), 2)) AS Price
}

//contact details

//looking to collect a range of data regarding ticket buying habits and perhaps with a view to introducing more theatregoers
//change ticket
//group booking discount possible
//change shows
//users can delete data

//Runnable r = () -> setNum(1);
//r.run();