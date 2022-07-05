package engine;

import model.Play;

import java.util.ArrayList;

import model.Controller;
import util.DatabaseManager;

public class main {
	public static void main(String[] args) {
		DatabaseManager dbm = new DatabaseManager();
		dbm.connect("FinalProject", "jdbc:mysql://127.0.0.1:3306/");
		
		Play p = new Play("Cats", "A show about cats", "12:00:00", "2022-12-10", "02:00:00", 5000, 5000);
		Play p2 = new Play("Tears for Fears", "Tears to Fears is suitable for everyone and will be perfoming their top hits", "18:00:00", "2022-07-04", "01:00:00", 2200, 2200);
		Play p3 = new Play("La Traviata", 2, "La Traviesta contains all the elements of operatic addiction", "12:00:00", "2022-07-14","02:00:00", 2800, 2800, "Italian", 1);
		Play p4 = new Play("The Corn is Green", 0, "Miss Lily Moffat arrives in rural North Wales, determined to help young local miners out of poverty by teaching them to read and write. Book a ticket to find out more", "14:00:00", "2022-07-10", "01:00", 3600, 3600, "English", 0);
		Play p5 = new Play("Beautiful", 1, "A brand new production of the award-winning West End and Broadway show, Beautiful – The Carole King Musical, returns to Theatre Royal.", "15:00:00", "2022-08-04", "01:30", 1800, 1800, "English", 1);
		Play p6 = new Play("The Prodigy", 3, "The Prodigy returns to stage for a run with great shows that coincides with the 30th Anniversary.", "19:00:00", "2022-07-09", "02:00:00", 1500, 1500, null, 1);
		Play p7 = new Play("One Kiss", 1, "A one woman show with music, performed by Lara J. West. An old library book – What it Means to be a Woman – is handed to Georgia on her sixteenth birthday. It becomes the thread connecting three women as they journey through their lives.", "10:00:00", "2022-08-01","1:45:00", 2000, 2000, "French", 0);
		//String playTitle, int playType, String playDescription, String playTime, String playDate, String playDuration, int playCirclePrice, int playStallsPrice, String playLanguage, int playMusicalAccompaniment
		dbm.addPlay(p);
		dbm.addPlay(p2);
		dbm.addPlay(p3);
		dbm.addPlay(p4);
		dbm.addPlay(p5);
		dbm.addPlay(p6);
		dbm.addPlay(p7);
		//dbm.printResult(dbm.searchPlays());
		ArrayList<Play> plays = dbm.constructPlayArrayList();
		
		dbm.searchByDate(plays, "2022-10-12");
		dbm.searchByTitle(plays, "Cats");
		
		dbm.printBasicPlayDetails(plays);
		//dbm.printPlayArrayListDetails(dbm.constructPlayArrayList(dbm.searchPlays()));
		Controller c = new Controller();
	}
}