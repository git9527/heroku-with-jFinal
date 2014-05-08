package me.bird.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.utils.HttpUtil;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

public class TestHttpUtil {
	
	private HttpUtil httpUtil = new HttpUtil();

	@Test
	public void test_get() throws Exception{
		String result = httpUtil.getContent("http://www.renren.com", BaseConsts.ENCODING);
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
		String result = httpUtil.postContent(url, headerMap, paramMap, BaseConsts.ENCODING);
		Assert.assertTrue(result.startsWith("﻿<p>要查这个"));
	}
	
	@Test
	public void test_post_image() throws Exception{
		String url = "http://up.qiniu.com";
		String path = this.getClass().getClassLoader().getResource("").getPath();
		path = path + "/../../src/main/webapp/resources/image/disney.png";
		File file = new File(path);
		byte[] data = FileUtils.readFileToByteArray(file);
		System.out.println(httpUtil.postImage(url, data, "test.png"));
	}
}
