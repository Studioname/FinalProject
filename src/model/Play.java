package model;

import java.time.*;

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

	//for internal use only
	public Play(String playTitle, String playDescription, String playTime, String playDate, String playDuration, int playCirclePrice, int playStallsPrice) {
		this.playTitle = playTitle;
		this.playType = 3;
		this.playDescription = playDescription;
		this.playTime = playTime;
		this.playDate = playDate;
		this.playDuration = playDuration;
		this.playCirclePrice = playCirclePrice;
		this.playStallsPrice = playStallsPrice;
		this.playLanguage = null;
		this.playMusicalAccompaniment = 1;
	}
	//user facing constructor
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
	//database facing constructor
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
	//methods
	public String getPlayTypeString() {
		switch(playType) {
		case 0: return "Theatre";
		case 1: return "Musical";
		case 2: return "Opera";
		case 3: return "Concert";
		default: return null;
		}
	}
	public String getMusicalAccompanimentString() {
		switch(playMusicalAccompaniment) {
		case 0: return "No";
		case 1: return "Yes";
		default: return null;
		}
	}
	public String getFormattedPrice(int price) {
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
	public String getFormattedPlayLanguage() {
		if (playLanguage != null) {
			return playLanguage;
		}
		else {
			return "None";
		}
	}
	public void printPlayDetails() {
		System.out.println("Title: " + getPlayTitle());
		System.out.println("Type: " + getPlayTypeString());
		System.out.println("Description: " + getPlayDescription());
		System.out.println("Time: " + getPlayTime());
		System.out.println("Date: " + getPlayDate());
		System.out.println("Duration: " + getPlayDuration());
		System.out.println("Circle Seat Price: " + getFormattedPrice(playCirclePrice));
		System.out.println("Stalls Seat Price: " + getFormattedPrice(playStallsPrice));
		System.out.println("Language: " + getFormattedPlayLanguage());
		System.out.println("Musical Accompaniment: " + getMusicalAccompanimentString());
	}
	public void printBasicPlayDetails(int index) {
		index += 1;
		System.out.println("" + index + ". " + getPlayTitle() + ", " + getPlayTypeString() + ", " + getPlayDate() + ", " + getFormattedPlayLanguage());
	}
	public int getPlayId() {
		return playId;
	}
	public void setPlayId(int playId) {
		this.playId = playId;
	}
	public String getPlayTitle() {
		return playTitle;
	}
	public void setPlayTitle(String playTitle) {
		this.playTitle = playTitle;
	}
	public int getPlayType() {
		return playType;
	}
	public void setPlayType(int playType) {
		this.playType = playType;
	}
	public String getPlayDescription() {
		return playDescription;
	}
	public void setPlayDescription(String playDescription) {
		this.playDescription = playDescription;
	}
	public String getPlayTime() {
		return playTime;
	}
	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}
	public String getPlayDate() {
		return playDate;
	}
	public void setPlayDate(String playDate) {
		this.playDate = playDate;
	}
	public String getPlayDuration() {
		return playDuration;
	}
	public void setPlayDuration(String playDuration) {
		this.playDuration = playDuration;
	}
	public int getPlayCirclePrice() {
		return playCirclePrice;
	}
	public void setPlayCirclePrice(int circlePrice) {
		this.playCirclePrice = circlePrice;
	}
	public int getPlayStallsPrice() {
		return playStallsPrice;
	}
	public void setPlayStallsPrice(int stallsPrice) {
		this.playStallsPrice = stallsPrice;
	}
	public String getPlayLanguage() {
		return playLanguage;
	}
	public void setPlayLanguage(String language) {
		this.playLanguage = language;
	}
	public int getPlayMusicalAccompaniment() {
		return playMusicalAccompaniment;
	}
	public void setPlayMusicalAccompaniment(int musicalAccompaniment) {
		this.playMusicalAccompaniment = musicalAccompaniment;
	}
}
