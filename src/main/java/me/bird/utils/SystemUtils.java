package me.bird.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemUtils {

	public static String getLocalIP(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "unknown";
		}
	}
	
	public static boolean needProxy(){
		return getLocalIP().equals("127.0.1.1");
	}
	
	public static boolean isLocalDev(){
		return StringUtils.equals(System.getProperty("user.name"), "zhangsn");
	}
	
	public static boolean isMobile(String userAgent){
		if (StringUtils.containsIgnoreCase(userAgent, "iPhone")){
			return true;
		}
		if (StringUtils.containsIgnoreCase(userAgent, "Android")){
			return true;
		}
		return false;
	}
}
