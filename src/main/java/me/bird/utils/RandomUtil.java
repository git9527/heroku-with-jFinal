package me.bird.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import me.bird.consts.BaseConsts;

public class RandomUtil {
	
	public static String random(int length){
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		while (buffer.length() <length) {
			buffer.append(random.nextInt(10));
		}
		return buffer.toString();
	}

	public static Set<Integer> getRandomIndex(int maxIndex) {
		Random random = new Random();
		int actualLength = maxIndex > BaseConsts.WEIXIN_NEWS_MAX_ITEM ? BaseConsts.WEIXIN_NEWS_MAX_ITEM : maxIndex;
		Set<Integer> set = new HashSet<Integer>();
		while (set.size() < actualLength) {
			set.add(random.nextInt(maxIndex));
		}
		return set;
	}
}
