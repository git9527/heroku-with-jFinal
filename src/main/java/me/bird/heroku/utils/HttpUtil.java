package me.bird.heroku.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.Map;

public class HttpUtil {

	private static String METHOD_GET = "GET";

	private static String METHOD_POST = "POST";

	public byte[] getContentBytes(String url) throws Exception {
		HttpURLConnection connection = this.getConnection(url, METHOD_GET);
		return this.getBytesResponseAndClose(connection);
	}

	public String getContent(String url, String encoding) throws Exception {
		HttpURLConnection connection = this.getConnection(url, METHOD_GET);
		return this.getStringResponseAndClose(connection, encoding);
	}

	public String getContent(String url, Map<String, String> headerMap, String encoding) throws Exception {
		HttpURLConnection connection = this.getConnection(url, METHOD_GET, headerMap);
		return this.getStringResponseAndClose(connection, encoding);
	}

	public String postContent(String url, Map<String, String> headerMap, String encoding) throws Exception {
		HttpURLConnection connection = this.getConnection(url, METHOD_POST, headerMap);
		connection.connect();
		return this.getStringResponseAndClose(connection, encoding);
	}

	public String postContent(String url, Map<String, String> headerMap, byte[] content, String encoding) throws Exception {
		HttpURLConnection connection = this.getConnection(url, METHOD_POST, headerMap);
		this.writeBytesToStream(connection.getOutputStream(), content);
		connection.connect();
		return this.getStringResponseAndClose(connection, encoding);
	}

	public HttpURLConnection getConnection(String url, String method, Map<String, String> headerMap) throws Exception {
		HttpURLConnection connection = this.getConnection(url, method);
		for (String key : headerMap.keySet()) {
			connection.addRequestProperty(key, headerMap.get(key));
		}
		return connection;
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

	private void writeBytesToStream(OutputStream outputStream, byte[] bytes) throws Exception {
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		dataOutputStream.write(bytes);
		dataOutputStream.flush();
		dataOutputStream.close();
	}

	private byte[] getBytesResponseAndClose(HttpURLConnection connection) throws IOException {
		byte[] bytes = IOUtils.toBytes(connection.getInputStream());
		connection.disconnect();
		return bytes;
	}

	private String getStringResponseAndClose(HttpURLConnection connection, String encoding) throws IOException {
		String result = IOUtils.toString(connection.getInputStream(), encoding);
		connection.disconnect();
		return result;
	}

}
