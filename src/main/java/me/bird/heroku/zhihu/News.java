package me.bird.heroku.zhihu;

import me.bird.heroku.annotations.JsonProperty;

public class News{
	
	private String title;
	
	private String url;
	
	private String image;
	
	@JsonProperty("share_url")
	private String shareUrl;
	
	private String thumbnail;
	
	private Long id;
	
	@JsonProperty("news_id")
	private Long newsId;
	
	@JsonProperty("ga_prefix")
	private String gaPrefix;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGaPrefix() {
		return gaPrefix;
	}

	public void setGaPrefix(String gaPrefix) {
		this.gaPrefix = gaPrefix;
	}

	public Long getNewsId() {
		return newsId;
	}

	public void setNewsId(Long newsId) {
		this.newsId = newsId;
	}
	
}
