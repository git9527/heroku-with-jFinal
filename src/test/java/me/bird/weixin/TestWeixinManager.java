package me.bird.weixin;

import org.junit.Assert;
import org.junit.Test;

import me.bird.weixin.WeixinManager;

public class TestWeixinManager {

	private WeixinManager weixinManager = new WeixinManager();
	
	@Test
	public void test_check(){
		String signature = "8093ccb3d10c4b43d6b0fc319a760ca190f644da";
		String timestamp = "1400142763";
		String nonce = "851882236";
		String result = weixinManager.checkAuthentication(timestamp, nonce);
		Assert.assertEquals(signature, result);
	}
}
