package me.bird.util;

import java.util.HashMap;
import java.util.Map;

import me.bird.consts.BaseConsts;
import me.bird.utils.HttpUtil;

import org.junit.Assert;
import org.junit.Test;

public class TestHttpUtil {
	
	private HttpUtil httpUtil = new HttpUtil();

	@Test
	public void test_get_string() throws Exception{
		String result = httpUtil.getContent("http://www.renren.com", BaseConsts.ENCODING);
		Assert.assertNotNull(result);
	}
	
	@Test
	public void test_get_bytes() throws Exception{
		byte[] bytes = httpUtil.getContentBytes("http://su.bdimg.com/static/superpage/img/logo_white.png");
		System.out.println(bytes.length);
	}
	
	@Test
	public void test_post() throws Exception{
		String url = "http://www.feisuzw.com/skin/hongxiu/include/fe1sushow.php?r=6POph1LGqVoRf3ERlNbiip";
		String referer = "http://www.feisuzw.com/Html/3195/2896318.html";
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Referer", referer);
		headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		String postContent = "bookid=3195&chapterid=2896318";
		String result = httpUtil.postContent(url, headerMap, postContent.getBytes(), BaseConsts.ENCODING);
		System.out.println(result);
//		Assert.assertTrue(result.startsWith("﻿<p>要查这个"));
	}
	
}
