package me.bird.heroku.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.encode.DigestUtil;
import me.bird.heroku.utils.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.Restful;

@Before(Restful.class)
public class WeixinController extends Controller {

	private final Logger logger = LoggerFactory.getLogger(getClass());

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
		String result =  this.sign(timestamp, nonce);
		if (StringUtils.equals(echostr,result)){
			super.renderText(echostr);
			logger.info("微信平台签名消息验证成功！");
		}else {
			logger.info("微信平台签名消息验证失败！收到的加密签名{}，计算出的加密签名{}", signature, result);
			super.renderText("微信平台签名消息验证失败");
		}

	}

	private String sign(String timestamp, String nonce) {
		// 将获取到的参数放入数组
		String[] ArrTmp = { BaseConsts.WEIXIN_TOKEN, timestamp, nonce };
		// 按微信提供的方法，对数据内容进行排序
		Arrays.sort(ArrTmp);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ArrTmp.length; i++) {
			sb.append(ArrTmp[i]);
		}
		// 对排序后的字符串进行SHA-1加密
		return DigestUtil.sha1Hex(sb.toString());
	}

	public void save() {
		super.renderJson("POST中文测试,test");
	}

}
