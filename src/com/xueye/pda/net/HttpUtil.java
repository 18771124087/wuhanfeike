package com.xueye.pda.net;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.net.ParseException;
import android.util.Log;

/**
 * @author wok
 * 
 *  http工具类，负责进行http通信服务
 **/
public class HttpUtil {
	private final static int timeout = 30 * 1000;


	/**
	 * get methord
	 */
	public static String httpGet(String httpUrl) {
		String result = ""; // 通信结果数据

		// 创建http客户端
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// 设置网络参数
		HttpParams params = httpclient.getParams();
		// 设置连接超时
		HttpConnectionParams.setConnectionTimeout(params, timeout);
		// 设置读取超时
		HttpConnectionParams.setSoTimeout(params, timeout);

		try {
			HttpGet httpget = new HttpGet(httpUrl);
//			Log.e("test", "url=" + httpUrl);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();// 得到http的内容
			result = EntityUtils.toString(response.getEntity());// 得到具体的返回值，一般是xml文件
			if (entity != null) {
				entity.consumeContent();// 如果entity不为空，则释放内存空间
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return "ClientProtocolException";
		} catch (ParseException e) {
			e.printStackTrace();
			return "ParseException";
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return "IllegalArgumentException";
		} catch (IOException e) {
			e.printStackTrace();
			return "网络异常!";
		}
//		Log.e("test", "result = " + result);
		return result;
	}

	/**
	 * http post methord
	 */
	public static String httpPost(String httpUrl, String jsonData) {
		String result = ""; // 通信结果数据

		DefaultHttpClient httpclient = new DefaultHttpClient();
		// 设置网络参数
		HttpParams params = httpclient.getParams();
		// 设置连接超时
		HttpConnectionParams.setConnectionTimeout(params, timeout);
		// 设置读取超时
		HttpConnectionParams.setSoTimeout(params, timeout);
		
		try {
			
			HttpPost httpPost = new HttpPost(httpUrl);// 进行httppost方式实例化
			StringEntity stringEntity = new StringEntity(jsonData, "utf-8");
			httpPost.setEntity(stringEntity);
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();// 得到http的内容
			result = EntityUtils.toString(response.getEntity());// 得到具体的返回值
			entity.consumeContent();// 如果entity不为空，则释放内存空间
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}

//		Log.e("=====gz=====", "result = " + result);
		return result;
	}
	

}
