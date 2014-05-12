package me.bird.heroku.encode;

import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import me.bird.heroku.consts.BaseConsts;

/*
 * 加密算法工具类
 */
public class DigestUtil {

	public static String hmacSha1Hex(String data, String key) {
		return bytetoString(hmacSha1(data, key));
	}

	public static byte[] hmacSha1(String data, String key) {
		return hmacSha1(data.getBytes(), key);
	}
	
	public static byte[] hmacSha1(byte[] data,String key){
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			SecretKeySpec secret = new SecretKeySpec(key.getBytes(BaseConsts.ENCODING), "HmacSHA1");
			mac.init(secret);
			return mac.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] sha1(byte[] sources) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			return digest.digest(sources);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String sha1Hex(String inStr) {
		return bytetoString(sha1(inStr.getBytes()));
	}

	public static String bytetoString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}
