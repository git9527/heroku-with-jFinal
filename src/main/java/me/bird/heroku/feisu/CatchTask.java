package me.bird.heroku.feisu;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.utils.HttpUtil;
import me.bird.heroku.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatchTask implements Runnable {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ChapterInfo chapterInfo;

	private CountDownLatch latch;

	public CatchTask(ChapterInfo chapterInfo, CountDownLatch latch) {
		this.chapterInfo = chapterInfo;
		this.latch = latch;
	}

	public String getChapterContent() throws Exception {
		return this.getContentWithHtml().replace("</p><p>", "\n").replace("﻿<p>", "\t").replace("</p>", "\n");
	}

	public String getContentWithHtml() throws Exception {
		String refererUrl = "http://www.feisuzw.com/Html/" + chapterInfo.getBookId() + "/" + chapterInfo.getChapterId() + ".html";
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Referer", refererUrl);
		headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("bookid", chapterInfo.getBookId() + "");
		paramMap.put("chapterid", chapterInfo.getChapterId() + "");
		String result = new HttpUtil().postContent(this.getContentUrl(), headerMap, paramMap, BaseConsts.BASE_ENCODING);
		return StringUtils.subStringBeforeLast(result, "看无广告");
	}

	public String getContentUrl() throws Exception {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		String script = new HttpUtil().getContent("http://www.feisuzw.com/skin/hongxiu/include/ping.php", BaseConsts.ENCODING_OF_FEISU);
		engine.eval(script);
		Invocable invocable = (Invocable) engine;
		Object object = invocable.invokeFunction("iHash", this.chapterInfo.getBookId() + "", this.chapterInfo.getChapterId() + "");
		return "http://www.feisuzw.com/skin/hongxiu/include/fe1sushow.php?r=" + object.toString();
	}

	@Override
	public void run() {
		long start = System.currentTimeMillis();
		try {
			String realContent = this.getChapterContent();
			chapterInfo.setContent(realContent);
			int span = chapterInfo.getUpdateNumber() - realContent.length();
			double deviation = (span > 0) ? span / chapterInfo.getUpdateNumber() : -span / chapterInfo.getUpdateNumber();
			if (deviation > 0.1) {
				logger.info("误差超过10%:{},真实值:{},预期值:{}", chapterInfo.getTitle(), chapterInfo.getUpdateNumber(), realContent.length());
			}
			logger.info("[{}]抓取完成,耗时{}ms", chapterInfo.getTitle(), System.currentTimeMillis() - start);
		} catch (Exception e) {
			chapterInfo.setContent("发生异常:" + e.getMessage());
			logger.error("[{}]抓取发生异常,耗时{}ms", chapterInfo.getTitle(), System.currentTimeMillis() - start, e);
		}
		latch.countDown();
	}

}
