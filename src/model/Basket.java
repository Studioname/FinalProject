package model;

import java.util.ArrayList;

public class Basket {

    private ArrayList<Booking> basket;
    private int total;

    public Basket() {
    	basket = new ArrayList<Booking>();
    	total = 0;
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
    
    //we nested loop through the basket because there might be different plays in there
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
    
    public void getBasketTotal() {
    	this.total = 0;
    	for (int i = 0; i < basket.size(); i++) {
    		this.total += basket.get(i).getPrice();
    	}
    }
    
    public void printCheckoutDetails() {
    	System.out.println("You currently have " + basket.size() + " items in your basket. These are:");
    	for (int i = 0; i < basket.size(); i++) {
    		basket.get(i).printCheckoutDetails(i);
    	}
    	getBasketTotal();
    	System.out.println("Total: " + getFormattedPrice(this.total));
    }
    
	public String getFormattedPrice(int price) {
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
	
	public int getTotal(){
		return total;
	}
	public void setTotal(int total){
		this.total = total;
	}
}