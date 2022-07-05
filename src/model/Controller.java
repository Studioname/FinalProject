package model;

import model.Employee;
import model.MainPerformer;
import util.DatabaseManager;
import util.InputReader;
import util.DatabaseManager;
import model.Booking;
import model.Play;
import model.Basket;

public class Controller {
	DatabaseManager dbm;
	InputReader inputReader;
	public Controller(){
		dbm = new DatabaseManager();
		inputReader = new InputReader();
	}
}