package me.bird.zhihu;

import java.util.ArrayList;
import java.util.List;

import me.bird.annotations.JsonProperty;

public class LastestNews {

	private String date;
	
	@JsonProperty("display_date")
	private String dateDisplay;
	
	@JsonProperty("is_today")
	private boolean isToday;
	
	private List<News> news = new ArrayList<News>();
	
	@JsonProperty("top_stories")
	private List<News> topStories = new ArrayList<News>();
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDateDisplay() {
		return dateDisplay;
	}

	public void setDateDisplay(String dateDisplay) {
		this.dateDisplay = dateDisplay;
	}

	public boolean isToday() {
		return isToday;
	}

	public void setToday(boolean isToday) {
		this.isToday = isToday;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	public List<News> getTopStories() {
		return topStories;
	}

	public void setTopStories(List<News> topStories) {
		this.topStories = topStories;
	}
	
}
