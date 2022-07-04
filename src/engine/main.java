package engine;

import model.Show;
import model.Controller;
import util.DatabaseManager;

public class main {
	public static void main(String[] args) {
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect("FinalProject", "jdbc:mysql://127.0.0.1:3306/");
		
		Controller c = new Controller();
		
		Show s = new Show(1, "Cats", 1, "A show about cats", "12:00:00", "2022-12-10", "2:00:00", 50, 50);
		
	}
}
