package me.bird.heroku.utils;

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
	
	public static boolean isBAE(){
		return StringUtils.equals(System.getProperty("user.name"), "bae");
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
