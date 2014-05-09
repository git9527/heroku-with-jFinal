package me.bird.qiniu;

import me.bird.heroku.manager.QiNiuManager;

import org.junit.Test;

import com.google.common.io.Resources;

public class TestQiNiuManager {
	
	private QiNiuManager qiNiuManager = new QiNiuManager();
	
	@Test
	public void test_post() throws Exception{
		byte[] data = Resources.toByteArray(Resources.getResource("image/disney.png"));
		System.out.println(qiNiuManager.postData(data, "a/b/3.png"));
	}
	
}
