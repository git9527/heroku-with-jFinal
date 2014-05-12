package me.bird.heroku.utils;

import java.lang.reflect.Field;
import java.util.List;

import org.json.JSONObject;

public class JsonUtils {

	public <T> T fromJson(String jsonString, Class<T> clazz) throws Exception {
		JSONObject firstObject = new JSONObject(jsonString);
		Object targetClass = clazz.newInstance();
		for (Field field : clazz.getFields()) {
			if (field.getType().equals(List.class)){
				
			}
		}
		return null;
	}
}
