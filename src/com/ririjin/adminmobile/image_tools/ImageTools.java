package com.ririjin.adminmobile.image_tools;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class ImageTools {
//	Intent intent = new Intent(
//			Intent.ACTION_PICK,
//			android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//	startActivityForResult(intent, 110);
	
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		Log.i("clx", "data==" + data + "resultCode==" + resultCode
//				+ "requestCode==" + requestCode);
//		// TODO Auto-generated method stub
//		if (resultCode != Activity.RESULT_OK)
//			return;
//
//		switch (requestCode) {
//
//		case 110:
//			Log.i("clx", "data" + data);
//			if (null == data) {
//				return;
//			}
//			Uri originalUri = data.getData();
//			try {
//				String[] proj = { MediaStore.Images.Media.DATA };
//				// 好像是android多媒体数据库的封装接口，具体的看Android文档
//				@SuppressWarnings("deprecation")
//				Cursor cursor = this.managedQuery(originalUri, proj, null,
//						null, null);
//				// 按我个人理解 这个是获得用户选择的图片的索引值
//				int column_index = cursor
//						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//				// 将光标移至开头 ，这个很重要，不小心很容易引起越界
//				cursor.moveToFirst();
//				// 最后根据索引值获取图片路径
//				String path = cursor.getString(column_index);
//				Uri mFileUri = Uri.parse(path);
//				Log.i("mFileUri1", "" + mFileUri);
//
//				uploadImg(path);
//
//			} catch (Exception e) {
//				Uri mFileUri = originalUri;
//				Log.i("mFileUri2", "" + mFileUri);
//
//			}
//			break;
//		}
//		super.onActivityResult(requestCode, resultCode, data);
//	}

}
