package me.bird.heroku.controller;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.feisu.ChapterInfo;
import me.bird.heroku.feisu.FeisuManager;
import me.bird.heroku.utils.HttpUtil;
import me.bird.heroku.utils.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class NovelController extends Controller {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private FeisuManager feisuManager = new FeisuManager();

	@ActionKey(value = "/novel/feisu")
	public void feisuIndex() {
		String bookId = super.getPara(0);
		String chapterId = super.getPara(1);
		if (StringUtils.isEmpty(chapterId)) {
			this.catchBook(bookId);
			super.render("feisu/book.ftl");
		} else {
			this.catchChapter(bookId, chapterId);
			super.render("feisu/chapter.ftl");
		}
	}
	
	private void catchBook(String bookId){
		try {
			String originContent = new HttpUtil().getContent("http://www.feisuzw.com/Html/" + bookId, BaseConsts.ENCODING_OF_FEISU);
			String list = StringUtils.subStringBetween(originContent, "<div class=\"chapterlist\">", "</div>");
			super.setAttr("bookName", StringUtils.subStringBetween(originContent, "index.aspx\" title=\"", "\">"));
			super.setAttr("chapters", list.replace(".html", "").replace("href=\"", "href=\""+ bookId+"-"));
		} catch (Exception e) {
			logger.error("抓取小说发生异常,bookId:{}", bookId, e);
		}
	}

	private void catchChapter(String bookId, String chapterId) {
		try {
			String originContent = new HttpUtil().getContent("http://www.feisuzw.com/Html/" + bookId + "/" + chapterId + ".html", BaseConsts.ENCODING_OF_FEISU);
			ChapterInfo chapterInfo = new ChapterInfo();
			chapterInfo.setBookId(Integer.valueOf(bookId));
			chapterInfo.setChapterId(Integer.valueOf(chapterId));
			String prevPage = StringUtils.subStringBetween(originContent, " var prevpage = \"", ".html");
			String nextPage = StringUtils.subStringBetween(originContent, " var nextpage = \"", ".html");
			super.setAttr("bookId", bookId);
			super.setAttr("chapterId", chapterId);
			super.setAttr("prevPage", "index".equalsIgnoreCase(prevPage) ? "" : prevPage);
			super.setAttr("nextPage", "index".equalsIgnoreCase(nextPage) ? "" : nextPage);
			super.setAttr("chapterName", StringUtils.subStringBetween(originContent, "<title>", " - "));
			super.setAttr("bookName", StringUtils.subStringBetween(originContent, "title=\"", "\">"));
			super.setAttr("content", feisuManager.getChapterContent(chapterInfo));
		} catch (Exception e) {
			logger.error("抓取小说发生异常,bookId:{},chapterId:{}", bookId, chapterId, e);
		}
	}

}
