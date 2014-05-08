package me.bird.heroku.feisu;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.utils.HttpUtil;
import me.bird.heroku.utils.RandomUtil;
import me.bird.heroku.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeisuManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private HttpUtil simpleClient = new HttpUtil();

	public List<BookInfo> searchBook(String keyWord) throws Exception {
		List<BookInfo> list = new ArrayList<BookInfo>();
		String url = "http://www.feisuzw.com/book/search.aspx?SearchClass=1&SearchKey="
				+ URLEncoder.encode(keyWord, BaseConsts.ENCODING_OF_FEISU);
		String originContent = simpleClient.getContent(url, BaseConsts.ENCODING_OF_FEISU);
		String bookInfos = StringUtils.subStringBetween(originContent, "<div class=\"title\">每页显示50部小说 ― 默认按更新时间排序</div>",
				"<div class=\"pager\">");
		String[] bookArray = StringUtils.subStringsBetween(bookInfos, "clearfix\">", "...</div>");
		if (bookArray.length == 0)
			throw new RuntimeException();
		List<String> bookInfoList = this.randomSubList(bookArray);
		for (String bookInfo : bookInfoList) {
			BookInfo info = new BookInfo();
			info.setBookUrl(StringUtils.subStringBetween(bookInfo, "<div id=\"CListTitle\"><a href=\"", "\" target=\"_blank\""));
			info.setBookName(StringUtils.subStringBetween(bookInfo, "><b>", "</b></a>"));
			info.setAuthor(StringUtils.subStringBetween(bookInfo, ".aspx\">", "</a>"));
			bookInfo = StringUtils.subStringAfter(bookInfo, info.getAuthor());
			info.setCategory(StringUtils.subStringBetween(bookInfo, ".aspx\">", "</a> <a") + ","
					+ StringUtils.subStringBetween(bookInfo, "\" target=\"_blank\">", "</a>"));
			bookInfo = StringUtils.subStringAfter(bookInfo, "最新章节 >>>");
			info.setLastestChapter(StringUtils.subStringBetween(bookInfo, " target=\"_blank\">", "</a>"));
			info.setLastestChapterUrl(BaseConsts.FEISU_DOMAIN + StringUtils.subStringBetween(bookInfo, "<a href=\"", "\" target"));
			info.setLastUpdate(StringUtils.subStringBetween(bookInfo, "</a> | ", " 更新"));
			info.setBrief(StringUtils.subStringAfter(bookInfo, "<div id=\"CListText\">") + "...");
			info.setContentUrl(BaseConsts.MY_DOMAIN + "/novel/feisu"
					+ StringUtils.subStringBetween(info.getLastestChapterUrl(), "Html", ".html"));
			list.add(info);
		}
		// this.completeBookInfo(list);
		return list;
	}

	public String getChapterContent(ChapterInfo info) {
		try {
			CatchTask task = new CatchTask(info, new CountDownLatch(1));
			return task.getContentWithHtml();
		} catch (Exception e) {
			logger.error("抓取文章内容失败书本id:{},章节id:{}", info.getBookId(), info.getChapterId(), e);
			return "<a href=\"http://www.baidu.com\">飞速网</a>抓取发生异常，速度与我联系";
		}
	}

	// private void completeBookInfo(List<BookInfo> bookInfoList) throws
	// InterruptedException {
	// CountDownLatch latch = new CountDownLatch(bookInfoList.size());
	// for (BookInfo bookInfo : bookInfoList) {
	// pool.execute(new CatchImageTask(bookInfo, latch));
	// }
	// latch.await();
	// }

	private List<String> randomSubList(String[] originArray) {
		List<String> list = new ArrayList<String>();
		Set<Integer> randomSet = RandomUtil.getRandomIndex(originArray.length);
		for (Integer index : randomSet) {
			list.add(originArray[index]);
		}
		return list;
	}
}
