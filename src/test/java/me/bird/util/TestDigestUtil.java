package me.bird.util;

import me.bird.encode.DigestUtil;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

public class TestDigestUtil {

	@Test
	public void test_hmacsha1() throws Exception{
		Assert.assertEquals("c10e287f2b1e7f547b20a9ebce2aada26ab20ef2", DigestUtil.hmacSha1Hex("eyJzY29wZSI6Im15LWJ1Y2tldDpzdW5mbG93ZXIuanBnIiwiZGVhZGxpbmUiOjE0NTE0OTEyMDAsInJldHVybkJvZHkiOiJ7XCJuYW1lXCI6JChmbmFtZSksXCJzaXplXCI6JChmc2l6ZSksXCJ3XCI6JChpbWFnZUluZm8ud2lkdGgpLFwiaFwiOiQoaW1hZ2VJbmZvLmhlaWdodCksXCJoYXNoXCI6JChldGFnKX0ifQ==", "MY_SECRET_KEY"));
	}
	
	@Test
	public void test_sha1(){
		Assert.assertEquals(DigestUtils.sha1Hex("arl7onB4MoLePp7oTLNSrhxAOWw"), DigestUtil.sha1Hex("arl7onB4MoLePp7oTLNSrhxAOWw"));
	}
}
