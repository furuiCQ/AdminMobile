package com.ririjin.adminmobile;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ririjin.adminmobile.fragment.UpdateBasiceFragment;
import com.ririjin.adminmobile.fragment.UpdateImageFragment;

public class UpdateCarActivity extends FragmentActivity {

	ArrayList<Fragment> list = new ArrayList<Fragment>();

	ViewPager viewPager;
	RadioGroup radioGroup;
	RadioButton basicButton;
	RadioButton uploadButton;

	Button BackButton;
	TextView TitleText;
	
	
	public static String assetId;
	
	public String getassetId(){
		return assetId;
	}
	public void setassetId(String assetId) {
		this.assetId = assetId;
	}
	
	public static String Carlicense;

	public String gettcarLicense() {
		return Carlicense;
	}

	public void setcarLicense(String carlicense) {
		this.Carlicense = carlicense;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addcar);
		
		if (getIntent().getExtras()!=null) {
			assetId=getIntent().getExtras().getString("assetId");
			setassetId(assetId);
		}
		
		
		TitleText=(TextView)findViewById(R.id.title_text);
		BackButton = (Button) findViewById(R.id.back);
		viewPager = (ViewPager) findViewById(R.id.addcar_viewpager);
		radioGroup = (RadioGroup) findViewById(R.id.radio_group);
		basicButton = (RadioButton) findViewById(R.id.basicview_btn);
		uploadButton = (RadioButton) findViewById(R.id.uploadview_btn);

		TitleText.setText("编辑车辆信息");
		
		UpdateBasiceFragment updateBasiceFragment = new UpdateBasiceFragment(assetId);
		UpdateImageFragment updateImageFragment = new UpdateImageFragment(
				BackButton);

		list.add(updateBasiceFragment);
		list.add(updateImageFragment);

		viewPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), list));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		
		BackButton.setOnClickListener(listener);
		basicButton.setOnClickListener(listener);
		uploadButton.setOnClickListener(listener);

	}

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.basicview_btn:
				viewPager.setCurrentItem(0);
				break;
			case R.id.uploadview_btn:
				viewPager.setCurrentItem(1);
				break;
			case R.id.back:
				finish();
				break;
			default:
				break;
			}
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 0:
				basicButton.setChecked(true);
				break;
			case 1:
				uploadButton.setChecked(true);
				break;
			default:
				break;
			}

		}
	}

	class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		ArrayList<Fragment> list;

		public MyFragmentPagerAdapter(FragmentManager fm,
				ArrayList<Fragment> list) {
			super(fm);
			this.list = list;

		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}

	}

	
	
	

}
