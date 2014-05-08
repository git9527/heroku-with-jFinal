package me.bird.heroku.utils;

import java.security.MessageDigest;

/*
 * SHA1 水印算法工具类
 * AJ 2013-04-12
 */
public final class DigestUtil {
	private static final DigestUtil _instance = new DigestUtil();

	private MessageDigest alga;

	private DigestUtil() {
		try {
			alga = MessageDigest.getInstance("SHA-1");
		} catch (Exception e) {
			throw new InternalError("init MessageDigest error:" + e.getMessage());
		}
	}

	public static DigestUtil getInstance() {
		return _instance;
	}

	public static String byte2hex(byte[] b) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < b.length; i++) {
			tmp = (Integer.toHexString(b[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	public String encipher(String strSrc) {
		String strDes = null;
		byte[] bt = strSrc.getBytes();
		alga.update(bt);
		strDes = byte2hex(alga.digest()); // to HexString
		return strDes;
	}

}
