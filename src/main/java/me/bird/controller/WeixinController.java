package me.bird.controller;

import me.bird.utils.StringUtils;
import me.bird.weixin.WeixinManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.Restful;

@Before(Restful.class)
public class WeixinController extends Controller {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private WeixinManager weixinManager = new WeixinManager();

	public void index() {
		String timestamp = super.getPara("timestamp");
		String nonce = super.getPara("nonce");
		String signature = super.getPara("signature");
		String echostr = super.getPara("echostr");
		logger.info("收到的微信加密签名:{},时间戳{},随机数{},随机字符串{}", signature, timestamp, nonce, echostr);
		if (StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce)) {
			super.renderText("微信平台签名消息验证失败！,参数不完整");
			return;
		}
		String result =  weixinManager.checkAuthentication(timestamp, nonce);
		if (StringUtils.equals(signature,result)){
			super.renderText(echostr);
			logger.info("微信平台签名消息验证成功！");
		}else {
			logger.info("微信平台签名消息验证失败！收到的加密签名{}，计算出的加密签名{}", signature, result);
			super.renderText("微信平台签名消息验证失败");
		}

	}

	public void save() {
		super.renderJson("POST中文测试,test");
	}

}
