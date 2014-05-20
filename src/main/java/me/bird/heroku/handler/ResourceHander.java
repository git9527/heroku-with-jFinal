package me.bird.heroku.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.bird.heroku.utils.StringUtils;

import com.jfinal.handler.Handler;

public class ResourceHander extends Handler{

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		String lastPart = StringUtils.subStringAfterLast(target, "/");
		target = target.replace(lastPart, lastPart.replace(".", "-"));
		super.nextHandler.handle(target, request, response, isHandled);
	}

}
