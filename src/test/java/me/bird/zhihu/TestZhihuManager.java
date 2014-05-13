package me.bird.zhihu;

import me.bird.heroku.zhihu.LastestNews;
import me.bird.heroku.zhihu.News;
import me.bird.heroku.zhihu.ZhihuManager;

import org.junit.Test;

public class TestZhihuManager {
	
	private ZhihuManager zhihuManager = new ZhihuManager();

	@Test
	public void test() throws Exception{
		LastestNews news = zhihuManager.getLastestNews();
		for (News news2 : news.getNews()) {
			System.out.println(news2.getUrl());
		}
	}
}
