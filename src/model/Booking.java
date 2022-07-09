package model;

import model.Customer;

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
	private Customer customer;
	
	//blank booking
	public Booking() {
		
	}
	
	//user facing constructor
	public Booking(int playId, int seatType, int seatNumber, int concession, int isPostal) {
		this.setPlayId(playId);
		this.setSeatType(seatType);
		this.setSeatNumber(seatNumber);
		this.setConcession(concession);
		this.setIsPostal(isPostal);
	}
	//user facing constructor
	public Booking(int playId, int customerId, int seatType, int seatNumber, int concession, int isPostal) {
		this.setPlayId(playId);
		this.customerId = customerId;
		this.setSeatType(seatType);
		this.setSeatNumber(seatNumber);
		this.setConcession(concession);
		this.setIsPostal(isPostal);
	}
	//database facing class
	public Booking(int bookingId, int playId, int customerId, int seatType, int seatNumber, int concession, int isPostal) {
		this.setBookingId(bookingId);
		this.setPlayId(playId);
		this.setCustomerId(customerId);
		this.setSeatType(seatType);
		this.setSeatNumber(seatNumber);
		this.setConcession(concession);
		this.setIsPostal(isPostal);
	}
	
	public void printBookingDetails() {
		System.out.println("Booking Id: " + getBookingId());
		System.out.println("Play Id: " + getPlayId());
		System.out.println("Customer Id: " + getCustomerId());
		System.out.println("Seat Type: " + getFormattedSeatType());
		System.out.println("Seat Number: " + getSeatNumber());
		System.out.println("Concession: " + getFormattedConcession());
		System.out.println("Postal ticket: " + getFormattedIsPostal());
		//System.out.println("Price: " + getFormattedPrice());
	}
	public void printBasicBookingDetails(int index) {
		index += 1;
		System.out.println("" + index + ". " + getPlayId() + ", " + getFormattedSeatType() + ", " + getSeatNumber() + ", " + getFormattedConcession());
	}
	
	//formatting
	
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
	
	public boolean getPostageAvailable() {
		return false;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getPlayId() {
		return playId;
	}

	public void setPlayId(int playId) {
		this.playId = playId;
	}
	
	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getSeatType() {
		return seatType;
	}

	public void setSeatType(int seatType) {
		this.seatType = seatType;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public int getConcession() {
		return concession;
	}

	public void setConcession(int concession) {
		this.concession = concession;
	}

	public int getIsPostal() {
		return isPostal;
	}

	public void setIsPostal(int isPostal) {
		this.isPostal = isPostal;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Play getPlay() {
		return play;
	}

	public void setPlay(Play play) {
		this.play = play;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
