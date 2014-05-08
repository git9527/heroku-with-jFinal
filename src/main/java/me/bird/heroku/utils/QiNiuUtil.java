package me.bird.heroku.utils;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.encode.BASE64Encoder;
import me.bird.heroku.encode.DigestUtil;

public class QiNiuUtil {

	public static String getUploadToken(String fileName) {
		String originPutPolicy = "{\"scope\":\"space:fileName\",\"deadline\":1451491200,\"returnBody\":\"{\\\"name\\\":$(fname),\\\"size\\\":$(fsize),\\\"w\\\":$(imageInfo.width),\\\"h\\\":$(imageInfo.height),\\\"hash\\\":$(etag)}\"}";
		String putPolicy = originPutPolicy.replace("space", BaseConsts.QINIU_SPACE_NAME).replace("fileName", fileName);
		String encodedPutPolicy = BASE64Encoder.encode(putPolicy.getBytes(BaseConsts.CHARSET));
		String sign = DigestUtil.hmacSha1Hex(encodedPutPolicy, BaseConsts.QINIU_SECRET_KEY);
		String encodedSign = BASE64Encoder.encode(sign.getBytes(BaseConsts.CHARSET));
		return BaseConsts.QINIU_APP_KEY + ":" + encodedSign + ":" + encodedPutPolicy;
	}

}
