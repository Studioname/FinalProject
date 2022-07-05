package model;

import java.util.ArrayList;

public class Basket {

    public ArrayList <Booking>basket;

    public Basket() {
    	basket= new ArrayList <Booking>();
    }

    public Booking getItem(int i) {
        return basket.get(i);
    }

    public void addToBasket(Booking booking) {
    	basket.add(booking);
    }

}