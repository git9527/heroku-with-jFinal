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
			Object object = jsonObject.opt(this.getRealFieldName(field));
			if (object != null){
				JSONArray jsonArray = (JSONArray) object;
				field.set(targetClass, this.getListFromJson(jsonArray, internalClass));
			}
		}else {
			Object object = jsonObject.opt(this.getRealFieldName(field));
			if (null == object){
				return;
			} else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)){
				field.set(targetClass, Integer.valueOf(object.toString()));
			} else if (field.getType().equals(Long.class) || field.getType().equals(long.class)){
				field.set(targetClass, Long.valueOf(object.toString()));
			} else if (field.getType().equals(Boolean.class) || field.getType().equals(boolean.class)){
				field.set(targetClass, Boolean.valueOf(object.toString()));
			} else {
				field.set(targetClass, object);
			}
		}
	}
	
	private String getRealFieldName(Field field){
		JsonProperty annotation = field.getAnnotation(JsonProperty.class);
		return (annotation == null)?field.getName():annotation.value();
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
