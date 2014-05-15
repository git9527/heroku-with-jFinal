package me.bird.heroku.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.qiniu.QiNiuManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceUtils {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private ExecutorService pool = Executors.newFixedThreadPool(10);

	public Map<String, String> asyncGetNewUrl(List<String> originUrlList) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		for (String oldUrl : originUrlList) {
			pool.execute(new CatchTask(oldUrl));
			map.put(oldUrl, this.getNewUrl(oldUrl));
		}
		return map;
	}

	private String getNewUrl(String oldUrl) {
		String pathString = StringUtils.subStringAfter(StringUtils.subStringAfter(oldUrl, "http://"), "/").replace("/", "-");
		return BaseConsts.QINIU_RESOURCE_DOMAIN + pathString;
	}

	public String syncGetNewUrl(String oldUrl) {
		new CatchTask(oldUrl).run();
		return this.getNewUrl(oldUrl);
	}
	
	public static String getStringContent(String type, String key) {
		return new String(getByteContent(type, key), BaseConsts.CHARSET);
	}

	public static byte[] getByteContent(String type, String key) {
		String path = ResourceUtils.class.getClassLoader().getResource("").getPath();
		if (SystemUtils.isLocalDev()) {
			path = path + "../../src/main/webapp/resources/" + type + "/" + key;
		} else {
			path = path + "../../resources/" + type + "/" + key;
		}
		try {
			FileInputStream inputStream = new FileInputStream(path);
			return IOUtils.toBytes(inputStream);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	class CatchTask implements Runnable {

		private String oldUrl;

		private QiNiuManager qiNiuManager = new QiNiuManager();

		public CatchTask(String oldUrl) {
			this.oldUrl = oldUrl;
		}

		@Override
		public void run() {
			String key = StringUtils.subStringAfter(StringUtils.subStringAfter(oldUrl, "http://"), "/").replace("/", "-");
			if (qiNiuManager.keyExist(key)) {
				logger.debug("该资源已存在:{},无需抓取", key);
			} else {
				try {
					byte[] data = new HttpUtil().getContentBytes(oldUrl);
					qiNiuManager.postImage(data, key);
					logger.debug("获取资源成功,key:{},size:{}", key, data.length);
				} catch (Exception e) {
					logger.error("获取资源失败,url:{}", oldUrl, e);
				}
			}
		}
	}
}
