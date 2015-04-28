package com.ririjin.adminmobile;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.ririjin.adminmobile.APIUtils.APIUtils;
import com.ririjin.adminmobile.APIUtils.DataManager;
import com.ririjin.adminmobile.APIUtils.RJCache;
import com.ririjin.adminmobile.APIUtils.RJPostData;
import com.ririjin.adminmobile.APIUtils.RJPostHandler;

public class LoginActivity extends Activity {
	private static final String TAG = "MainActivity";

	EditText username_edit;
	EditText userpasword_edit;
	Button loginButton;
	Button offlineButton;
	CheckBox RemeberCheckBox;

	String username;
	String password;

	private static HttpClient httpClient = new DefaultHttpClient();
	public static String cookie = "";
	String apiUrl = APIUtils.APIURL;
	RJPostData postData;
	ProgressDialog dialog = null;

	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 0;

	Boolean SaveBoolean = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		dialog = new ProgressDialog(this);
		postData = new RJPostData(getApplicationContext(), apiUrl);

		offlineButton = (Button) findViewById(R.id.offline_btn);
		loginButton = (Button) findViewById(R.id.login_btn);
		username_edit = (EditText) findViewById(R.id.login_username);
		userpasword_edit = (EditText) findViewById(R.id.login_userpasword);
		RemeberCheckBox = (CheckBox) findViewById(R.id.remember_pass);

		offlineButton.setOnClickListener(listener);
		loginButton.setOnClickListener(listener);
		RemeberCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						// TODO Auto-generated method stub
						SaveBoolean = arg1;
					}
				});

		RJCache.initCacheData(getApplicationContext());
		if (RJCache.toGetDB("password") != null
				&& RJCache.toGetDB("username") != null
				&& !RJCache.toGetDB("username").equals("")
				&& !RJCache.toGetDB("password").equals("")) {
			AUTOLogin();
		}
	}

	private void AUTOLogin() {
		// TODO Auto-generated method stub
		dialog.setMessage("自动登陆中");
		dialog.show();
		dialog.setCancelable(false);// 设置点击屏幕Dialog不消失
		Login(RJCache.toGetDB("username"), RJCache.toGetDB("password"));
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.login_btn:
				dialog.setMessage("登陆中");
				dialog.show();
				dialog.setCancelable(false);// 设置点击屏幕Dialog不消失
				if (CheckData()) {
					username = username_edit.getText().toString();
					password = DataManager.getTwiceMD5(userpasword_edit
							.getText().toString());
					Login(username, password);
				}

				break;
			case R.id.offline_btn:
				MyApplication.OnLine = false;
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
				break;
			default:
				break;
			}
		}
	};

	// 校验输入的密码和账号
	protected boolean CheckData() {
		// TODO Auto-generated method stub
		if (username_edit.getText().toString() == null
				&& username_edit.getText().toString().equals("")) {
			return false;
		}
		if (userpasword_edit.getText().toString() == null
				&& userpasword_edit.getText().toString().equals("")) {
			return false;
		}
		return true;
	}

	private void Login(final String username, final String password) {
		String APIName = APIUtils.API_LOGIN;

		postData.HeadersPut("X-API", APIName);

		JSONObject body = new JSONObject();

		try {
			body.put("user_name", username);
			body.put("passwd", password);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		postData.setParamsData(body);

		postData.Post(new RJPostHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject json) {
				// TODO Auto-generated method stub

				Log.i(TAG,
						"接收数据成功 statusCode:" + statusCode + "  json:"
								+ json.toString());
				try {
					if (json.getString("code").equals("0")) {
						Toast.makeText(getApplicationContext(), "登陆成功",
								Toast.LENGTH_LONG).show();
						if (SaveBoolean) {
							RJCache.toSaveDB("username", username);
							RJCache.toSaveDB("password", password);
						}
						IsLive();
					} else {
						Toast.makeText(getApplicationContext(),
								json.getString("message"), Toast.LENGTH_LONG)
								.show();
						if (dialog != null && dialog.isShowing()) {
							dialog.dismiss();
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onFail(int statusCode, String msg) {
				// TODO Auto-generated method stub
				Log.i(TAG, "接收数据失败 statusCode:" + statusCode + "  msg:" + msg);
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
					Toast.makeText(getApplicationContext(), "登陆失败",
							Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	private void IsLive() {
		String APIName = APIUtils.API_ISLIVE;

		postData.HeadersPut("X-API", APIName);

		postData.Post(new RJPostHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject json) {
				// TODO Auto-generated method stub

				Log.i(TAG,
						"接收数据成功 statusCode:" + statusCode + "  json:"
								+ json.toString());

				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
					Toast.makeText(getApplicationContext(), "状态验证成功",
							Toast.LENGTH_LONG).show();

					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				}
			}

			@Override
			public void onFail(int statusCode, String msg) {
				// TODO Auto-generated method stub
				Log.i(TAG, "接收数据失败 statusCode:" + statusCode + "  msg:" + msg);
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
					Toast.makeText(getApplicationContext(), "状态验证失败",
							Toast.LENGTH_LONG).show();
				}
			}
		});

	}

}
