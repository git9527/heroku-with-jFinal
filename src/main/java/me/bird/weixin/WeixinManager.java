package me.bird.weixin;

import java.util.Arrays;

import me.bird.consts.BaseConsts;
import me.bird.encode.DigestUtil;

public class WeixinManager {

	/**
	 * 微信验证方法
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	public String checkAuthentication(String timestamp, String nonce) {
		// 将获取到的参数放入数组
		String[] ArrTmp = { BaseConsts.WEIXIN_TOKEN, timestamp, nonce };
		// 按微信提供的方法，对数据内容进行排序
		Arrays.sort(ArrTmp);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ArrTmp.length; i++) {
			sb.append(ArrTmp[i]);
		}
		// 对排序后的字符串进行SHA-1加密
		return DigestUtil.sha1Hex(sb.toString());
	}
}
