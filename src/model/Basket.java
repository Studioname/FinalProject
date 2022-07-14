package model;

import java.util.ArrayList;

/**
 * Represents a shopping basket which the user can add Booking objects to
 * @author Conan
 *
 */

public class Basket {

    private ArrayList<Booking> basket;
    Customer customer;

    /**
     * Constructor for the Basket class
     */
    public Basket() {
    	basket = new ArrayList<Booking>();
    }

    /**
     * Returns booking at index i
     * @param i
     * @return
     */
    public Booking getItem(int i) {
        return basket.get(i);
    }

    /**
     * Adds a booking to basket
     * @param booking
     */
    public void addToBasket(Booking booking) {
    	basket.add(booking);
    }
    
    /**
     * Calls the printBasicBookingDetails for each booking in the basket
     */
    public void printBasketContents() {
    	for (int i = 0; i < basket.size(); i++) {
    		Booking booking = basket.get(i);
    		booking.printBasicBookingDetails(i);
    	}
    }
    
    /**
     * Returns the size of the basket
     * @return
     */
    public int getSize() {
    	return basket.size();
    }
    
    /**
     * Iterates through the basket, gets the playId for each booking, and fetches the play with that Id
     * from the database. Gets the price from said play and applies it to the relevant booking
     * @param plays
     */
    public void setBookingPrices(ArrayList<Play> plays){
    	for (int i = 0; i < basket.size(); i++) {
    		int playId = basket.get(i).getPlayId();
    		int seatType = basket.get(i).getSeatType();
    		for (int j = 0; j < plays.size(); j++) {
    			if (playId == plays.get(j).getPlayId()) {
    				basket.get(i).setPlay(plays.get(j));
    				switch (seatType) {
    				case (0):
    					basket.get(i).setPrice(plays.get(j).getPlayStallsPrice());
    					break;
    				case (1):
    					basket.get(i).setPrice(plays.get(j).getPlayCirclePrice());
    					break;
    				default: 
    					return;
    				}
    			}
    		}
    	}
    }
    
	/**
	 * Loops through the basket and sets a given number of tickets to concessionary
	 * @param noOfConcessions
	 */
	public void createConcessionaryBookings(int noOfConcessions) {
		for (int i = 0; i < noOfConcessions; i++) {
			basket.get(i).setConcession(1);
		}
	}
	
	/**
	 * Creates booking objects with the given parameters and adds them to the basket
	 * @param play
	 * @param stallsOrCircle
	 * @param noOfSeats
	 * @param seatNumbers
	 * @param noOfConcessions
	 * @param isPostal
	 */
	public void createBookings(Play play, int stallsOrCircle, int noOfSeats, int[] seatNumbers, int noOfConcessions,
			int isPostal) {
		for (int i = 0; i < noOfSeats; i++) {
			Booking b = new Booking(play.getPlayId(), stallsOrCircle, seatNumbers[i], 0, isPostal);
			basket.add(b);
		}
	}
    
	/**
	 * Iterates through the basket and uses the applyConcession method, which checks to see if a booking
	 * is concessionary and if so modifies the price
	 * @return
	 */

    public void applyConcessions() {
    	for (int i = 0; i < basket.size(); i++) {
    		basket.get(i).applyConcession();
    	}
    }
    
    /**
     * Returns the total price of bookings in the basket
     * @return
     */
    public int getBookingsTotal() {
		int total = 0;
		for (int i = 0; i < basket.size(); i++) {
			total += basket.get(i).getPrice();
			}
		return total;
    }
	
    /**
     * Prints the relevant basket information with relevant pricing details
     */
    public void printCheckoutDetails() {
    	System.out.println("You currently have " + basket.size() + " item(s) in your basket. These are:");
    	for (int i = 0; i < basket.size(); i++) {
    		basket.get(i).printCheckoutDetails(i);
    	}
    	System.out.println("Postage: " + getFormattedPrice(calculatePostage()));
    	System.out.println("Bookings: " + getFormattedPrice(getBookingsTotal()));
    	System.out.println("Total: " + getFormattedPrice(getBookingsTotal() + calculatePostage()));
    }
    
    /**
     * Returns a string with relevant basket information
     */
    public String getPurchaseDetails() {
    	String string = "";
    	string += "" + customer.getCustomerForename() + " " + customer.getCustomerSurname() + '\n';
    	string += "" + customer.getCustomerAddress() + '\n';
    	string += "" + customer.getCustomerEmail() + '\n'; 
    	string += "" + customer.getCustomerTelephone() + '\n' + '\n';
    	string += "Purchase receipt for " + basket.size() + " item(s):" + '\n' + '\n';
    	for (int i = 0; i < basket.size(); i++) {
    		string += basket.get(i).getPurchaseDetails(i) + '\n';
    	}
    	string += "Postage: " + getFormattedPrice(calculatePostage()) + '\n';
    	string += "Bookings: " + getFormattedPrice(getBookingsTotal()) + '\n';
    	string += "Total: " + getFormattedPrice(getBookingsTotal() + calculatePostage()) + '\n' + '\n';
    	string += "Thank you for shopping at the Theatre Royal";
    	return string;
    }
    
    /**
     * Takes a number, returns a string converting it to currency (£)
     * @param price
     * @return
     */
	public String getFormattedPrice(int price) {
		if (price == 0) {
			return "£0.00";
		}
		String priceString = Integer.toString(price);
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
	 * Calculates the cost of postage based upon the number of concessions vs tickets
	 * @return
	 */
	public int calculatePostage() {
		int noOfBookings = basket.size();
		int noOfConcessions = getNoOfConcessions();
		if (noOfConcessions == 0) {
			return noOfBookings * 100;
		}
		else if (noOfConcessions == noOfBookings) {
			return 0;
		}
		else {
			return 100;
		}
	}
	
	/**
	 * Returns the number of concessionary tickets in the basket
	 * @return
	 */
	public int getNoOfConcessions() {
		int noOfConcessions = 0;
		for (int i = 0; i < basket.size(); i++) {
			if (basket.get(i).getConcession() == 1) {
				noOfConcessions++;
			}
		}
		return noOfConcessions;
	}
	
	/**
	 * Empties the basket after purchase complete
	 */
	public void clearBasket() {
		basket.clear();
	}
	
	/**
	 * Returns Customer object for basket
	 * @return
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Sets Customer object
	 * @param customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}