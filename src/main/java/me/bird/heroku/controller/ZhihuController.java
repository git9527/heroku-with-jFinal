package me.bird.heroku.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.utils.DateUtil;
import me.bird.heroku.utils.HttpUtil;
import me.bird.heroku.utils.ResourceUtils;
import me.bird.heroku.utils.StringUtils;
import me.bird.heroku.zhihu.LastestNews;
import me.bird.heroku.zhihu.News;
import me.bird.heroku.zhihu.ZhihuManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class ZhihuController extends Controller {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private ZhihuManager zhihuManager = new ZhihuManager();

	private ResourceUtils resourceUtils = new ResourceUtils();

	public void index() {
		try {
			long start = System.currentTimeMillis();
			LastestNews todayNews = zhihuManager.getCompleteLastestNews();
			String yestoday = DateUtil.getIntervalDateString(new Date(), "-1D", DateUtil.yyyyMMdd);
			LastestNews yestodayNews = zhihuManager.getCompleteBeforeNews(yestoday);
			this.updateImageUrl(todayNews.getNews());
			this.updateImageUrl(yestodayNews.getNews());
			super.setAttr("newsList", Arrays.asList(todayNews, yestodayNews));
			super.setAttr("before", yestoday);
			logger.info("调用接口组装数据完成,共耗时:{}ms", System.currentTimeMillis() - start);
		} catch (Exception e) {
			logger.error("发生异常", e);
		}
		this.render("zhihu.ftl");
	}

	@ActionKey(value = "/zhihu/day")
	public void day() {
		String date = super.getPara(0);
		try {
			boolean isToday = date.equals(DateUtil.toString(new Date(), DateUtil.yyyyMMdd));
			LastestNews lastestNews = zhihuManager.getCompleteBeforeNews(date);
			String dayBefore = DateUtil.getIntervalDateString(date, "-1D", DateUtil.yyyyMMdd);
			LastestNews beforeNews = zhihuManager.getCompleteBeforeNews(dayBefore);
			String dayAfter = DateUtil.getIntervalDateString(date, "1D", DateUtil.yyyyMMdd);
			this.updateImageUrl(lastestNews.getNews());
			this.updateImageUrl(beforeNews.getNews());
			super.setAttr("newsList", Arrays.asList(lastestNews, beforeNews));
			super.setAttr("before", dayBefore);
			if (!isToday) {
				super.setAttr("after", dayAfter);
			}
		} catch (Exception e) {
			logger.error("发生异常", e);
		}
		this.render("zhihu.ftl");
	}

	@ActionKey(value = "/zhihu/story")
	public void story() {
		String id = super.getPara(0);
		try {
			String originContent = new HttpUtil().getContent("http://daily.zhihu.com/story/" + id, BaseConsts.ENCODING);
			String header = StringUtils.subStringBetween(originContent, "<body>", "<div class=\"main-wrap content-wrap\">");
			originContent = StringUtils.remove(originContent, header);
			String footer = StringUtils.subStringBetween(originContent, "<div class=\"qr\">", "<script src=");
			originContent = StringUtils.remove(originContent, footer);
			originContent = this.updateImageLocation(originContent);
			super.renderHtml(this.updateCssAndJs(originContent));
		} catch (Exception e) {
			logger.error("抓取文章内容失败,id:{}", id, e);
			super.renderError(404);
		}
	}

	@ActionKey(value = "/zhihu/answer")
	public void answer() {
		String data = super.getPara("data");
		try {
			String result = new HttpUtil().getContent("http://daily.zhihu.com/answer/count?data=" + data, BaseConsts.ENCODING);
			super.renderJson(result);
		} catch (Exception e) {
			logger.error("获取评论数失败:{}", data, e);
			super.renderError(404);
		}
	}

	private String updateImageLocation(String originContent) throws Exception {
		List<String> oldImageUrlList = new ArrayList<String>();
		oldImageUrlList.addAll(this.getImageList(originContent, "img src=\"", "\" alt"));
		oldImageUrlList.addAll(this.getImageList(originContent, "img class=\"content_image\" src=\"", "\" alt"));
		oldImageUrlList.addAll(this.getImageList(originContent, "img class=\"avatar\" src=\"", "\""));
		oldImageUrlList.addAll(this.getImageList(originContent, "img class=\"origin_image zh-lightbox-thumb\" src=\"", "\" alt"));
		Map<String, String> map = resourceUtils.asyncGetNewUrl(oldImageUrlList);
		for (String oldImageUrl : oldImageUrlList) {
			String newImageUrl = map.get(oldImageUrl);
			originContent = originContent.replace(oldImageUrl, newImageUrl);
			logger.info("图片地址更新成功,原始地址:{},更新地址:{}", oldImageUrl, newImageUrl);
		}
		return originContent;
	}

	private String updateCssAndJs(String originContent) {
		String context = super.getRequest().getContextPath();
		originContent = originContent.replace("http://static.daily.zhihu.com/css/share6.css", context + "/resources/css/share6.css");
		originContent = originContent.replace("http://news.at.zhihu.com/js/share.js", context + "/resources/js/share.js");
		return originContent;
	}

	private void updateImageUrl(List<News> newsList) throws Exception {
		List<String> oldUrlList = new ArrayList<>();
		for (News news : newsList) {
			oldUrlList.add(news.getImage());
		}
		Map<String, String> map = resourceUtils.asyncGetNewUrl(oldUrlList);
		for (News news : newsList) {
			news.setImage(map.get(news.getImage()));
		}
	}

	private List<String> getImageList(String content, String start, String end) {
		String[] imageArray = StringUtils.subStringsBetween(content, start, end);
		if (imageArray == null || imageArray.length == 0) {
			return new ArrayList<String>();
		} else {
			return Arrays.asList(imageArray);
		}
	}

}
