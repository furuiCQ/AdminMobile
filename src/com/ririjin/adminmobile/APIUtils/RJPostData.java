package com.ririjin.adminmobile.APIUtils;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.TextHttpResponseHandler;

public class RJPostData {
	private final static String TAG = "RJPostData";
	private final static int POST_CODE_OK = 0;
	private final static int POST_CODE_NETERR = 5000;
	private final static int POST_CODE_TIMEOUT = 5001;
	private final static int POST_CODE_ERR = 5010;
	private int Timeout = 10 * 1000;
	private int ConnectTimeout = 10 * 1000;
	private String ContentType = "application/json";
	private String ApiUrl = "";
	private JSONObject JsonData = new JSONObject();
	private JSONObject JsonHeaders = new JSONObject();
	private Context context;
	private RJPostHandler Handler;

	public RJPostData(Context _context, String APIURL) {
		this.ApiUrl = APIURL;
		this.context = _context;
	}

	public void Post(RJPostHandler handler) {
		this.Handler = handler;

		if (this.ApiUrl == null || this.ApiUrl.equals("")) {
			Handler.onFail(POST_CODE_ERR, "PostUrl错误");
			return;
		}

		if (!networkIsAvailable(context)) {
			Handler.onFail(POST_CODE_NETERR, "网络连接失败");
			return;
		}

		AsyncHttpClient client = new AsyncHttpClient(80, 443);
		StringEntity stringEntity;

		try {
			stringEntity = new StringEntity(JsonData.toString(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Handler.onFail(POST_CODE_ERR, "提交参数错误");
			return;
		}

		setHeaders(client, JsonHeaders);
		client.addHeader("Cookie", RJCache.toGetDB("RIJIN_CACHE_HTTP_Cookies"));
		client.setTimeout(Timeout);
		client.setConnectTimeout(Timeout);
		client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());

		client.post(this.context, ApiUrl, stringEntity, ContentType,
				new TextHttpResponseHandler() {

					@Override
					public void onFailure(int statusCode, Header[] headers,
							String responseString, Throwable throwable) {
						// TODO Auto-generated method stub

						if (statusCode == 0) {
							Handler.onFail(POST_CODE_TIMEOUT, "网络连接超时");
						} else {
							Handler.onFail(statusCode, throwable.getMessage());
						}
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							String responseString) {
						// TODO Auto-generated method stub

						try {
							JSONObject json = new JSONObject(responseString);
							json.put("statuscode", statusCode);

							for (Header header : headers) {
								if (header.getName().equals("Set-Cookie")) {
									if (header.getValue() != null
											&& !header.getValue().equals("")) {
										RJCache.toSaveDB(
												"RIJIN_CACHE_HTTP_Cookies", ";"
														+ header.getValue());
									}
								}
							}

							Handler.onSuccess(POST_CODE_OK, json);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							// e.printStackTrace();
							Handler.onFail(POST_CODE_ERR, "接收数据错误");
						}

					}

				});

	}

	private void setHeaders(AsyncHttpClient client, JSONObject headers) {
		Iterator<?> i = headers.keys();

		while (i.hasNext()) {
			String key = (String) i.next();
			try {
				client.addHeader(key, headers.getString(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 网络连接是否可用
	 */
	public boolean networkIsAvailable(Context context) {
		ConnectivityManager cManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if (info == null) {
			return false;
		}
		if (info.isConnected()) {
			return true;
		}
		return false;
	}

	public int getTimeout() {
		return Timeout;
	}

	public void setTimeout(int timeout) {
		Timeout = timeout;
	}

	public int getConnectTimeout() {
		return ConnectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		ConnectTimeout = connectTimeout;
	}

	public String getContentType() {
		return ContentType;
	}

	public void setContentType(String contentType) {
		ContentType = contentType;
	}

	public String getApiUrl() {
		return ApiUrl;
	}

	public void setApiUrl(String apiUrl) {
		ApiUrl = apiUrl;
	}

	public JSONObject getParamsData() {
		return JsonData;
	}

	public void setParamsData(JSONObject jsonData) {
		JsonData = jsonData;
	}

	public void HeadersPut(String key, Object value) {
		try {
			JsonHeaders.put(key, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JSONObject getHeaders() {
		return JsonHeaders;
	}

	public void setHeaders(JSONObject jsonHeaders) {
		JsonHeaders = jsonHeaders;
	}

}
