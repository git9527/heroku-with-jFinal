package me.bird.heroku.controller;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class IndexController extends Controller{

	@ActionKey("/")
	public void index(){
		super.renderHtml("Hello");
	}
}
