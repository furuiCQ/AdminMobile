package com.ririjin.adminmobile.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ririjin.adminmobile.R;
import com.ririjin.adminmobile.APIUtils.APIUtils;
import com.ririjin.adminmobile.APIUtils.RJPostData;
import com.ririjin.adminmobile.APIUtils.RJPostHandler;

public class ListViewAdapter extends BaseAdapter {
	private Context c = null;

	private LayoutInflater myInflater = null;

	private List<CarList> list = null;

	/**
	 * 构造函数
	 * 
	 * @param myContext
	 */
	public ListViewAdapter(Context myContext, List<CarList> list) {
		c = myContext;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * 这个方法getView()，是用来逐一绘制每一条item
	 * 
	 * setTag()会把View与ViewHolder绑定，形成一一对应的关系，拖动listview的时候会重新绘制每一条item
	 * 但是那些已经取得绑定的View，会调用getTag()方法，就不会重新绘制，而是拿到内存中已经取得的资源，提高了效率
	 * 
	 * @param position
	 *            position就是位置从0开始
	 * @param convertView
	 *            convertView是Spinner,ListView中每一项要显示的view
	 * @param parent
	 *            parent就是父窗体了，也就是Spinner,ListView,GridView了
	 * @return 通常return 的view也就是convertView
	 */
	@SuppressLint("ViewTag")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			/**
			 * 在程序中动态加载以上布局: LayoutInflater flater = LayoutInflater.from(this);
			 * View view = flater.inflate(R.layout.example, null);
			 */
			myInflater = LayoutInflater.from(c);
			convertView = myInflater.inflate(R.layout.list_item, null);

			holder.AssetIdText = (TextView) convertView
					.findViewById(R.id.asset_id_text);
			holder.BizTypeIdText = (TextView) convertView
					.findViewById(R.id.biz_type_id);
			holder.CarLisence = (TextView) convertView
					.findViewById(R.id.car_lisence);
			
			holder.AssetStatues = (TextView) convertView
					.findViewById(R.id.asset_statues);

			convertView.setTag(holder);
			
		
			
		} else {
			holder = (ViewHolder) convertView.getTag();
			
		}
		holder.AssetIdText.setText(list.get(position).AssetId);
		switch (list.get(position).BizTypeId) {
		case 1:
			holder.BizTypeIdText.setText("融资车辆");
			break;
		case 2:
			holder.BizTypeIdText.setText("抵押车辆");
			break;

		default:
			break;
		}

		holder.AssetStatues.setText(GetStatues(list.get(position).StatusId));
		holder.CarLisence.setText(list.get(position).CarLicenseNumber);

		

		return convertView;
	}

	private class ViewHolder {
		TextView AssetIdText;
		TextView BizTypeIdText;
		TextView CarLisence;
		TextView AssetStatues;
	}

	

	protected String GetStatues(String str) {
		// TODO Auto-generated method stub
		if (str.equals("applied")) {
			str = "已申报";
		} else if (str.equals("deleted")) {
			str = "已删除";
		} else if (str.equals("logoff")) {
			str = "已注销";
		} else if (str.equals("mortgaged")) {
			str = "已抵押";
		} else if (str.equals("normal")) {
			str = "正常";
		} else if (str.equals("refused")) {
			str = "审核不通过";
		} else if (str.equals("saved")) {
			str = "已保存";
		} else if (str.equals("solved")) {
			str = "已解押";
		} else if (str.equals("submitted")) {
			str = "已提交";
		} else if (str.equals("unsigned")) {
			str = "待签订合同";
		} else {
			str = "未知状态";
		}
		return str;
	}
}
