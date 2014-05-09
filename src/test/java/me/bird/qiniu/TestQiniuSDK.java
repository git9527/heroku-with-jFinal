package me.bird.qiniu;

import me.bird.heroku.consts.BaseConsts;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.junit.Test;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.net.Http;
import com.qiniu.api.rs.Entry;
import com.qiniu.api.rs.RSClient;

public class TestQiniuSDK {

	@Test
	public void test_stat() {
		Http.setClient(getHttpClient());
		Mac mac = new Mac(BaseConsts.QINIU_APP_KEY, BaseConsts.QINIU_SECRET_KEY);
		RSClient client = new RSClient(mac);
		Entry statRet = client.stat(BaseConsts.QINIU_SPACE_NAME, "a-b-3.png");
		System.out.println(statRet);
	}
	
	private HttpClient getHttpClient(){
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory
				.getSocketFactory()));

		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(
				schemeRegistry);
		// Increase max total connection to 200
		cm.setMaxTotal(200);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20);
		// Increase max connections for localhost:80 to 50
		HttpHost localhost = new HttpHost("locahost", 80);
		cm.setMaxPerRoute(new HttpRoute(localhost), 50);
		HttpHost proxy = new HttpHost("172.17.18.84",8080);
		HttpClient client = new DefaultHttpClient(cm);
		client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
		return client;
	}
}
