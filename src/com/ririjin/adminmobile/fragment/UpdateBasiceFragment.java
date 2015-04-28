package com.ririjin.adminmobile.fragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ririjin.adminmobile.ActualBorrowMoneyCalculator;
import com.ririjin.adminmobile.AddCarAcitivity;
import com.ririjin.adminmobile.R;
import com.ririjin.adminmobile.UpdateCarActivity;
import com.ririjin.adminmobile.APIUtils.APIUtils;
import com.ririjin.adminmobile.APIUtils.RJPostData;
import com.ririjin.adminmobile.APIUtils.RJPostHandler;
import com.ririjin.adminmobile.adapter.MoneyDetails;
import com.ririjin.adminmobile.ftp_tools.SFTP;

public class UpdateBasiceFragment extends Fragment {
	private static final String TAG = "BasicFragment";

	String apiUrl = APIUtils.APIURL;
	RJPostData postData;

	LinearLayout assetIdLinear;

	EditText CarLinseceEdit;
	EditText ContractFilter;
	EditText AppraiserAsses;


	RadioGroup radioGroup;
	RadioButton rZRadioButton;
	RadioButton dYRadioButton;

	Button SaveButton;
	Button CalculateButton;

	TextView AssetIdText;

	// String[] list = { "baoxian", "cheliang", "chepaihao", "dengjizheng",
	// "fapiao", "jiancebaogao", "shenfenzheng", "shenqingbiao",
	// "xingshiben" };
	String carlicense;
	String assetId;

	Thread mkThread;

	ProgressDialog dialog = null;

	MoneyDetails moneyDetails = new MoneyDetails();

	
	public UpdateBasiceFragment(String assetId) {
		// TODO Auto-generated constructor stub
		this.assetId = assetId;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.basic_fragment, container,
				false);// 关联布局文件

		dialog = new ProgressDialog(getActivity());
		carlicense = ((UpdateCarActivity) getActivity()).gettcarLicense();

		assetIdLinear = (LinearLayout) rootView
				.findViewById(R.id.assetId_linear);
		assetIdLinear.setVisibility(View.VISIBLE);
		AssetIdText = (TextView) rootView.findViewById(R.id.asset_id);
		AssetIdText.setText(assetId);

		SaveButton = (Button) rootView.findViewById(R.id.save_btn);
		CalculateButton = (Button) rootView.findViewById(R.id.calculate_btn);

		CarLinseceEdit = (EditText) rootView.findViewById(R.id.carlinsece_edit);
		ContractFilter = (EditText) rootView.findViewById(R.id.contract_filter);
		AppraiserAsses = (EditText) rootView.findViewById(R.id.appraiser_asses);

		
		
		radioGroup = (RadioGroup) rootView.findViewById(R.id.cartype_radio);
		rZRadioButton = (RadioButton) rootView.findViewById(R.id.rongzi_btn);
		dYRadioButton = (RadioButton) rootView.findViewById(R.id.diya_btn);

		SaveButton.setOnClickListener(listener);
		CalculateButton.setOnClickListener(listener);

		postData = new RJPostData(getActivity().getApplicationContext(), apiUrl);

		CarLinseceEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {
					carlicense = GetCarLincese(CarLinseceEdit.getText()
							.toString());
					((UpdateCarActivity) getActivity())
							.setcarLicense(carlicense);
					ContractFilter.setText(SFTP.GetNowTime() + carlicense);
				}
			}
		});

		CarLinseceEdit
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						Log.d(TAG, "" + actionId + "," + event);
						if (actionId == EditorInfo.IME_ACTION_DONE
								|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
							// do something;
							CarLinseceEdit.clearFocus();
							return true;
						}
						return false;
					}
				});
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		});
		dialog.setMessage("正在加载数据...");
		dialog.setCancelable(false);
		dialog.show();
		GetCarData(assetId);

		return rootView;
	}

	private void GetCarData(String asset_id) {
		// TODO Auto-generated method stub
		String APIName = APIUtils.API_CAR_GET;

		postData.HeadersPut("X-API", APIName);

		JSONObject body = new JSONObject();

		try {
			body.put("asset_id", asset_id);
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
					JSONObject jsonObject = new JSONObject(json
							.getString("result"));

					Log.i("result", json.getString("result"));
					JSONObject basicJson = (JSONObject) jsonObject.get("basic");

					String car_type_id = basicJson.getString("car_type_id");
					String contract_id = basicJson.getString("img_folder_name");
					String car_license_number = basicJson
							.getString("car_license_number");

					CarLinseceEdit.setText(car_license_number);
					carlicense = GetCarLincese(CarLinseceEdit.getText()
							.toString());
					((UpdateCarActivity) getActivity())
							.setcarLicense(carlicense);

					ContractFilter.setText(contract_id);
					if (car_type_id.equals("1")) {
						rZRadioButton.setChecked(true);
					} else if (car_type_id.equals("2")) {
						dYRadioButton.setChecked(true);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}

			}

			@Override
			public void onFail(int statusCode, String msg) {
				// TODO Auto-generated method stub
				Log.i(TAG, "接收数据失败 statusCode:" + statusCode + "  msg:" + msg);
				if (dialog != null && dialog.isShowing()) {
					if (statusCode==5001) {
						dialog.setMessage("数据获取超时");
						dialog.setButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								GetCarData(assetId);
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
					}
				}
			}
		});
	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.save_btn:
				if (CarLinseceEdit.getText().toString() != null
						&& !CarLinseceEdit.getText().toString().equals("")) {

					carlicense = GetCarLincese(CarLinseceEdit.getText()
							.toString());
					((AddCarAcitivity) getActivity()).setcarLicense(carlicense);
					BasicUpdate();

				}

				break;
			case R.id.calculate_btn:
				Intent intent = new Intent(getActivity(),
						ActualBorrowMoneyCalculator.class);
				getActivity().startActivityForResult(intent, 1001);
				break;
			default:
				break;
			}
		}
	};

	private void BasicUpdate() {
		String APIName = APIUtils.API_BAISC_UPDATE;

		postData.HeadersPut("X-API", APIName);

		JSONObject body = new JSONObject();

		try {
			body.put("asset_id", assetId);
			body.put("car_type_id", 1);
			body.put("img_folder_name", SFTP.GetNowTime() + carlicense);
			body.put("car_license_number", CarLinseceEdit.getText().toString());

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

			}

			@Override
			public void onFail(int statusCode, String msg) {
				// TODO Auto-generated method stub
				Log.i(TAG, "接收数据失败 statusCode:" + statusCode + "  msg:" + msg);

			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		moneyDetails = (MoneyDetails) data.getSerializableExtra("moneyDetails");
		AppraiserAsses.setText("" + moneyDetails.getActual_borrow_money());

		super.onActivityResult(requestCode, resultCode, data);
	}

	public String FilterChinese(String str) {
		String reg = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(reg);
		Matcher mat = pat.matcher(str);
		String repickStr = mat.replaceAll("");
		System.out.println("去中文后:" + repickStr);
		return repickStr;
	}

	// 截取车牌号
	private String GetCarLincese(String str) {
		String reg = FilterChinese(str);
		reg.substring(1, reg.length());
		return reg.substring(1, reg.length());
	}

}
