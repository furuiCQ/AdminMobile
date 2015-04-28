package com.ririjin.adminmobile.fragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ririjin.adminmobile.R;
import com.ririjin.adminmobile.UpdateCarActivity;
import com.ririjin.adminmobile.ftp_tools.MyProgressMonitor;
import com.ririjin.adminmobile.ftp_tools.SFTP;

public class UpdateImageFragment extends Fragment {

	private static final String UpdateCarActivity = null;
	Button BaoxianAlbumBtn;
	Button BaoxianCameraBtn;
	CheckBox BaoxianCheckBox;
	LinearLayout BaoxianProgeressLinear;

	Button CheliangzhaopianAlbumBtn;
	Button CheliangzhaopianCameraBtn;
	CheckBox CheliangzhaopianCheckBox;

	Button ChepaiAlbumBtn;
	Button ChepaiCameraBtn;
	CheckBox ChepaiCheckBox;

	Button DengjizhengAlbumBtn;
	Button DengjizhengCameraBtn;
	CheckBox DengjizhengCheckBox;

	Button FapiaoAlbumBtn;
	Button FapiaoCameraBtn;
	CheckBox FapiaoCheckBox;

	Button JianchebaogaoAlbumBtn;
	Button JianchebaogaoCameraBtn;
	CheckBox JianchebaogaoCheckBox;

	Button ShenfenzhengAlbumBtn;
	Button ShenfenzhengCameraBtn;
	CheckBox ShenfenzhengCheckBox;

	Button ShenqingbiaoAlbumBtn;
	Button ShenqingbiaoCameraBtn;
	CheckBox ShenqingbiaoCheckBox;

	Button XingshibenAlbumBtn;
	Button XingshibenCameraBtn;
	CheckBox XingshibenCheckBox;

	Button DoudihetongAlbumBtn;
	Button DoudihetongCameraBtn;
	CheckBox DoudihetongCheckBox;

	Button HuigouhetongAlbumBtn;
	Button HuigouhetongCameraBtn;
	CheckBox HuigouhetongCheckBox;

	Button BackButton;

	String carLisence;

	ProgressDialog uploadDialog = null;

	public UpdateImageFragment(View v) {
		this.BackButton = (Button) v;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.upload_image_fragment,
				container, false);// 关联布局文件
		uploadDialog = new ProgressDialog(getActivity());
		// 设置最大值为100
		uploadDialog.setMax(100);
		// 设置进度条风格STYLE_HORIZONTAL
		uploadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		uploadDialog.setTitle("进度条对话框");
		BaoxianAlbumBtn = (Button) rootView.findViewById(R.id.baoxian_album);
		BaoxianAlbumBtn = (Button) rootView.findViewById(R.id.baoxian_album);
		BaoxianCameraBtn = (Button) rootView.findViewById(R.id.baoxian_camera);
		BaoxianCheckBox = (CheckBox) rootView.findViewById(R.id.baoxian_check);
		BaoxianProgeressLinear = (LinearLayout) rootView
				.findViewById(R.id.baoxian_progress_linear);

		CheliangzhaopianAlbumBtn = (Button) rootView
				.findViewById(R.id.cheliangzhaopian_album);
		CheliangzhaopianCameraBtn = (Button) rootView
				.findViewById(R.id.cheliangzhaopian_camera);
		CheliangzhaopianCheckBox = (CheckBox) rootView
				.findViewById(R.id.cheliangzhaopian_check);

		ChepaiAlbumBtn = (Button) rootView
				.findViewById(R.id.chepaihaozhaopian_album);
		ChepaiCameraBtn = (Button) rootView
				.findViewById(R.id.chepaihaozhaopian_camera);
		ChepaiCheckBox = (CheckBox) rootView.findViewById(R.id.chepaihao_check);

		DengjizhengAlbumBtn = (Button) rootView
				.findViewById(R.id.dengjizhengzhaopian_album);
		DengjizhengCameraBtn = (Button) rootView
				.findViewById(R.id.dengjizhengzhaopian_camera);
		DengjizhengCheckBox = (CheckBox) rootView
				.findViewById(R.id.dengjizheng_check);

		FapiaoAlbumBtn = (Button) rootView
				.findViewById(R.id.gouchefapiao_album);
		FapiaoCameraBtn = (Button) rootView
				.findViewById(R.id.gouchefapiao_camera);
		FapiaoCheckBox = (CheckBox) rootView
				.findViewById(R.id.gouchefapiao_check);

		JianchebaogaoAlbumBtn = (Button) rootView
				.findViewById(R.id.jianchebaogao_album);
		JianchebaogaoCameraBtn = (Button) rootView
				.findViewById(R.id.jianchebaogao_camera);
		JianchebaogaoCheckBox = (CheckBox) rootView
				.findViewById(R.id.jianchebaogao_check);

		ShenfenzhengAlbumBtn = (Button) rootView
				.findViewById(R.id.shenfenzheng_album);
		ShenfenzhengCameraBtn = (Button) rootView
				.findViewById(R.id.shenfenzheng_camera);
		ShenfenzhengCheckBox = (CheckBox) rootView
				.findViewById(R.id.shenfenzheng_check);

		ShenqingbiaoAlbumBtn = (Button) rootView
				.findViewById(R.id.shenqingbiaozhaopian_album);
		ShenqingbiaoCameraBtn = (Button) rootView
				.findViewById(R.id.shenqingbiaozhaopian_camera);
		ShenqingbiaoCheckBox = (CheckBox) rootView
				.findViewById(R.id.shenqingbiao_check);

		XingshibenAlbumBtn = (Button) rootView
				.findViewById(R.id.xingshiben_album);
		XingshibenCameraBtn = (Button) rootView
				.findViewById(R.id.xingshiben_camera);
		XingshibenCheckBox = (CheckBox) rootView
				.findViewById(R.id.xingshiben_check);

		DoudihetongAlbumBtn = (Button) rootView
				.findViewById(R.id.doudihetong_album);
		DoudihetongCameraBtn = (Button) rootView
				.findViewById(R.id.doudihetong_camera);
		DoudihetongCheckBox = (CheckBox) rootView
				.findViewById(R.id.doudihetong_check);

		HuigouhetongAlbumBtn = (Button) rootView
				.findViewById(R.id.huigouhetong_album);
		HuigouhetongCameraBtn = (Button) rootView
				.findViewById(R.id.huigouhetong_camera);
		HuigouhetongCheckBox = (CheckBox) rootView
				.findViewById(R.id.huigouhetong_check);

		BackButton.setVisibility(View.VISIBLE);

		BaoxianAlbumBtn.setOnClickListener(listener);
		BaoxianCameraBtn.setOnClickListener(listener);
		CheliangzhaopianAlbumBtn.setOnClickListener(listener);
		CheliangzhaopianCameraBtn.setOnClickListener(listener);
		ChepaiAlbumBtn.setOnClickListener(listener);
		ChepaiCameraBtn.setOnClickListener(listener);
		DengjizhengAlbumBtn.setOnClickListener(listener);
		DengjizhengCameraBtn.setOnClickListener(listener);
		FapiaoAlbumBtn.setOnClickListener(listener);
		FapiaoCameraBtn.setOnClickListener(listener);
		JianchebaogaoAlbumBtn.setOnClickListener(listener);
		JianchebaogaoCameraBtn.setOnClickListener(listener);
		ShenfenzhengAlbumBtn.setOnClickListener(listener);
		ShenfenzhengCameraBtn.setOnClickListener(listener);
		ShenqingbiaoAlbumBtn.setOnClickListener(listener);
		ShenqingbiaoCameraBtn.setOnClickListener(listener);
		XingshibenAlbumBtn.setOnClickListener(listener);
		XingshibenCameraBtn.setOnClickListener(listener);
		DoudihetongAlbumBtn.setOnClickListener(listener);
		DoudihetongCameraBtn.setOnClickListener(listener);
		HuigouhetongAlbumBtn.setOnClickListener(listener);
		HuigouhetongCameraBtn.setOnClickListener(listener);

		return rootView;
	}
	private String getPhotopath() {  
        // 照片全路径  
        String fileName = "";  
        // 文件夹路径  
        String pathUrl = Environment.getExternalStorageDirectory()+"/adminMoblie/";  
        String imageName = "car.jpg";  
        File file = new File(pathUrl);  
        file.mkdirs();// 创建文件夹  
        fileName = pathUrl + imageName;  
        return fileName;  
    }  
	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.baoxian_album:

				if (CheckCarData()) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, 110);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				// 打开相册
				break;
			case R.id.baoxian_camera:
				if (CheckCarData()) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File out = new File(getPhotopath());  
	                Uri uri = Uri.fromFile(out);  
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);   
					startActivityForResult(intent, 210);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.cheliangzhaopian_album:

				if (CheckCarData()) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, 111);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				// 打开相册
				break;
			case R.id.cheliangzhaopian_camera:
				if (CheckCarData()) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File out = new File(getPhotopath());  
	                Uri uri = Uri.fromFile(out);  
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(intent, 211);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.chepaihaozhaopian_album:

				if (CheckCarData()) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, 112);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				// 打开相册
				break;
			case R.id.chepaihaozhaopian_camera:
				if (CheckCarData()) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File out = new File(getPhotopath());  
	                Uri uri = Uri.fromFile(out);  
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(intent, 212);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.dengjizhengzhaopian_album:

				if (CheckCarData()) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, 113);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				// 打开相册
				break;
			case R.id.dengjizhengzhaopian_camera:
				if (CheckCarData()) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File out = new File(getPhotopath());  
	                Uri uri = Uri.fromFile(out);  
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(intent, 213);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.gouchefapiao_album:

				if (CheckCarData()) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, 114);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				// 打开相册
				break;
			case R.id.gouchefapiao_camera:
				if (CheckCarData()) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File out = new File(getPhotopath());  
	                Uri uri = Uri.fromFile(out);  
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(intent, 214);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.jianchebaogao_album:

				if (CheckCarData()) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, 115);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				// 打开相册
				break;
			case R.id.jianchebaogao_camera:
				if (CheckCarData()) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File out = new File(getPhotopath());  
	                Uri uri = Uri.fromFile(out);  
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(intent, 215);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.shenfenzheng_album:

				if (CheckCarData()) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, 116);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				// 打开相册
				break;
			case R.id.shenfenzheng_camera:
				if (CheckCarData()) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File out = new File(getPhotopath());  
	                Uri uri = Uri.fromFile(out);  
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(intent, 216);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.shenqingbiaozhaopian_album:

				if (CheckCarData()) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, 117);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				// 打开相册
				break;
			case R.id.shenqingbiaozhaopian_camera:
				if (CheckCarData()) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File out = new File(getPhotopath());  
	                Uri uri = Uri.fromFile(out);  
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(intent, 217);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.xingshiben_album:

				if (CheckCarData()) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, 118);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				// 打开相册
				break;
			case R.id.xingshiben_camera:
				if (CheckCarData()) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File out = new File(getPhotopath());  
	                Uri uri = Uri.fromFile(out);  
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(intent, 218);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.huigouhetong_album:

				if (CheckCarData()) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, 119);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				// 打开相册
				break;
			case R.id.huigouhetong_camera:
				if (CheckCarData()) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File out = new File(getPhotopath());  
	                Uri uri = Uri.fromFile(out);  
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(intent, 219);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.doudihetong_album:

				if (CheckCarData()) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

					startActivityForResult(intent, 120);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				// 打开相册
				break;
			case R.id.doudihetong_camera:
				if (CheckCarData()) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File out = new File(getPhotopath());  
	                Uri uri = Uri.fromFile(out);  
	                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					startActivityForResult(intent, 220);
				} else {
					Toast.makeText(getActivity(), "请把车牌号填写保存后再上传图片",
							Toast.LENGTH_LONG).show();
				}
				break;

			default:
				break;
			}
		}
	};

	private Boolean CheckCarData() {
		carLisence = ((UpdateCarActivity) getActivity()).gettcarLicense();
		if (carLisence != null) {
			return true;
		} else {
			return false;
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("clx", "data==" + data + "resultCode==" + resultCode
				+ "requestCode==" + requestCode);
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK)
			return;
		switch (requestCode) {

		case 110:
			GetImageUrl(data, "baoxian");
			break;
		case 111:
			GetImageUrl(data, "cheliang");
			break;
		case 112:
			GetImageUrl(data, "chepaihao");
			break;
		case 113:
			GetImageUrl(data, "dengjizheng");

			break;
		case 114:
			GetImageUrl(data, "fapiao");

			break;
		case 115:
			GetImageUrl(data, "jiancebaogao");

			break;
		case 116:
			GetImageUrl(data, "shenfenzheng");
			break;

		case 117:
			GetImageUrl(data, "shenqingbiao");
			break;

		case 118:
			GetImageUrl(data, "xingshiben");

			break;
		case 119:
			GetImageUrl(data, "huigouhetong");

			break;
		case 120:
			GetImageUrl(data, "doudihetong");

			break;
		case 210:
			saveMyBitmap(getimage(getPhotopath()), "baoxian");
			break;
		case 211:
			
			saveMyBitmap(getimage(getPhotopath()), "cheliang");
			break;
		case 212:
			
			saveMyBitmap(getimage(getPhotopath()), "chepaihao");
			break;
		case 213:
			
			saveMyBitmap(getimage(getPhotopath()), "dengjizheng");
			break;
		case 214:
			
			saveMyBitmap(getimage(getPhotopath()), "fapiao");
			break;
		case 215:
			
			saveMyBitmap(getimage(getPhotopath()), "jiancebaogao");
			break;
		case 216:
			
			saveMyBitmap(getimage(getPhotopath()), "shenqingbiao");
			break;
		case 217:
			
			saveMyBitmap(getimage(getPhotopath()), "shenqingbiao");
			break;
		case 218:
			
			saveMyBitmap(getimage(getPhotopath()), "xingshiben");
			break;
		case 219:
			
			saveMyBitmap(getimage(getPhotopath()), "huigouhetong");
			break;
		case 220:
			
			saveMyBitmap(getimage(getPhotopath()), "doudihetong");
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void GetImageUrl(Intent data, String imagename) {
		Log.i("clx", "data" + data);
		if (null == data) {
			return;
		}
		Uri originalUri = data.getData();
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			// 好像是android多媒体数据库的封装接口，具体的看Android文档
			@SuppressWarnings("deprecation")
			Cursor cursor = getActivity().managedQuery(originalUri, proj, null,
					null, null);
			// 按我个人理解 这个是获得用户选择的图片的索引值
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			// 将光标移至开头 ，这个很重要，不小心很容易引起越界
			cursor.moveToFirst();
			// 最后根据索引值获取图片路径
			String path = cursor.getString(column_index);
//			Uri mFileUri = Uri.parse(path);
//			Log.i("mFileUri1", "" + mFileUri);
			saveMyBitmap(getimage(path), imagename);

		} catch (Exception e) {
			Uri mFileUri = originalUri;
			Log.i("mFileUri2", "" + mFileUri);

		}
	}

	public void saveMyBitmap(Bitmap mBitmap, String bitName) {
		String time=""+System.currentTimeMillis();
		File f = new File("/sdcard/" + bitName + SFTP.GetNowTime()+time + ".jpg");
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
			fOut.flush();
			fOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		uploadImg("/sdcard/" + bitName + SFTP.GetNowTime()+time + ".jpg", bitName);
	}

	private Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	private Bitmap getimage(String srcPath) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	private Bitmap comp(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	public void uploadImg(final String path, final String type) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 上传
				File file = new File(path);
				long fileSize = file.length();
				Looper.prepare();
				//uploadDialog.show();
				MyProgressMonitor monitor = new MyProgressMonitor(fileSize,
						getActivity());
				SFTP.UpdateImage(path, type, monitor);

			}
		}).start();

	}

}
