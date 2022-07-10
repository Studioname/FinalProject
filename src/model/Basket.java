package model;

import java.util.ArrayList;

public class Basket {

    private ArrayList<Booking> basket;

    public Basket() {
    	basket = new ArrayList<Booking>();
    }

    public Booking getItem(int i) {
        return basket.get(i);
    }

    public void addToBasket(Booking booking) {
    	basket.add(booking);
    }
    
    public void printBasketContents() {
    	for (int i = 0; i < basket.size(); i++) {
    		Booking booking = basket.get(i);
    		booking.printBasicBookingDetails(i);
    	}
    }
    
    public int getSize() {
    	return basket.size();
    }
}