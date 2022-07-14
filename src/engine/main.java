package engine;

import model.Play;

import java.util.ArrayList;

import model.Booking;
import model.Controller;
import model.Customer;
import model.Employee;
import model.MainPerformers;
import util.DatabaseManager; 

public class main {
	public static void main(String[] args) {
		DatabaseManager dbm = new DatabaseManager();
		Controller c = new Controller();
		c.run();
	}
}