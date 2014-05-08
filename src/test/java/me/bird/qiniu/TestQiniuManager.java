package me.bird.qiniu;

import me.bird.heroku.qiniu.QiniuManager;

import org.junit.Test;

public class TestQiniuManager {
	
	private QiniuManager qiniuManager = new QiniuManager();

	@Test
	public void test(){
		System.out.println(qiniuManager.getToken("sunflower.jpg"));
	}
}
