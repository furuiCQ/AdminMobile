package com.ririjin.adminmobile;

import com.ririjin.adminmobile.asset_manager.CarManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button AssetManagerButton;
	Button AssetAssessButton;
	Button SystemSettingButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_main);

		AssetManagerButton = (Button) findViewById(R.id.asset_manager);
		AssetAssessButton = (Button) findViewById(R.id.asset_assess);
		SystemSettingButton = (Button) findViewById(R.id.sys_setting);

		AssetManagerButton.setOnClickListener(listener);
		AssetAssessButton.setOnClickListener(listener);
		SystemSettingButton.setOnClickListener(listener);

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.asset_manager:
				Intent intent = new Intent(MainActivity.this,
						AssetManagerAcitivity.class);
				startActivity(intent);
				break;
			case R.id.asset_assess:
				
				break;
			case R.id.sys_setting:

				break;

			default:
				break;
			}
		}
	};

}
