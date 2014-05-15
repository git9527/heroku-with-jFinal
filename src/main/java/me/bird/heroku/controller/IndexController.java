package me.bird.heroku.controller;

import java.io.IOException;

import me.bird.heroku.utils.ResourceUtils;
import me.bird.heroku.utils.StringUtils;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class IndexController extends Controller{

	@ActionKey("/")
	public void index(){
		if (StringUtils.isEmpty(super.getPara())){
			super.renderHtml("Hello");
		}else {
			String key = super.getPara().replace("-", ".");
			byte[] bytes = ResourceUtils.getByteContent("ico", key);
			if (bytes == null || bytes.length == 0) {
				super.renderError(404);
			} else {
				try {
					super.getResponse().getOutputStream().write(bytes);
					super.renderNull();
				} catch (IOException e) {
					super.renderError(404);
				}
			}
		}
	}
}
