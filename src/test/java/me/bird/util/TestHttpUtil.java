package me.bird.util;

import java.util.HashMap;
import java.util.Map;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.utils.HttpUtil;

import org.junit.Assert;
import org.junit.Test;

public class TestHttpUtil {
	
	private HttpUtil httpUtil = new HttpUtil();

	@Test
	public void test_get() throws Exception{
		String result = httpUtil.getContent("http://www.renren.com", BaseConsts.BASE_ENCODING);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void test_post() throws Exception{
		String url = "http://www.feisuzw.com/skin/hongxiu/include/fe1sushow.php?r=EWQ8PuAOiu3WEuy~";
		String referer = "http://www.feisuzw.com/Html/3195/2896318.html";
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Referer", referer);
		headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("bookid", "3195");
		paramMap.put("chapterid", "2896318");
		String result = httpUtil.postContent(url, headerMap, paramMap, BaseConsts.BASE_ENCODING);
		Assert.assertTrue(result.startsWith("﻿<p>要查这个"));
	}
}
