package me.bird.util;

import me.bird.consts.BaseConsts;
import me.bird.utils.JsonUtils;
import me.bird.zhihu.LastestNews;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.io.Resources;

public class TestJsonUtil {

	@Test
	public void test_from_json() throws Exception {
		String jsonString = Resources.toString(Resources.getResource("json/zhihu/lastest.json"), BaseConsts.CHARSET);
		LastestNews lastestNews = new JsonUtils().fromJson(jsonString, LastestNews.class);
		Assert.assertEquals("20140410", lastestNews.getDate());
		Assert.assertEquals("即使不含税，中国人买的进口车也比别人的贵", lastestNews.getNews().get(0).getTitle());
		Assert.assertEquals(3827573L, lastestNews.getNews().get(0).getId().longValue());
		Assert.assertEquals("http://daily.zhihu.com/api/2/news/3827825", lastestNews.getTopStories().get(0).getUrl());
	}
}
