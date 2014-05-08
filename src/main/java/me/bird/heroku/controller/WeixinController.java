package me.bird.heroku.controller;

import java.util.Arrays;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.utils.DigestUtil;
import me.bird.heroku.utils.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.Restful;

@Before(Restful.class)
public class WeixinController extends Controller {

	public void index() {
		String timestamp = super.getPara("timestamp");
		String nonce = super.getPara("nonce");
		if (StringUtils.isEmpty(timestamp)){
			super.renderText("timestamp不能为空");
			return;
		}
		if (StringUtils.isEmpty(nonce)){
			super.renderText("nonce不能为空");
			return;
		}
		// 将获取到的参数放入数组
		String[] ArrTmp = { BaseConsts.WEIXIN_TOKEN, timestamp, nonce };
		// 按微信提供的方法，对数据内容进行排序
		Arrays.sort(ArrTmp);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ArrTmp.length; i++) {
			sb.append(ArrTmp[i]);
		}
		// 对排序后的字符串进行SHA-1加密
		super.renderText(DigestUtil.getInstance().encipher(sb.toString()));
	}

	public void save() {
		super.renderJson("POST中文测试,test");
	}

}
