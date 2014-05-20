package me.bird.zhihu;

import java.util.ArrayList;
import java.util.List;

public class HotNews {

	private List<News> recent = new ArrayList<News>();

	public List<News> getRecent() {
		return recent;
	}

	public void setRecent(List<News> recent) {
		this.recent = recent;
	}
	
}
