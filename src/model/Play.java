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
	private int circlePrice;
	private int stallsPrice;
	private String language;
	private int musicalAccompaniment;

	public Play(int playId, String playTitle, String playDescription, String playTime, String playDate, String playDuration, int circlePrice, int stallsPrice) {
		this.playId = playId;
		this.playTitle = playTitle;
		this.playType = 3;
		this.playDescription = playDescription;
		this.playTime = playTime;
		this.playDate = playDate;
		this.playDuration = playDuration;
		this.circlePrice = circlePrice;
		this.stallsPrice = stallsPrice;
		this.language = null;
		this.musicalAccompaniment = 1;
	}
	public Play(int playId, String playTitle, int playType, String playDescription, String playTime, String playDate, String playDuration, int circlePrice, int stallsPrice, String language, int musicalAccompaniment) {
		this.playId = playId;
		this.playTitle = playTitle;
		this.playType = playType;
		this.playDescription = playDescription;
		this.playTime = playTime;
		this.playDate = playDate;
		this.playDuration = playDuration;
		this.circlePrice = circlePrice;
		this.stallsPrice = stallsPrice;
		this.language = language;
		this.musicalAccompaniment = musicalAccompaniment;
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
	public int getCirclePrice() {
		return circlePrice;
	}
	public void setCirclePrice(int circlePrice) {
		this.circlePrice = circlePrice;
	}
	public int getStallsPrice() {
		return stallsPrice;
	}
	public void setStallsPrice(int stallsPrice) {
		this.stallsPrice = stallsPrice;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getMusicalAccompaniment() {
		return musicalAccompaniment;
	}
	public void setMusicalAccompaniment(int musicalAccompaniment) {
		this.musicalAccompaniment = musicalAccompaniment;
	}
}
