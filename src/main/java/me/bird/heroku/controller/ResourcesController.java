package me.bird.heroku.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.utils.HttpUtil;
import me.bird.heroku.utils.IOUtils;
import me.bird.heroku.utils.StringUtils;
import me.bird.heroku.utils.SystemUtils;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class ResourcesController extends Controller {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@ActionKey("/resources/css")
	public void css() {
		String content = this.getContent("css", this.getKey());
		if (StringUtils.isEmpty(content)) {
			super.renderError(404);
		} else {
			super.renderText(content);
		}
	}

	@ActionKey("/resources/js")
	public void js() {
		String content = this.getContent("js", this.getKey());
		if (StringUtils.isEmpty(content)) {
			super.renderError(404);
		} else {
			super.renderText(content);
		}
	}

	@ActionKey("/resources/image")
	public void image() {
		String key = super.getPara();
		String suffix = StringUtils.subStringAfterLast(key, "-");
		key = key.replace("-" + suffix, "." + suffix);
		try {
			byte[] bytes = new HttpUtil().getContentBytes(BaseConsts.QINIU_RESOURCE_DOMAIN + key);
			super.getResponse().getOutputStream().write(bytes);
			super.renderNull();
		} catch (Exception e) {
			logger.error("获取资源出错:{}", key, e);
			super.renderError(404);
		}
	}

	private String getKey() {
		String key = super.getPara(0);
		String suffix = super.getPara(1);
		String userAgent = super.getRequest().getHeader("User-Agent");
		if (SystemUtils.isMobile(userAgent)) {
			key = key + "_m";
		}
		return key + "." + suffix;
	}

	private String getContent(String type, String key) {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		if (SystemUtils.isLocalDev()) {
			path = path + "../../src/main/webapp/resources/" + type + "/" + key;
		} else {
			path = path + "../../resources/" + type + "/" + key;
		}
		try {
			FileInputStream inputStream = new FileInputStream(path);
			return IOUtils.toString(inputStream, BaseConsts.ENCODING);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

}
