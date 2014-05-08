package me.bird.heroku.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

	public static int INDEX_NOT_FOUND = -1;

	public static boolean containsIgnoreCase(String source, String target) {
		if (source == null || target == null) {
			return false;
		}
		int len = target.length();
		int max = source.length() - len;
		for (int i = 0; i <= max; i++) {
			if (regionMatches(source, true, i, target, 0, len)) {
				return true;
			}
		}
		return false;
	}
	
	static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart,
            CharSequence substring, int start, int length)    {
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        } else {
            return cs.toString().regionMatches(ignoreCase, thisStart, substring.toString(), start, length);
        }
    }

	public static boolean equals(String string1, String string2) {
		return string1 == null ? string2 == null : string1.equals(string2);
	}

	public static String subStringBefore(String source, String target) {
		if (isEmpty(source))
			return null;
		int index = source.indexOf(target);
		if (INDEX_NOT_FOUND == index) {
			return source;
		} else {
			return source.substring(0, index);
		}
	}

	public static String subStringBeforeLast(String source, String target) {
		if (isEmpty(source))
			return null;
		int index = source.lastIndexOf(target);
		if (INDEX_NOT_FOUND == index) {
			return source;
		} else {
			return source.substring(0, index);
		}
	}

	public static String subStringBetween(String source, String open, String close) {
		if (source == null || open == null || close == null) {
            return null;
        }
		int start = source.indexOf(open);
		if (start != INDEX_NOT_FOUND) {
			int end = source.indexOf(close, start + open.length());
			if (end != INDEX_NOT_FOUND) {
				return source.substring(start + open.length(), end);
			}
		}
		return null;
	}

	public static String[] subStringsBetween(String source, String open, String close) {
		if (source == null || isEmpty(open) || isEmpty(close)) {
			return null;
		}
		int strLen = source.length();
		if (strLen == 0) {
			return new String[0];
		}
		int closeLen = close.length();
		int openLen = open.length();
		List<String> list = new ArrayList<String>();
		int pos = 0;
		while (pos < strLen - closeLen) {
			int start = source.indexOf(open, pos);
			if (start < 0) {
				break;
			}
			start += openLen;
			int end = source.indexOf(close, start);
			if (end < 0) {
				break;
			}
			list.add(source.substring(start, end));
			pos = end + closeLen;
		}
		if (list.isEmpty()) {
			return null;
		}
		return list.toArray(new String[list.size()]);
	}

	public static String subStringAfter(String source, String target) {
		if (isEmpty(source))
			return source;
		int index = source.indexOf(target);
		if (INDEX_NOT_FOUND == index) {
			return null;
		} else {
			return source.substring(index + target.length(), source.length());
		}
	}

	public static String subStringAfterLast(String source, String target) {
		if (isEmpty(source))
			return source;
		int index = source.indexOf(target);
		if (INDEX_NOT_FOUND == index) {
			return null;
		} else {
			return source.substring(index + target.length(), source.length());
		}
	}

	public static boolean isEmpty(String source) {
		return source == null || "".equals(source);
	}
}
