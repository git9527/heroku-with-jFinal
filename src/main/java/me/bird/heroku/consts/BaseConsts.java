package me.bird.heroku.consts;

import java.nio.charset.Charset;

public class BaseConsts {

	public static String CONTROLLER_SUFFIX = "Controller";
	
	public static String CONTROLLER_BASE_PACKAGE = "me.bird.heroku.controller";
	
	public static String WEIXIN_TOKEN = "zhangsnWeiXinToken";
	
	public static String ENCODING = "UTF-8";
	
	public static String ENCODING_OF_FEISU = "GB2312";
	
	public static Charset CHARSET = Charset.forName(ENCODING);
	
	public static String MY_DOMAIN = "http://weixinzhangsn.duapp.com";
	
	public static String FEISU_DOMAIN = "http://www.feisuzw.com";
	
	public final static int WEIXIN_NEWS_MAX_ITEM = 10;
	
	public static String QINIU_SPACE_NAME = "zhangsn";
	
	public static String QINIU_APP_KEY = "X4rXz25vYNuH5escSXCU2rxK_v-Zilv5lfLwfilH";
	
	public static String QINIU_SECRET_KEY = "GD6qojtiZExnhFuuYcHONLzs7xy3VUUcvX4Qzgm6";
	
}
