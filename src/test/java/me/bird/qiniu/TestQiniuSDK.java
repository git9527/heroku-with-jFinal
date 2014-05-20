package me.bird.qiniu;

import java.util.Arrays;
import java.util.List;

import me.bird.consts.BaseConsts;
import me.bird.utils.SystemUtils;

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
import org.junit.Before;
import org.junit.Test;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.net.Http;
import com.qiniu.api.rs.BatchStatRet;
import com.qiniu.api.rs.Entry;
import com.qiniu.api.rs.EntryPath;
import com.qiniu.api.rs.RSClient;

public class TestQiniuSDK {

	private RSClient client;

	@Before
	public void init() {
		Http.setClient(getHttpClient());
		Mac mac = new Mac(BaseConsts.QINIU_APP_KEY, BaseConsts.QINIU_SECRET_KEY);
		client = new RSClient(mac);
	}

	@Test
	public void test_stat() {
		Entry statRet = client.stat(BaseConsts.QINIU_SPACE_NAME, "a-b-3.png");
		System.out.println(statRet);
	}
	
	@Test
	public void test_batch_stat() {
		//op=/stat/emhhbmdzbjphLWItNC5wbmc=&op=/stat/emhhbmdzbjphLWItMy5wbmc=
		//Authorization X4rXz25vYNuH5escSXCU2rxK_v-Zilv5lfLwfilH:47IarNwMU_ANW_p2tUmpfpA_XF0=
		EntryPath aPath = new EntryPath();
		aPath.bucket = BaseConsts.QINIU_SPACE_NAME;
		aPath.key = "a-b-3.png";
		EntryPath bPath = new EntryPath();
		bPath.bucket = BaseConsts.QINIU_SPACE_NAME;
		bPath.key = "a-b-4.png";
		List<EntryPath> list = Arrays.asList(aPath, bPath);
		BatchStatRet ret = client.batchStat(list);
		System.out.println(ret);
	}

	private HttpClient getHttpClient() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		// Increase max total connection to 200
		cm.setMaxTotal(200);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20);
		// Increase max connections for localhost:80 to 50
		HttpHost localhost = new HttpHost("locahost", 80);
		cm.setMaxPerRoute(new HttpRoute(localhost), 50);
		HttpClient client = new DefaultHttpClient(cm);
		if (SystemUtils.needProxy()){
			HttpHost proxy = new HttpHost("172.17.18.84", 8080);
			client.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
		}
		return client;
	}
}
