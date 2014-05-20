package me.bird.util;

import me.bird.utils.StringUtils;

import org.junit.Assert;
import org.junit.Test;

public class TestStringUtils {

	@Test
	public void test_sub_string_before(){
		Assert.assertEquals("teststring", StringUtils.subStringBefore("teststringzhangsn", "zhangsn"));
		Assert.assertEquals(null, StringUtils.subStringBefore(null, "zhangsn"));
		Assert.assertEquals(null, StringUtils.subStringBefore("", "zhangsn"));
		Assert.assertEquals("teststringzhangsn", StringUtils.subStringBefore("teststringzhangsn", "notExist"));
	}
	
	@Test
	public void test_sub_string_after(){
		Assert.assertEquals("zhangsn", StringUtils.subStringAfter("teststringzhangsn", "string"));
		Assert.assertEquals(null, StringUtils.subStringBefore(null, "zhangsn"));
		Assert.assertEquals(null, StringUtils.subStringBefore("", "zhangsn"));
		Assert.assertEquals("teststringzhangsn", StringUtils.subStringBefore("teststringzhangsn", "notExist"));
	}
	
	@Test
	public void test_sub_string_after_last(){
		Assert.assertEquals("11.css", StringUtils.subStringAfterLast("http://127.0.0.1:8080/heroku/resources/css/11.css", "/"));
	}
	
	@Test
	public void test_sub_string_before_last(){
		Assert.assertEquals("http://127.0.0.1:8080/heroku/resources/css", StringUtils.subStringBeforeLast("http://127.0.0.1:8080/heroku/resources/css/11.css", "/"));
	}
	
	@Test
	public void test_sub_string_between(){
		Assert.assertEquals("string", StringUtils.subStringBetween("teststringzhangsn","st","zhangsn"));
		Assert.assertEquals(null, StringUtils.subStringBetween(null, "zhangsn",null));
		Assert.assertEquals(null, StringUtils.subStringBetween("", null,"zhangsn"));
		Assert.assertEquals(null, StringUtils.subStringBetween("teststringzhangsn", "notExist",null));
	}
	
	@Test
	public void test_equals(){
		Assert.assertTrue(StringUtils.equals("test", "test"));
		Assert.assertTrue(StringUtils.equals("test", new String("test")));
	}
}
