package me.bird.heroku.controller;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.utils.DateUtil;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class IndexController extends Controller{

	@ActionKey("/")
	public void index(){
		super.renderHtml("启动时间:" + DateUtil.toString(BaseConsts.START_TIME, DateUtil.yyyy_MM_dd_HH_mm));
	}
}
