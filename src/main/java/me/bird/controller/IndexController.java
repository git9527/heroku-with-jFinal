package me.bird.controller;

import me.bird.consts.BaseConsts;
import me.bird.utils.DateUtil;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class IndexController extends Controller{

	@ActionKey("/")
	public void index(){
		super.renderHtml("启动时间:" + DateUtil.toString(BaseConsts.START_TIME, DateUtil.yyyy_MM_dd_HH_mm_ss));
	}
	
	@ActionKey("/angular")
	public void angular(){
		super.render("angular.ftl");
	}
}
