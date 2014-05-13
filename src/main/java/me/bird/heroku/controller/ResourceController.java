package me.bird.heroku.controller;

import java.io.File;

import me.bird.heroku.utils.StringUtils;
import me.bird.heroku.utils.SystemUtils;

import com.jfinal.core.Controller;

public class ResourceController extends Controller {

	public void index() {
		String type = super.getPara(0);
		String key = super.getPara(1);
		String userAgent = super.getRequest().getHeader("User-Agent");
		String path = this.getClass().getClassLoader().getResource("").getPath();
		if (SystemUtils.isMobile(userAgent)) {
			key = StringUtils.subStringBeforeLast(key, ".") + "_m" + StringUtils.subStringAfterLast(key, ".");
		}
		if (SystemUtils.isLocalDev()) {
			path = path + "/../../resources/" + type + "/"+ key;
		} else {
			path = path + "/../../src/main/webapp/resources/" + type + "/" + key;
		}
		super.renderFile(new File(path));
	}
}
