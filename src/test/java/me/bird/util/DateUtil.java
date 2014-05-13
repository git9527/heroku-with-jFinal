package me.bird.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.bird.heroku.utils.StringUtils;

public class DateUtil {

	// 日期格式
	public static String yyyyMMdd = "yyyyMMdd";
	public static String yyyyMMddHHmm = "yyyyMMddHHmm";
	public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";

	// 中划线日期格式
	public static String yyyy_MM_dd = "yyyy-MM-dd";
	public static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

	public static Date getIntervalDate(Date date, String offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (offset.endsWith("D")) {
			int offsetInt = getIntFromOffset(offset, "D");
			calendar.add(Calendar.DAY_OF_MONTH, offsetInt);
		} else if (offset.endsWith("M")) {
			int offsetInt = getIntFromOffset(offset, "M");
			calendar.add(Calendar.MONTH, offsetInt);
		} else if (offset.endsWith("Y")) {
			int offsetInt = getIntFromOffset(offset, "Y");
			calendar.add(Calendar.YEAR, offsetInt);
		}
		return calendar.getTime();
	}

	public static String getIntervalDateString(String target, String offset, String format) {
		return toString(getIntervalDate(toDate(target, format), offset), format);
	}

	public static String getIntervalDateString(Date date, String offset, String format) {
		return toString(getIntervalDate(date, offset), format);
	}

	public static Date toDate(String dateString, String format) {
		if (StringUtils.isEmpty(dateString)) return null;  
        try {
        	return new SimpleDateFormat(format).parse(dateString);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	public static String toString(Date date, String format) {
		try {
			return new SimpleDateFormat(format).format(date);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static int getIntFromOffset(String offsetString, String subfix) {
		return Integer.valueOf(StringUtils.subStringBefore(offsetString, subfix));
	}
}
