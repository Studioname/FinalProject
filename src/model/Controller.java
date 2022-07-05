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
	boolean running = true;
	public Controller(){
		dbm = new DatabaseManager();
		inputReader = new InputReader();
	}
	public void run() {
		while (running) {
			//welcome user, press a key to continue
			
			//menu screen
			String[] menu = {"1. Search all shows", "2. Search by Name", "3. Search by Date", "4. Purchase Ticket"};
			for (int i = 0; i < menu.length; i++) {
				System.out.println("Menu choices");
			}
			int userInput = inputReader.getNextInt();
			inputReader.nextLine();
			//submenus
			
			
		}
	}
}