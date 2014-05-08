package me.bird.util;

import me.bird.heroku.encode.DigestUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

public class TestDigestUtil {

	@Test
	public void test_hmacsha1(){
		Assert.assertEquals("8ba33c7db1ae3d3bd7b1f85edfb42345f840b23c", DigestUtil.hmacSha1Hex("eyJzY29wZSI6Im15LWJ1Y2tldDpzdW5mbG93ZXIuanBnIiwiZGVhZGxpbmUiOjE0NTE0OTEyMDAsInJldHVybkJvZHkiOiJ7XCJuYW1lXCI6JChmbmFtZSksXCJzaXplXCI6JChmc2l6ZSksXCJ3XCI6JChpbWFnZUluZm8ud2lkdGgpLFwiaFwiOiQoaW1hZ2VJbmZvLmhlaWdodCksXCJoYXNoXCI6JChldGFnKX0ifQ==", "MY_SECRET_KEY"));
	}
	
	@Test
	public void test_sha1(){
		Assert.assertEquals(DigestUtils.sha1Hex("arl7onB4MoLePp7oTLNSrhxAOWw"), DigestUtil.sha1Hex("arl7onB4MoLePp7oTLNSrhxAOWw"));
//		Assert.assertEquals("bd001563085fc35165329ea1ff5c5ecbdbbeef", DigestUtil.sha1Hex("123"));
	}
}