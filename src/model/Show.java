package model;

import java.time.*;

public class Show {
	private int showId;
	private String showTitle;
	private int showType;
	private String showDescription;
	private String showTime;
	private String showDate;
	private String showDuration;
	private int circlePrice;
	private int stallsPrice;
	private String language;
	private int musicalAccompaniment;

	public Show(int showId, String showTitle, int showType, String showDescription, String showTime, String showDate, String showDuration, int circlePrice, int stallsPrice) {
		this.showId = showId;
		this.showTitle = showTitle;
		this.showType = showType;
		this.showDescription = showDescription;
		this.showTime = showTime;
		this.showDate = showDate;
		this.showDuration = showDuration;
		this.circlePrice = circlePrice;
		this.stallsPrice = stallsPrice;
		this.language = null;
		this.musicalAccompaniment = 1;
	}
	public Show(int showId, String showTitle, int showType, String showDescription, String showTime, String showDate, String showDuration, int circlePrice, int stallsPrice, String language, int musicalAccompaniment) {
		this.showId = showId;
		this.showTitle = showTitle;
		this.showType = showType;
		this.showDescription = showDescription;
		this.showTime = showTime;
		this.showDate = showDate;
		this.showDuration = showDuration;
		this.circlePrice = circlePrice;
		this.stallsPrice = stallsPrice;
		this.language = language;
		this.musicalAccompaniment = musicalAccompaniment;
		if (showType == 3) {
			musicalAccompaniment = 1;
			language = null;
		}
	}
	//methods
	public String getShowTypeString(int showType) {
		switch(showType) {
		case 0: return "Theatre";
		case 1: return "Musical";
		case 2: return "Opera";
		case 3: return "Concert";
		default: return null;
		}
	}
	public int getShowId() {
		return showId;
	}
	public void setShowId(int showId) {
		this.showId = showId;
	}
	public String getShowTitle() {
		return showTitle;
	}
	public void setShowTitle(String showTitle) {
		this.showTitle = showTitle;
	}
	public int getShowType() {
		return showType;
	}
	public void setShowType(int showType) {
		this.showType = showType;
	}
	public String getShowDescription() {
		return showDescription;
	}
	public void setShowDescription(String showDescription) {
		this.showDescription = showDescription;
	}
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public String getShowDate() {
		return showDate;
	}
	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}
	public String getShowDuration() {
		return showDuration;
	}
	public void setShowDuration(String showDuration) {
		this.showDuration = showDuration;
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
