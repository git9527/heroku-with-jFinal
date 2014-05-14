package me.bird.qiniu;

import java.util.Arrays;
import java.util.List;

import me.bird.heroku.qiniu.QiNiuManager;
import me.bird.heroku.qiniu.ResourceInfo;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.io.Resources;

@Ignore
public class TestQiNiuManager {
	
	private QiNiuManager qiNiuManager = new QiNiuManager();
	
	@Test
	public void test_post() throws Exception{
		byte[] data = Resources.toByteArray(Resources.getResource("image/disney.png"));
		System.out.println(qiNiuManager.postImage(data, "a/b/4.png"));
	}
	
	@Test
	public void test_access_token() throws Exception{
		Assert.assertEquals("X4rXz25vYNuH5escSXCU2rxK_v-Zilv5lfLwfilH:NKzKIZevw1UwQFYqWHlHgPlSrLU=", qiNiuManager.makeAccessToken("/stat/", "a-b-3.png"));
		Assert.assertEquals("X4rXz25vYNuH5escSXCU2rxK_v-Zilv5lfLwfilH:47IarNwMU_ANW_p2tUmpfpA_XF0=", qiNiuManager.makeAccessToken("/batch","op=/stat/emhhbmdzbjphLWItNC5wbmc=&op=/stat/emhhbmdzbjphLWItMy5wbmc=".getBytes()));
	}
	
	@Test
	public void test_get_stat() throws Exception{
		System.out.println(qiNiuManager.getSingleStat("a-b-3-1.png"));
	}
	
	@Test
	public void test_batch_get_stat() throws Exception{
		List<ResourceInfo> list = qiNiuManager.getBatchStat(Arrays.asList("a-b-3.png","a-b-1.png"));
		for (ResourceInfo resourceInfo : list) {
			System.out.println(resourceInfo.getStatusCode());
		}
	}
	
}
