package me.bird.heroku.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class WeixinValidator extends Validator{

	@Override
	protected void validate(Controller c) {
		super.validateRequiredString("timestamp", "timestampMsg", "timestamp不能为空");
		super.validateRequiredString("nonce", "nonceMsg", "nonce不能为空");
	}

	@Override
	protected void handleError(Controller c) {
		c.redirect("/weixin");
	}

}
