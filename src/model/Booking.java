package model;

public class Booking {
	private int bookingId;
	private int showId;
	private int customerId;
	private int seatType;
	private int seatNumber;
	private int concession;
	private int isPostal;
	private int price;
	private Play play;
	
	public Booking(int showId, int customerId, int seatType, int seatNumber, int concession, int isPostal, int price) {
		this.setShowId(showId);
		this.setCustomerId(customerId);
		this.setSeatType(seatType);
		this.setSeatNumber(seatNumber);
		this.setConcession(concession);
		this.setIsPostal(isPostal);
		this.setPrice(price);
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

	public int getShowId() {
		return showId;
	}

	public void setShowId(int showId) {
		this.showId = showId;
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
	
}
