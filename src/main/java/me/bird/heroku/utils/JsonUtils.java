package me.bird.heroku.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import me.bird.heroku.annotations.JsonProperty;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtils {

	@SuppressWarnings("unchecked")
	public <T> T fromJson(String jsonString, Class<T> clazz) throws Exception {
		JSONObject firstObject = new JSONObject(jsonString);
		Object targetClass = clazz.newInstance();
		for (Field field : clazz.getDeclaredFields()) {
			setFieldValue(targetClass, field, firstObject);
		}
		return (T) targetClass;
	}
	
	private void setFieldValue(Object targetClass,Field field,JSONObject jsonObject) throws Exception{
		field.setAccessible(true);
		if (field.getType().equals(List.class)){
			ParameterizedType internalType = (ParameterizedType) field.getGenericType();
			Class<?> internalClass = (Class<?>) internalType.getActualTypeArguments()[0];
			JSONArray jsonArray = jsonObject.getJSONArray(this.getRealFieldName(field));
			field.set(targetClass, this.getListFromJson(jsonArray, internalClass));
		}else if (this.fieldIsSimple(field)){
			field.set(targetClass, jsonObject.opt(this.getRealFieldName(field)));
		}
	}
	
	private String getRealFieldName(Field field){
		JsonProperty annotation = field.getAnnotation(JsonProperty.class);
		return (annotation == null)?field.getName():annotation.value();
	}
	
	private boolean fieldIsSimple(Field field){
		Class<?> clazz = field.getType();
		return clazz.equals(String.class) || clazz.equals(boolean.class) || clazz.equals(int.class) || clazz.equals(long.class);
	}
	
	@SuppressWarnings("unchecked")
	private <T> List<Class<T>> getListFromJson(JSONArray jsonArray,Class<T> clazz) throws Exception{
		List<T> list = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			Object object = this.fromJson(jsonArray.getJSONObject(i).toString(), clazz);
			list.add((T) object);
		}
		return (List<Class<T>>) list;
	}
	
}
