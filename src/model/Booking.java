package model;

import model.Customer;

/**
 * Represents Booking objects for shows. The user can create bookings for performances at the theatre. Some of
 * the properties are user defined.
 * @author Conan
 *
 */

public class Booking {
	private int bookingId;
	private int playId;
	private int customerId;
	private int seatType;
	private int seatNumber;
	private int concession;
	private int isPostal;
	private int price;
	private Play play;
	//private Customer customer;
	
	/**
	 * Empty constructor for Booking class
	 */
	public Booking() {
		
	}
	
	/**
	 * User facing constructor for Booking class, takes user data which can be used to create Booking
	 * objects which can be used in the basket class
	 * @param playId
	 * @param seatType
	 * @param seatNumber
	 * @param concession
	 * @param isPostal
	 */
	public Booking(int playId, int seatType, int seatNumber, int concession, int isPostal) {
		this.playId = playId;
		this.setSeatType(seatType);
		this.setSeatNumber(seatNumber);
		this.setConcession(concession);
		this.setIsPostal(isPostal);
	}

	/**
	 * Database facing constructor which creates a Booking object from the Booking table in the database
	 * @param bookingId
	 * @param playId
	 * @param customerId
	 * @param seatType
	 * @param seatNumber
	 * @param concession
	 * @param isPostal
	 */
	public Booking(int bookingId, int playId, int customerId, int seatType, int seatNumber, int concession, int isPostal) {
		this.bookingId = bookingId;
		this.playId = playId;
		this.customerId = customerId;
		this.setSeatType(seatType);
		this.setSeatNumber(seatNumber);
		this.setConcession(concession);
		this.setIsPostal(isPostal);
	}
	
	/**
	 * Prints the details of a booking to the console
	 */
	public void printBookingDetails() {
		System.out.println("Booking Id: " + getBookingId());
		System.out.println("Play Id: " + getPlayId());
		System.out.println("Customer Id: " + getCustomerId());
		System.out.println("Seat Type: " + getFormattedSeatType());
		System.out.println("Seat Number: " + getSeatNumber());
		System.out.println("Concession: " + getFormattedConcession());
		System.out.println("Postal ticket: " + getFormattedIsPostal());
	}
	
	/**
	 * Prints basic booking details to the console
	 * @param index
	 */
	public void printBasicBookingDetails(int index) {
		index += 1;
		System.out.println("" + index + ". " + getPlayId() + ", " + getFormattedSeatType() + ", " + getSeatNumber() + ", " + getFormattedConcession());
	}
	
	/**
	 * Prints the details of a Booking to the console, used to review purchase
	 * @param index
	 */
	public String getPurchaseDetails(int index) {
		index += 1;
		String string = "" + index + ". " + play.getPlayTitle() + ", " + getFormattedSeatType() + ", Seat no. " + getSeatNumber() + ", Concession: " + getFormattedConcession() + ", Price: " + getFormattedPrice() + ", Postal: " + getFormattedIsPostal();
		return string;
	}
	
	/**
	 * Returns a string with Booking details for writing to file
	 * @param index
	 */
	public void printCheckoutDetails(int index) {
		index += 1;
		System.out.println("" + index + ". " + play.getPlayTitle() + ", " + getFormattedSeatType() + ", Seat no. " + getSeatNumber() + ", Concession: " + getFormattedConcession() + ", Price: " + getFormattedPrice() + ", Postal: " + getFormattedIsPostal());
	}
	
	/**
	 * SeatType is stored as an integer in the database, this function converts each value to its relevant
	 * String
	 * @return
	 */
	public String getFormattedSeatType() {
		switch(seatType) {
		case 0:
			return "Stalls";
		case 1:
			return "Circle";
		default:
			return "Unknown seat type";
		}
	}
	
	/**
	 * Concession is stored as an integer in the database, this function converts it to the String value
	 * it represents
	 * @return
	 */
	public String getFormattedConcession() {
		switch (concession) {
			case 0:
				return "No";
			case 1:
				return "Yes";
			default:
				return "Unknown";
		}
	}
	
	/**
	 * IsPostal is stored as an integer in the database, this function converts it to the String value
	 * it represents
	 * @return
	 */
	public String getFormattedIsPostal() {
		switch (concession) {
			case 0:
				return "No";
			case 1:
				return "Yes";
			default:
				return "Unknown";
		}
	}

	/**
	 * Returns a formatted version of a Booking's price variable with the price represented as a currency
	 * string e.g converts 5000 to £50.00
	 * @return
	 */
	public String getFormattedPrice() {
		String priceString = Integer.toString(this.price);
		char [] chars = priceString.toCharArray();
		String str = "£";
		if (chars.length <= 2) {
			return str += "0." + priceString;
		}
		
		for (int i = 0; i < chars.length -2; i++) {
			str += chars[i];
		}
		str += "." + chars[chars.length-2] + chars[chars.length-1];
		return str;
	}
	
	/**
	 * Applies concessionary discount to price if the booking is concessionary. This represents a 25% discount
	 */
	public void applyConcession() {
		if (getConcession() == 1) {
			Double price = getPrice() * 0.75;
			int value = price.intValue();
			setPrice(value);
		}
	}
	
	/**
	 * Returns bookingId
	 * @return
	 */
	public int getBookingId() {
		return bookingId;
	}

	/**
	 * Returns playId
	 * @return
	 */
	public int getPlayId() {
		return playId;
	}
	
	/**
	 * Returns customerId
	 * @return
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * Returns seatType
	 * @return
	 */
	public int getSeatType() {
		return seatType;
	}

	/**
	 * Sets seatType [0 = stalls, 1 = circle]
	 * @param seatType
	 */
	public void setSeatType(int seatType) {
		this.seatType = seatType;
	}
	
	/**
	 * Returns seatNumber
	 * @return
	 */
	public int getSeatNumber() {
		return seatNumber;
	}
	
	/**
	 * Sets seatNumber
	 * @param seatNumber
	 */
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	/**
	 * Returns concession [0 = no, 1 = yes]
	 * @return
	 */
	public int getConcession() {
		return concession;
	}

	/**
	 * Sets concession
	 * @param concession
	 */
	public void setConcession(int concession) {
		this.concession = concession;
	}

	/**
	 * Returns isPostal [0 = no, 1 = yes]
	 * @return
	 */
	public int getIsPostal() {
		return isPostal;
	}

	/**
	 * Sets isPostal
	 * @param isPostal
	 */
	public void setIsPostal(int isPostal) {
		this.isPostal = isPostal;
	}

	/**
	 * Returns price of Booking in pence
	 * @return
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Sets price
	 * @param price
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Gets the Play object for this booking
	 * @return
	 */
	public Play getPlay() {
		return play;
	}

	/**
	 * Sets the play object for this booking
	 * @param play
	 */
	public void setPlay(Play play) {
		this.play = play;
	}
	
}
