package model;

import java.time.*;

/**
 * The Play class represents play objects. The user can create plays to push to the database, and we can
 * construct play objects from the Play table in the database to work with in Java
 * @author Conan
 *
 */

public class Play {
	private int playId;
	private String playTitle;
	private int playType;
	private String playDescription;
	private String playTime;
	private String playDate;
	private String playDuration;
	private int playCirclePrice;
	private int playStallsPrice;
	private String playLanguage;
	private int playMusicalAccompaniment;
	
	/**
	 * Empty constructor for the Play class
	 */
	public Play() {
		
	}

	/**
	 * User facing constructor, allows the user to create Play objects which can be pushed to the Play table
	 * in database
	 * @param playTitle
	 * @param playType
	 * @param playDescription
	 * @param playTime
	 * @param playDate
	 * @param playDuration
	 * @param playCirclePrice
	 * @param playStallsPrice
	 * @param playLanguage
	 * @param playMusicalAccompaniment
	 */
	public Play(String playTitle, int playType, String playDescription, String playTime, String playDate, String playDuration, int playCirclePrice, int playStallsPrice, String playLanguage, int playMusicalAccompaniment) {
		this.playTitle = playTitle;
		this.playType = playType;
		this.playDescription = playDescription;
		this.playTime = playTime;
		this.playDate = playDate;
		this.playDuration = playDuration;
		this.playCirclePrice = playCirclePrice;
		this.playStallsPrice = playStallsPrice;
		this.playLanguage = playLanguage;
		this.playMusicalAccompaniment = playMusicalAccompaniment;
		if (playType == 3) {
			playMusicalAccompaniment = this.playMusicalAccompaniment;
			playLanguage = this.playLanguage;
		}
	}
	/**
	 * Database facing constructor allows us to create Play objects from the Play table in the database to 
	 * work with in java
	 * @param playId
	 * @param playTitle
	 * @param playType
	 * @param playDescription
	 * @param playTime
	 * @param playDate
	 * @param playDuration
	 * @param playCirclePrice
	 * @param playStallsPrice
	 * @param playLanguage
	 * @param playMusicalAccompaniment
	 */
		public Play(int playId, String playTitle, int playType, String playDescription, String playTime, String playDate, String playDuration, int playCirclePrice, int playStallsPrice, String playLanguage, int playMusicalAccompaniment) {
			this.playId = playId;
			this.playTitle = playTitle;
			this.playType = playType;
			this.playDescription = playDescription;
			this.playTime = playTime;
			this.playDate = playDate;
			this.playDuration = playDuration;
			this.playCirclePrice = playCirclePrice;
			this.playStallsPrice = playStallsPrice;
			this.playLanguage = playLanguage;
			this.playMusicalAccompaniment = playMusicalAccompaniment;
			if (playType == 3) {
				playMusicalAccompaniment = this.playMusicalAccompaniment;
				playLanguage = this.playLanguage;
			}
		}
	/**
	 * playType is stored as an integer, this converts it into a readable form
	 * @return
	 */
	public String getFormattedPlayType() {
		switch(playType) {
		case 0: return "Theatre";
		case 1: return "Musical";
		case 2: return "Opera";
		case 3: return "Concert";
		default: return null;
		}
	}
	
	/**
	 * musicalAccompaniment is stored as an integer, this converts it into a readable format
	 * @return
	 */
	public String getMusicalAccompanimentString() {
		switch(playMusicalAccompaniment) {
		case 0: return "No";
		case 1: return "Yes";
		default: return null;
		}
	}
	
	/**
	 * price is stored as an integer, this returns a string containing price as currency [£]
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
			str += "0";
		}
		
		for (int i = 0; i < chars.length -2; i++) {
			str += chars[i];
		}
		str += "." + chars[chars.length-2] + chars[chars.length-1];
		return str;
	}
	
	/**
	 * Returns playLanguage, except if playLanguage is null [as in case of concerts] returns "None"
	 * @return
	 */
	public String getFormattedPlayLanguage() {
		if (playLanguage != null) {
			return playLanguage;
		}
		else {
			return "None";
		}
	}
	
	/**
	 * Prints details of a play to the console
	 */
	public void printPlayDetails() {
		System.out.println("Title: " + getPlayTitle());
		System.out.println("Type: " + getFormattedPlayType());
		System.out.println("Description: " + getPlayDescription());
		System.out.println("Time: " + getPlayTime());
		System.out.println("Date: " + getPlayDate());
		System.out.println("Duration: " + getPlayDuration());
		System.out.println("Circle Seat Price: " + getFormattedPrice(playCirclePrice));
		System.out.println("Stalls Seat Price: " + getFormattedPrice(playStallsPrice));
		System.out.println("Language: " + getFormattedPlayLanguage());
		System.out.println("Musical Accompaniment: " + getMusicalAccompanimentString() + '\n');
	}
	
	/**
	 * Prints basic play details to the console
	 * @param index
	 */
	public void printBasicPlayDetails(int index) {
		index += 1;
		System.out.println("" + index + ". " + getPlayId() + ". " + getPlayTitle() + ", " + getFormattedPlayType() + ", " + getPlayDate() + ", " + getFormattedPlayLanguage());
	}
	
	/**
	 * Returns playId
	 * @return
	 */
	public int getPlayId() {
		return playId;
	}
	
	/**
	 * Sets playId
	 * @param playId
	 */
	public void setPlayId(int playId) {
		this.playId = playId;
	}
	
	/**
	 * Returns playTitle
	 * @return
	 */
	public String getPlayTitle() {
		return playTitle;
	}
	
	/**
	 * Sets playTitle
	 * @param playTitle
	 */
	public void setPlayTitle(String playTitle) {
		this.playTitle = playTitle;
	}
	
	/**
	 * Returns playType [0 = theatre, 1 = musical, 2 = opera, 3 = concert]
	 * @return
	 */
	public int getPlayType() {
		return playType;
	}
	
	/**
	 * Sets playType [0 = theatre, 1 = musical, 2 = opera, 3 = concert]
	 * @param playType
	 */
	public void setPlayType(int playType) {
		this.playType = playType;
	}
	
	/**
	 * Returns playDescription
	 * @return
	 */
	public String getPlayDescription() {
		return playDescription;
	}
	
	/**
	 * Sets playDescription
	 * @param playDescription
	 */
	public void setPlayDescription(String playDescription) {
		this.playDescription = playDescription;
	}
	
	/**
	 * Returns a string containing playTime
	 * @return
	 */
	public String getPlayTime() {
		return playTime;
	}
	
	/**
	 * Sets playTime
	 * @param playTime
	 */
	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}
	
	/**
	 * Returns playDate
	 * @return
	 */
	public String getPlayDate() {
		return playDate;
	}
	
	/**
	 * Sets playDate
	 * @param playDate
	 */
	public void setPlayDate(String playDate) {
		this.playDate = playDate;
	}
	
	/**
	 * Returns playDuration
	 * @return
	 */
	public String getPlayDuration() {
		return playDuration;
	}
	
	/**
	 * Sets playDuration
	 * @param playDuration
	 */
	public void setPlayDuration(String playDuration) {
		this.playDuration = playDuration;
	}
	
	/**
	 * Returns playCirclePrice
	 * @return
	 */
	public int getPlayCirclePrice() {
		return playCirclePrice;
	}
	
	/**
	 * Sets playCirclePrice
	 * @param circlePrice
	 */
	public void setPlayCirclePrice(int circlePrice) {
		this.playCirclePrice = circlePrice;
	}
	
	/**
	 * Returns playStallsPrice
	 * @return
	 */
	public int getPlayStallsPrice() {
		return playStallsPrice;
	}
	
	/**
	 * Sets playStallsPrice
	 * @param stallsPrice
	 */
	public void setPlayStallsPrice(int stallsPrice) {
		this.playStallsPrice = stallsPrice;
	}
	
	/**
	 * Returns plauLanguage
	 * @return
	 */
	public String getPlayLanguage() {
		return playLanguage;
	}
	
	/**
	 * Sets playLanguage
	 * @param language
	 */
	public void setPlayLanguage(String language) {
		this.playLanguage = language;
	}
	
	/**
	 * Returns whether the play has musical accompaniment
	 * @return
	 */
	public int getPlayMusicalAccompaniment() {
		return playMusicalAccompaniment;
	}
	
	/**
	 * Sets musicalAccompaniment
	 * @param musicalAccompaniment
	 */
	public void setPlayMusicalAccompaniment(int musicalAccompaniment) {
		this.playMusicalAccompaniment = musicalAccompaniment;
	}
}
