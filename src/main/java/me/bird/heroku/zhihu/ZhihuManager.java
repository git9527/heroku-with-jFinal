package me.bird.heroku.zhihu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.utils.HttpUtil;
import me.bird.heroku.utils.JsonUtils;
import me.bird.heroku.utils.RandomUtil;
import me.bird.heroku.utils.ResourceUtils;
import me.bird.heroku.utils.StringUtils;
import me.bird.util.DateUtil;

public class ZhihuManager {

	private HttpUtil httpUtil = new HttpUtil();

	private static String LASTEST_NEWS_URL = "http://news-at.zhihu.com/api/2/news/latest";

	private static String BEFORE_NEWS_URL = "http://news.at.zhihu.com/api/2/news/before/";

	private static String HOT_NEWS_URL = "http://news-at.zhihu.com/api/2/news/hot";

	public LastestNews getLastestNews() throws Exception {
		LastestNews news = this.getCompleteLastestNews();
		news.setNews(this.updateImageUrl(news.getNews()));
		return news;
	}

	public LastestNews getCompleteLastestNews() throws Exception {
		String jsonResult = httpUtil.getContent(LASTEST_NEWS_URL, BaseConsts.ENCODING);
		return new JsonUtils().fromJson(jsonResult, LastestNews.class);
	}

	public HotNews getHotNews() throws Exception {
		String jsonResult = httpUtil.getContent(HOT_NEWS_URL, BaseConsts.ENCODING);
		HotNews hotNews = new JsonUtils().fromJson(jsonResult, HotNews.class);
		hotNews.setRecent(this.updateImageUrl(hotNews.getRecent()));
		return hotNews;
	}

	public LastestNews getBeforeNews(String dayString) throws Exception {
		LastestNews news = this.getCompleteBeforeNews(dayString);
		news.setNews(this.updateImageUrl(news.getNews()));
		return news;
	}

	public LastestNews getCompleteBeforeNews(String dayString) throws Exception {
		String afterOneDay = DateUtil.getIntervalDateString(new Date(), "1D", DateUtil.yyyyMMdd);
		String jsonResult = httpUtil.getContent(BEFORE_NEWS_URL + afterOneDay, BaseConsts.ENCODING);
		return new JsonUtils().fromJson(jsonResult, LastestNews.class);
	}

	private List<News> updateImageUrl(List<News> originList) throws Exception {
		List<News> list = this.randomSubList(originList);
		List<String> oldUrls = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			News news = list.get(i);
			boolean catchThumbnail = i != 0 || StringUtils.isEmpty(news.getImage());
			if (catchThumbnail) {
				oldUrls.add(news.getThumbnail());
			} else {
				oldUrls.add(news.getImage());
			}
		}
		Map<String, String> resultMap = new ResourceUtils().asyncGetNewUrl(oldUrls);
		for (News news : list) {
			if (resultMap.containsKey(news.getImage())) {
				news.setImage(resultMap.get(news.getImage()));
			} else {
				news.setThumbnail(resultMap.get(news.getThumbnail()));
			}
		}
		return list;
	}

	private List<News> randomSubList(List<News> originList) {
		List<News> newList = new ArrayList<News>();
		Set<Integer> randomSet = RandomUtil.getRandomIndex(originList.size());
		for (Integer index : randomSet) {
			newList.add(originList.get(index));
		}
		return newList;
	}
}
