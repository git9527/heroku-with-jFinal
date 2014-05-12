package me.bird.heroku.qiniu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bird.heroku.consts.BaseConsts;
import me.bird.heroku.encode.BASE64Encoder;
import me.bird.heroku.encode.DigestUtil;
import me.bird.heroku.utils.HttpUtil;
import me.bird.heroku.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class QiNiuManager {

	private static String CRLF = "\r\n";

	private static String UPLOAD_HOST = "http://up.qiniu.com";

	private static String STAT_HOST = "http://rs.qbox.me";

	public String makeUploadToken(String policy) {
		String encodedPutPolicy = BASE64Encoder.encodeUrlSafe(policy.getBytes(BaseConsts.CHARSET));
		byte[] signBytes = DigestUtil.hmacSha1(encodedPutPolicy, BaseConsts.QINIU_SECRET_KEY);
		String encodedSign = BASE64Encoder.encodeUrlSafe(signBytes);
		return BaseConsts.QINIU_APP_KEY + ":" + encodedSign + ":" + encodedPutPolicy;
	}
	
	public String makeAccessToken(String url,byte[] content){
		byte[] urlBytes = (url + "\n").getBytes();
		byte[] totalBytes = new byte[urlBytes.length + content.length];
		System.arraycopy(urlBytes, 0, totalBytes, 0, urlBytes.length);
		System.arraycopy(content, 0, totalBytes, urlBytes.length, content.length);
		byte[] signBytes = DigestUtil.hmacSha1(totalBytes, BaseConsts.QINIU_SECRET_KEY);
		String encodedSign = BASE64Encoder.encodeUrlSafe(signBytes);
		return BaseConsts.QINIU_APP_KEY + ":" + encodedSign;
	}

	public String makeAccessToken(String prefix, String key) {
		String signedStr = prefix + this.getEncodedKey(key) + "\n";
		byte[] signBytes = DigestUtil.hmacSha1(signedStr, BaseConsts.QINIU_SECRET_KEY);
		String encodedSign = BASE64Encoder.encodeUrlSafe(signBytes);
		return BaseConsts.QINIU_APP_KEY + ":" + encodedSign;
	}

	public String makeUploadToken() {
		return this.makeUploadToken("{\"scope\":\"space\",\"deadline\":1451491200}".replace("space", BaseConsts.QINIU_SPACE_NAME));
	}

	public String getEncodedKey(String key) {
		return BASE64Encoder.encodeUrlSafe((BaseConsts.QINIU_SPACE_NAME + ":" + key).getBytes());
	}

	public List<ResourceInfo> getBatchStat(List<String> keyList) throws Exception {
		String completeUrl = STAT_HOST + "/batch";
		StringBuilder builder = new StringBuilder();
		for (String key : keyList) {
			builder.append("op=/stat/" + this.getEncodedKey(key) + "&");
		}
		builder.deleteCharAt(builder.length() - 1);
		Map<String, String> headerMap = new HashMap<>();
		byte[] data = builder.toString().getBytes();
		headerMap.put("Content-Type", "application/x-www-form-urlencoded");
		headerMap.put("Authorization", "QBox " + this.makeAccessToken("/batch", data));
		String response = new HttpUtil().postContent(completeUrl, headerMap,data, BaseConsts.ENCODING);
		return this.getResourceInfosFromJson(response);
	}
	
	private List<ResourceInfo> getResourceInfosFromJson(String response) throws Exception{
		List<ResourceInfo> list = new ArrayList<>();
		JSONTokener tokens = new JSONTokener(response);
		JSONArray arr = new JSONArray(tokens);
		for (int i = 0; i < arr.length(); i++) {
			JSONObject jsonObj = arr.getJSONObject(i);
			if (jsonObj.has("code") && jsonObj.has("data")) {
				int code = jsonObj.getInt("code");
				JSONObject body = jsonObj.getJSONObject("data");
				ResourceInfo info = new ResourceInfo(code, body.toString());
				list.add(info);
			} else {
				new JSONException("Bad BatchStat result!");
			}
		}
		return list;
	}

	public String getSingleStat(String key) throws Exception {
		String completeUrl = STAT_HOST + "/stat/" + this.getEncodedKey(key);
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("User-Agent", "qiniu java-sdk v6.0.0");
		headerMap.put("Authorization", "QBox " + this.makeAccessToken("/stat/", key));
		return new HttpUtil().getContent(completeUrl, headerMap, BaseConsts.ENCODING);
	}

	public String postImage(byte[] data, String url) throws Exception {
		Map<String, String> headerMap = new HashMap<String, String>();
		String boundary = "-------" + Long.toHexString(System.currentTimeMillis());
		headerMap.put("Host", UPLOAD_HOST);
		headerMap.put("Content-Type", "multipart/form-data; boundary=" + boundary);
		byte[] dataContent = this.getUploadContent(data, boundary, url.replace("/", "-"));
		return new HttpUtil().postContent(UPLOAD_HOST, headerMap, dataContent, BaseConsts.ENCODING);
	}

	/**
	 * 生成类似于以下结构的正文 ---------145deb54a22 Content-Disposition: form-data;
	 * name="token"
	 * 
	 * tokenString ---------145deb54a22 Content-Disposition: form-data;
	 * name="key"
	 * 
	 * test.png ---------145deb54a22
	 * 
	 * Content-Disposition:form-data;name="file";filename="002886003200239"
	 * Content-Type:application/octet-stream Content-Transfer-Encoding: binary
	 * 
	 * 虚拟图片 ---------145deb54a22--
	 * 
	 * @param sourceData
	 * @param boundary
	 * @return
	 */
	public byte[] getUploadContent(byte[] sourceData, String boundary, String key) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("--" + boundary + CRLF);
		buffer.append("Content-Disposition: form-data; name=\"token\"").append(CRLF);
		buffer.append(CRLF).append(this.makeUploadToken()).append(CRLF);
		buffer.append("--").append(boundary).append(CRLF);
		buffer.append("Content-Disposition: form-data; name=\"key\"").append(CRLF);
		buffer.append(CRLF).append(key).append(CRLF);
		buffer.append("--").append(boundary).append(CRLF);
		buffer.append("Content-Disposition: form-data;name=\"file\";filename=\"" + StringUtils.subStringAfterLast(key, "-") + "\"" + CRLF);
		buffer.append("Content-Type:application/octet-stream" + CRLF);
		buffer.append("Content-Transfer-Encoding: binary" + CRLF + CRLF);
		byte[] startData = buffer.toString().getBytes();
		byte[] endData = (CRLF + "--" + boundary + "--" + CRLF + CRLF).getBytes();
		byte[] completeData = new byte[startData.length + sourceData.length + endData.length];
		System.arraycopy(startData, 0, completeData, 0, startData.length);
		System.arraycopy(sourceData, 0, completeData, startData.length, sourceData.length);
		System.arraycopy(endData, 0, completeData, startData.length + sourceData.length, endData.length);
		return completeData;
	}
}
