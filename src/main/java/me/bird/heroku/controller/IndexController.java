package me.bird.heroku.controller;

import me.bird.heroku.consts.BaseConsts;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class IndexController extends Controller{

	@ActionKey("/")
	public void index(){
		super.renderHtml("启动时间:" + BaseConsts.START_TIME);
	}
}
