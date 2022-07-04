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
	
	public void printResult(ResultSet rs) {
		try {
			// while there is another row
			//Retrieving the ResultSetMetadata object
		      ResultSetMetaData rsMetaData = rs.getMetaData();
		      System.out.println("List of column names in the current table: ");
		      //Retrieving the list of column names
		      int count = rsMetaData.getColumnCount();
		      for(int i = 1; i<=count; i++) {
		         System.out.println(rsMetaData.getColumnName(i));
		      }
		      
// we have to make this compatible with our code
		      
//			while (rs.next()) {
//				System.out.print("Person's name is " + rs.getString(rsMetaData.getColumnName(1)) + " ");
//				System.out.println("Age is " + rs.getInt(rsMetaData.getColumnName(2)));
//			}
		} catch (SQLException e) {
			e.printStackTrace();
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

//	public ArrayList<Person> getPeople(ResultSet rs) {
//		ArrayList<Person> results = new ArrayList<>();
//		try {
//			while (rs.next()) {
//				String name = rs.getString("Name");
//				int age = rs.getInt("Age");
//				Person p = new Person(name, age);
//				results.add(p);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return results;
//	}

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
	public void addConcert(Play play) {
		String str = "INSERT INTO Play (PlayType, PlayTitle, PlayDescription, PlayTime, PlayDate, PlayDuration, PlayCirclePrice, PlayStallsPrice, PlayLanguage, PlayMusicalAccompaniment) VALUES (" + play.getPlayType() + ", '" + play.getPlayTitle() + "', '" + play.getPlayDescription() + "', '" + play.getPlayTime() + "', '" + play.getPlayDate() + "', '" + play.getPlayDuration() + "', " + play.getCirclePrice() + ", " + play.getStallsPrice() + ", " + play.getLanguage() + ", " + play.getMusicalAccompaniment() + ");";
		runQuery(str);
	}
	
	public ResultSet getPlays() {
		return runQuery("SELECT * FROM Play");
	}
	//format as currency
	//CONCAT('£', FORMAT(SUM(Balance), 2)) AS Price
}

//contact details

//looking to collect a range of data regarding ticket buying habits and perhaps with a view to introducing more theatregoers
//change ticket
//group booking discount possible
//change shows
//users can delete data