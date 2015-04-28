package com.ririjin.adminmobile;

import com.ririjin.adminmobile.asset_manager.CarManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AssetManagerAcitivity extends Activity {
	Button AddCarButton;
	Button CarManagerButton;
	Button LimitAssessButton;
	Button BackButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_asset_manager);

		AddCarButton = (Button) findViewById(R.id.add_car);
		CarManagerButton = (Button) findViewById(R.id.car_manager);
		LimitAssessButton = (Button) findViewById(R.id.limit_assess);
		BackButton=(Button)findViewById(R.id.back);
		
		AddCarButton.setOnClickListener(listener);
		CarManagerButton.setOnClickListener(listener);
		LimitAssessButton.setOnClickListener(listener);
		BackButton.setOnClickListener(listener);

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.add_car:
				Intent intent=new Intent(AssetManagerAcitivity.this,AddCarAcitivity.class);
				startActivity(intent);
				
				break;
			case R.id.car_manager:
				 intent=new Intent(AssetManagerAcitivity.this,CarManager.class);
				startActivity(intent);
				break;
			case R.id.limit_assess:

				break;
			case R.id.back:
				finish();
				break;
			default:
				break;
			}
		}
	};
}
