package com.ririjin.adminmobile.APIUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.SharedPreferences;

public class DataManager {
	private static String SILENAME = "AdminMobileData";

	public static void SaveStringData(Context context, String key, String value) {
		
		SharedPreferences share = context.getSharedPreferences(SILENAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = share.edit(); 
		edit.putString(key, value);
		edit.commit();
	}	
	public static String GetStringData(Context context, String key) {
		
		String value = "";
		SharedPreferences share = context.getSharedPreferences(SILENAME,
				Context.MODE_PRIVATE);
		value = share.getString(key, null);
		return value;
	}
	
	
	
	
	
	
	public static void SaveIntData(Context context, String key, int value) {
		
		SharedPreferences share = context.getSharedPreferences(SILENAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = share.edit(); 
		edit.putInt(key, value);
		edit.commit();
	}
	
	public static int GetIntData(Context context, String key) {
		
		int value;
		SharedPreferences share = context.getSharedPreferences(SILENAME,
				Context.MODE_PRIVATE);
		value = share.getInt(key, 0);
		return value;
	}
	
	
	
	
	
	public static void SaveBooLData(Context context, String key, Boolean value) {
		
		SharedPreferences share = context.getSharedPreferences(SILENAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = share.edit(); 
		edit.putBoolean(key, value);
		edit.commit();
	}
	public static Boolean GetBoolData(Context context, String key) {
		
		Boolean value;
		SharedPreferences share = context.getSharedPreferences(SILENAME,
				Context.MODE_PRIVATE);
		value = share.getBoolean(key, false);
		return value;
	}
	
	
	public static String getMD5(String string) {
		byte[] hash;

		try {
			hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}

		return hex.toString().toLowerCase();
	}
	
	public static String getTwiceMD5(String str){
		String strMD5="";
		
		strMD5=getMD5(getMD5(str));
		
		return strMD5;
	}
	
	
}
