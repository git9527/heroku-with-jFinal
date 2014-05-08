package me.bird.util;

import me.bird.heroku.utils.StringUtils;

import org.junit.Assert;
import org.junit.Test;

public class TestStringUtils {

	@Test
	public void test_sub_string_before(){
		Assert.assertEquals("teststring", StringUtils.subStringBefore("teststringzhangsn", "zhangsn"));
		Assert.assertEquals(null, StringUtils.subStringBefore(null, "zhangsn"));
		Assert.assertEquals(null, StringUtils.subStringBefore("", "zhangsn"));
		Assert.assertEquals(null, StringUtils.subStringBefore("teststringzhangsn", "notExist"));
	}
	
	@Test
	public void test_sub_string_after(){
		Assert.assertEquals("zhangsn", StringUtils.subStringAfter("teststringzhangsn", "string"));
		Assert.assertEquals(null, StringUtils.subStringBefore(null, "zhangsn"));
		Assert.assertEquals(null, StringUtils.subStringBefore("", "zhangsn"));
		Assert.assertEquals(null, StringUtils.subStringBefore("teststringzhangsn", "notExist"));
	}
	
	@Test
	public void test_sub_string_between(){
		Assert.assertEquals("string", StringUtils.subStringBetween("teststringzhangsn","st","zhangsn"));
		Assert.assertEquals(null, StringUtils.subStringBetween(null, "zhangsn",null));
		Assert.assertEquals(null, StringUtils.subStringBetween("", null,"zhangsn"));
		Assert.assertEquals(null, StringUtils.subStringBetween("teststringzhangsn", "notExist",null));
	}
}
