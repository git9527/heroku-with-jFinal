package me.bird.heroku.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.utils.HttpUtil;
import me.bird.heroku.utils.ResourceUtils;
import me.bird.heroku.utils.StringUtils;
import me.bird.heroku.zhihu.LastestNews;
import me.bird.heroku.zhihu.News;
import me.bird.heroku.zhihu.ZhihuManager;
import me.bird.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.Restful;

@Before(Restful.class)
public class ZhiHuController extends Controller{

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private ZhihuManager zhihuManager = new ZhihuManager();
	
	private ResourceUtils resourceUtils = new ResourceUtils();

	public String show(){
		String date = super.getPara("date");
		try {
			boolean isToday = date.equals(DateUtil.toString(new Date(),DateUtil.yyyyMMdd));
			LastestNews lastestNews = zhihuManager.getCompleteBeforeNews(date);
			String dayBefore = DateUtil.getIntervalDateString(date, "-1D", DateUtil.yyyyMMdd);
			LastestNews beforeNews = zhihuManager.getCompleteBeforeNews(dayBefore);
			String dayAfter = DateUtil.getIntervalDateString(date, "1D", DateUtil.yyyyMMdd);
			this.updateImageUrl(lastestNews.getNews());
			this.updateImageUrl(beforeNews.getNews());
			super.setAttr("newsList", Arrays.asList(lastestNews,beforeNews));
			super.setAttr("before", dayBefore);
			if (!isToday){
				super.setAttr("after", dayAfter);
			}
		} catch (Exception e) {
			logger.error("发生异常",e);
		}
		return "zhihu/zhihu";
	}
	
	public String today(){
		try {
			LastestNews todayNews = zhihuManager.getCompleteLastestNews();
			String yestoday = DateUtil.getIntervalDateString(new Date(),"-1D", DateUtil.yyyyMMdd);
			LastestNews yestodayNews = zhihuManager.getCompleteBeforeNews(yestoday);
			this.updateImageUrl(todayNews.getNews());
			this.updateImageUrl(yestodayNews.getNews());
			super.setAttr("newsList", Arrays.asList(todayNews,yestodayNews));
			super.setAttr("before", yestoday);
			Thread.sleep(100);
		} catch (Exception e) {
			logger.error("发生异常",e);
		}
		return "zhihu/zhihu";
	}
	
	private void updateImageUrl(List<News> newsList) throws Exception{
		List<String> oldUrlList = new ArrayList<>();
		for (News news : newsList) {
			oldUrlList.add(news.getImage());
		}
		Map<String, String> map = resourceUtils.asyncGetNewUrl(oldUrlList);
		for (News news : newsList) {
			news.setImage(map.get(news.getImage()));
		}
	}

	public void init() {
		String id = super.getPara("id");
		try {
			String originContent = new HttpUtil().getContent("http://daily.zhihu.com/story/" + id, BaseConsts.ENCODING);
			String header = StringUtils.subStringBetween(originContent, "<body>", "<div class=\"main-wrap content-wrap\">");
			originContent = StringUtils.remove(originContent, header);
			String footer = StringUtils.subStringBetween(originContent, "<div class=\"qr\">", "<script src=");
			originContent = StringUtils.remove(originContent, footer);
			super.renderText(this.updateImageLocation(originContent));
		} catch (Exception e) {
			logger.error("抓取文章内容失败,id:{}", id, e);
			super.renderText(e.getMessage());
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
	
	private List<String> getImageList(String content,String start,String end){
		String[] imageArray = StringUtils.subStringsBetween(content, start, end);
		if (imageArray == null || imageArray.length == 0){
			return new ArrayList<String>();
		}else {
			return Arrays.asList(imageArray);
		}
	}
	
}
