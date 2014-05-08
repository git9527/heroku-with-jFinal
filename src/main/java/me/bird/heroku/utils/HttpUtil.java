package me.bird.heroku.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import me.bird.heroku.consts.BaseConsts;

public class HttpUtil {

	private String crlf = "\r\n";

	private String boundary = "-------" + Long.toHexString(System.currentTimeMillis());

	public String getContent(String url, String encoding) throws Exception {
		HttpURLConnection connection = this.getConnection(url, "GET");
		return this.getResponseAndClose(connection, encoding);
	}

	public String postContent(String url, Map<String, String> headerMap, Map<String, String> paramMap, String encoding) throws Exception {
		HttpURLConnection connection = this.getConnection(url, "POST");
		for (String key : headerMap.keySet()) {
			connection.addRequestProperty(key, headerMap.get(key));
		}
		StringBuffer params = new StringBuffer();
		for (String paramkey : paramMap.keySet()) {
			params.append(paramkey).append("=").append(URLEncoder.encode(paramMap.get(paramkey), BaseConsts.ENCODING)).append("&");
		}
		params = params.deleteCharAt(params.length() - 1);
		this.writeBytesToStream(connection.getOutputStream(), params.toString().getBytes(BaseConsts.CHARSET));
		connection.connect();
		return this.getResponseAndClose(connection, encoding);
	}

	private HttpURLConnection getConnection(String url, String method) throws Exception {
		URL realUrl = new URL(url);
		HttpURLConnection connection;
		if (SystemUtils.needProxy()) {
			Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("172.17.18.84", 8080));
			connection = (HttpURLConnection) realUrl.openConnection(proxy);
		} else {
			connection = (HttpURLConnection) realUrl.openConnection();
		}
		connection.setConnectTimeout(2000);
		connection.setDoOutput(true);
		connection.setRequestMethod(method);
		return connection;
	}

	public String upload(String url, byte[] bytes) throws Exception {
		HttpURLConnection connection = this.getConnection(url, "POST");
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
		dataOut.writeBytes(this.getDataContent(RandomUtil.random(5) + ".jpg"));
		dataOut.write(bytes);
		dataOut.writeBytes(crlf + "--" + boundary + "--" + crlf);// 定义最后数据分隔线
		dataOut.flush();
		dataOut.close();
		connection.connect();
		return this.getResponseAndClose(connection, BaseConsts.ENCODING);
	}

	public String postImage(String url, byte[] bytes, String key) throws Exception {
		HttpURLConnection connection = this.getConnection(url, "POST");
		String fileName = RandomUtil.random(15);
		connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		connection.setRequestProperty("Host", "up.qiniu.com");
		connection.setRequestProperty("Content-Length", bytes.length+"");
		DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
		dataOut.writeBytes(crlf + crlf + "--" + boundary + crlf);
		dataOut.writeBytes(this.getContentDisposition("token", QiNiuUtil.getUploadToken(fileName)));
		dataOut.writeBytes(this.getContentDisposition("key", key));
		dataOut.writeBytes("Content-Disposition: form-data;name=\"file\";filename=\"" + fileName + "\"" + crlf);
		dataOut.writeBytes("Content-Type:application/octet-stream" + crlf);
		dataOut.writeBytes("Content-Transfer-Encoding: binary");
		//		dataOut.write(bytes);
		dataOut.writeBytes(crlf + "--" + boundary + "--" + crlf);
		dataOut.flush();
		dataOut.close();
		connection.connect();
		return this.getResponseAndClose(connection, BaseConsts.ENCODING);
	}

	private void writeBytesToStream(OutputStream outputStream, byte[] bytes) throws Exception {
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		dataOutputStream.write(bytes);
		dataOutputStream.flush();
		dataOutputStream.close();
	}

	private String getResponseAndClose(HttpURLConnection connection, String encoding) throws IOException {
		String result = IOUtils.toString(connection.getInputStream(), encoding);
		connection.disconnect();
		return result;
	}

	private String getDataContent(String fileName) {
		StringBuilder builder = new StringBuilder();
		builder.append("--").append(boundary).append(crlf);
		builder.append("Content-Disposition: form-data;name=\"myFile\";filename=\"" + fileName + "\"").append(crlf);
		builder.append("Content-Type:application/octet-stream").append(crlf).append(crlf);
		return builder.toString();
	}

	private String getContentDisposition(String key, String value) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(crlf);
		buffer.append(value).append(crlf);
		buffer.append("--").append(boundary).append(crlf).append(crlf);
		return buffer.toString();
	}
	
}
