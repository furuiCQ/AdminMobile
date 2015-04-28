package com.ririjin.adminmobile.asset_manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ririjin.adminmobile.LoginActivity;
import com.ririjin.adminmobile.MyApplication;
import com.ririjin.adminmobile.R;
import com.ririjin.adminmobile.UpdateCarActivity;
import com.ririjin.adminmobile.APIUtils.APIUtils;
import com.ririjin.adminmobile.APIUtils.RJPostData;
import com.ririjin.adminmobile.APIUtils.RJPostHandler;
import com.ririjin.adminmobile.adapter.CarList;
import com.ririjin.adminmobile.adapter.ListViewAdapter;

public class CarManager extends Activity {
	private static final String TAG = "CarManager";

	ListView listview;

	Button BackButton;

	String apiUrl = APIUtils.APIURL;
	RJPostData postData;

	List<CarList> list = new ArrayList<CarList>();
	ProgressDialog dialog = null;
	String assetId;

	/**
	 * @param args
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_manager);
		dialog = new ProgressDialog(this);

		postData = new RJPostData(getApplicationContext(), apiUrl);

		listview = (ListView) findViewById(R.id.car_list);

		BackButton = (Button) findViewById(R.id.back);

		if (MyApplication.OnLine) {
			dialog.setMessage("正在加载数据...");
			dialog.show();
			GetCarList();
		} else {
			dialog.setMessage("当前为离线模式，需要登录后才能查看数据..");
			
			dialog.setButton("登录", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(CarManager.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
					dialog.dismiss();
				}
			});
			dialog.setButton2("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			dialog.show();
		}

		listview.setOnItemClickListener(onItemclickListener);
		BackButton.setOnClickListener(listener);
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back:
				finish();
				break;

			default:
				break;
			}
		}
	};

	OnItemClickListener onItemclickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			// AlertDialog alertDialog=new
			assetId = list.get(position).AssetId;

			final AlertDialog.Builder builder = new AlertDialog.Builder(
					CarManager.this);

			builder.setTitle("操作");
			builder.setPositiveButton("编辑",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getApplicationContext(),
									UpdateCarActivity.class);

							intent.putExtra("assetId", assetId);

							startActivity(intent);
							dialog.dismiss();

						}
					});
			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							// TODO Auto-generated method stub

							dialog.dismiss();

						}
					});
			builder.show();
		}

	};

	private void GetCarList() {
		String APIName = APIUtils.API_CAR_LIST;

		postData.HeadersPut("X-API", APIName);

		postData.Post(new RJPostHandler() {

			@Override
			public void onSuccess(int statusCode, JSONObject json) {
				// TODO Auto-generated method stub

				Log.i(TAG,
						"接收数据成功 statusCode:" + statusCode + "  json:"
								+ json.toString());
				try {
					String result = json.getString("result").toString();
					Log.i(TAG, "接收数据成功 result:" + result);
					LoadListData(result);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(getApplicationContext(), "数据加载成功",
						Toast.LENGTH_LONG).show();
			}

			@Override
			public void onFail(int statusCode, String msg) {
				// TODO Auto-generated method stub
				Log.i(TAG, "接收数据失败 statusCode:" + statusCode + "  msg:" + msg);
				// if (statusCode==5001) {
				// GetCarList();
				// }
			}
		});
	}

	protected void LoadListData(String result) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject jsonObject = new JSONObject(result);
		String listData = jsonObject.getString("list");
		JSONArray jsonArray = new JSONArray(listData);

		for (int i = 0; i < jsonArray.length(); i++) {
			CarList carList = new CarList();

			JSONObject data = (JSONObject) jsonArray.get(i);
			carList.AssetId = data.getString("asset_id");
			carList.BizTypeId = data.getInt("biz_type_id");
			carList.BrandName = data.getString("brand_name");
			carList.ExpireDate = data.getString("expire_date");
			carList.RegisterDate = data.getString("register_date");
			carList.SerialName = data.getString("serial_name");
			carList.StatusId = data.getString("status_id");
			carList.TraderId = data.getString("trader_id");
			carList.TraderName = data.getString("trader_name");
			carList.CarLicenseNumber = data.getString("car_license_number");

			list.add(carList);
		}
		Log.e("list size====>", "" + list.size());
		ListViewAdapter adapter = new ListViewAdapter(getApplicationContext(),
				list);

		listview.setAdapter(adapter);

		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();

		}
	}

}
