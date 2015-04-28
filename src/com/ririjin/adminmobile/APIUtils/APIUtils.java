package com.ririjin.adminmobile.APIUtils;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class APIUtils {
	public static String APIURL = "https://api.rd.rijin.com/v1";
	private static HttpClient httpClient = new DefaultHttpClient();
	public static String cookie = "";

	public static String API_LOGIN = "rijin.manager.login";// 登陆接口
	public static String API_ISLIVE = "rijin.manager.isalive";// 查看在线状态接口
	public static String API_BAISC_SAVE = "rijin.admin.car.basic.save";// 保存车辆信息
	public static String API_BAISC_UPDATE = "rijin.admin.car.basic.save";// 保存车辆信息
	public static String API_CAR_LIST = "rijin.admin.car.list.get";// 车辆列表
	public static String API_CAR_GET = "rijin.admin.car.get";// 车辆详情
	
	
	
}
