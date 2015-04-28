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
import android.widget.TextView;
import android.widget.Toast;

import com.ririjin.adminmobile.ActualBorrowMoneyCalculator;
import com.ririjin.adminmobile.AddCarAcitivity;
import com.ririjin.adminmobile.R;
import com.ririjin.adminmobile.APIUtils.APIUtils;
import com.ririjin.adminmobile.APIUtils.RJPostData;
import com.ririjin.adminmobile.APIUtils.RJPostHandler;
import com.ririjin.adminmobile.adapter.MoneyDetails;
import com.ririjin.adminmobile.ftp_tools.SFTP;

public class BasicFragment extends Fragment {
	private static final String TAG = "BasicFragment";

	String apiUrl = APIUtils.APIURL;
	RJPostData postData;

	LinearLayout assetIdLinear;

	EditText CarLinseceEdit;
	EditText ContractFilter;
	// car_factor_assess
	EditText CarFactorAssess;
	EditText AppraiserAsses;

	Button SaveButton;
	Button CalculateButton;

	String[] list = { "baoxian", "cheliang", "chepaihao", "dengjizheng",
			"fapiao", "jiancebaogao", "shenfenzheng", "shenqingbiao",
			"xingshiben", "huigouhetong", "doudihetong" };
	String carlicense;

	Thread mkThread;

	MoneyDetails moneyDetails = new MoneyDetails();

	ProgressDialog dialog = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.basic_fragment, container,
				false);// 关联布局文件
		dialog = new ProgressDialog(getActivity());
		assetIdLinear = (LinearLayout) rootView
				.findViewById(R.id.assetId_linear);
		assetIdLinear.setVisibility(View.VISIBLE);

		SaveButton = (Button) rootView.findViewById(R.id.save_btn);
		CalculateButton = (Button) rootView.findViewById(R.id.calculate_btn);

		CarLinseceEdit = (EditText) rootView.findViewById(R.id.carlinsece_edit);
		ContractFilter = (EditText) rootView.findViewById(R.id.contract_filter);
		CarFactorAssess = (EditText) rootView
				.findViewById(R.id.car_factor_assess);
		AppraiserAsses = (EditText) rootView.findViewById(R.id.appraiser_asses);

		SaveButton.setOnClickListener(listener);
		CalculateButton.setOnClickListener(listener);

		postData = new RJPostData(getActivity().getApplicationContext(), apiUrl);

		CarLinseceEdit.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (!hasFocus) {
					if (CarLinseceEdit.getText().toString().length() != 7) {
						Toast.makeText(getActivity(), "车牌号不正确，请重新输入",
								Toast.LENGTH_SHORT).show();
						ContractFilter.setText("");
					} else {

						carlicense = GetCarLincese(CarLinseceEdit.getText()
								.toString());
						((AddCarAcitivity) getActivity())
								.setcarLicense(carlicense);
						ContractFilter.setText(SFTP.GetNowTime() + carlicense);
					}

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

		return rootView;
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

					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							SFTP.MkdirFile(carlicense, list, getActivity()
									.getApplicationContext());
						}
					}).start();
					BasicSave();

				}

				break;
			case R.id.calculate_btn:
				Intent intent = new Intent(getActivity(),
						ActualBorrowMoneyCalculator.class);

				startActivityForResult(intent, 1001);
				break;

			default:
				break;
			}
		}
	};

	Runnable mkFilerunRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}
	};

	private void BasicSave() {
		String APIName = APIUtils.API_BAISC_SAVE;

		postData.HeadersPut("X-API", APIName);

		JSONObject body = new JSONObject();

		try {
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

				Toast.makeText(getActivity().getApplicationContext(),
						"车辆信息提交成功", Toast.LENGTH_LONG).show();

			}

			@Override
			public void onFail(int statusCode, String msg) {
				// TODO Auto-generated method stub
				Log.i(TAG, "接收数据失败 statusCode:" + statusCode + "  msg:" + msg);
				if (dialog != null && dialog.isShowing()) {
					if (statusCode == 5001) {
						dialog.setMessage("数据获取超时");

						dialog.dismiss();

					}
				}
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		moneyDetails = (MoneyDetails) data
				.getSerializableExtra("money_details");
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
