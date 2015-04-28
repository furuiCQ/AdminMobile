package com.ririjin.adminmobile.APIUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class RJCache {
	private static SharedPreferences RJCacheData; // 缓存数据
	private static String RJCACHE_APPKEY = "";
	private static Context _context;

	/**
	 * 初始化缓存
	 * */
	public static void initCacheData(Context c) {
		RJCACHE_APPKEY = c.getPackageName();
		_context = c;
		RJCacheData = _context.getSharedPreferences(RJCACHE_APPKEY,
				_context.MODE_PRIVATE);

		Log.i("RJCache", RJCACHE_APPKEY);
	}

	/**
	 * 保存数据
	 * */
	public static boolean toSaveDB(String strKey, String strValue) {
		Editor editor = RJCacheData.edit();

		editor.putString(strKey, strValue);
		return editor.commit();
	}

	/**
	 * 获取数据
	 * */
	public static String toGetDB(String strKey){
		if (RJCacheData.getString(strKey, null)!=null) {
			return RJCacheData.getString(strKey, null);

		}else{
			return null;
		}
		
	}

	/**
	 * 删除数据
	 * */
	public static boolean toDelDB(String strKey) {
		Editor editor = RJCacheData.edit();

		editor.remove(strKey);

		return editor.commit();
	}

	/**
	 * 数据是否存在
	 * */
	public static boolean DBIsExist(String strKey) {
		return RJCacheData.contains(strKey);
	}

	public static String getRJCacheKey() {
		return RJCACHE_APPKEY;
	}
}
