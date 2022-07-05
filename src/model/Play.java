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

	public Play(int playId, String playTitle, String playDescription, String playTime, String playDate, String playDuration, int circlePrice, int stallsPrice) {
		this.playId = playId;
		this.playTitle = playTitle;
		this.playType = 3;
		this.playDescription = playDescription;
		this.playTime = playTime;
		this.playDate = playDate;
		this.playDuration = playDuration;
		this.playCirclePrice = circlePrice;
		this.playStallsPrice = stallsPrice;
		this.playLanguage = null;
		this.playMusicalAccompaniment = 1;
	}
	public Play(int playId, String playTitle, int playType, String playDescription, String playTime, String playDate, String playDuration, int circlePrice, int stallsPrice, String language, int musicalAccompaniment) {
		this.playId = playId;
		this.playTitle = playTitle;
		this.playType = playType;
		this.playDescription = playDescription;
		this.playTime = playTime;
		this.playDate = playDate;
		this.playDuration = playDuration;
		this.playCirclePrice = circlePrice;
		this.playStallsPrice = stallsPrice;
		this.playLanguage = language;
		this.playMusicalAccompaniment = musicalAccompaniment;
		if (playType == 3) {
			musicalAccompaniment = 1;
			language = null;
		}
	}
	//methods
	public String getPlayTypeString(int playType) {
		switch(playType) {
		case 0: return "Theatre";
		case 1: return "Musical";
		case 2: return "Opera";
		case 3: return "Concert";
		default: return null;
		}
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
