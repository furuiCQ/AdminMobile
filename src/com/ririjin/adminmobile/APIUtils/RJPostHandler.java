package com.ririjin.adminmobile.APIUtils;

import org.json.JSONObject;


public abstract class RJPostHandler {
	/**
	 * 数据请求成功
	 * */
	public abstract void onSuccess(int statusCode,JSONObject json);

	/**
	 * 数据请求失败
	 * */
	public abstract void onFail(int statusCode,String msg);
}
