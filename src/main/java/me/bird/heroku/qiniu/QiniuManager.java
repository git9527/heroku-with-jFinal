package me.bird.heroku.qiniu;

import me.bird.heroku.consts.BaseConsts;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class QiniuManager {

	public String getToken(String fileName){
		String originPutPolicy = "{\"scope\":\"my-bucket:fileName\",\"deadline\":1451491200,\"returnBody\":\"{\\\"name\\\":$(fname),\\\"size\\\":$(fsize),\\\"w\\\":$(imageInfo.width),\\\"h\\\":$(imageInfo.height),\\\"hash\\\":$(etag)}\"}";
		String putPolicy = originPutPolicy.replace("fileName", fileName);
		String encodedPutPolicy = Base64.encodeBase64URLSafeString(putPolicy.getBytes(BaseConsts.BASE_CHARSET));
		String sign = DigestUtils.shaHex(encodedPutPolicy.getBytes(BaseConsts.BASE_CHARSET));
		String encodedSign = Base64.encodeBase64URLSafeString(sign.getBytes(BaseConsts.BASE_CHARSET));
		return BaseConsts.APP_KEY_QINIU + ":" + encodedSign + ":" + BaseConsts.SECRET_KEY_QINIU;
	}
}
