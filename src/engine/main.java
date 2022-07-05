package engine;

import model.Play;
import model.Controller;
import util.DatabaseManager;

public class main {
	public static void main(String[] args) {
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect("FinalProject", "jdbc:mysql://127.0.0.1:3306/");
		
		Play p = new Play(1, "Cats", "A show about cats", "12:00:00", "2022-12-10", "02:00:00", 50, 50);
		dbm.addPlay(p);
		dbm.printResult(dbm.getPlays());
		dbm.printResult(dbm.searchByDate("2022-10-12"));
		dbm.printResult(dbm.searchByName("Cats"));
		
		Controller c = new Controller();
		
		
	}
}