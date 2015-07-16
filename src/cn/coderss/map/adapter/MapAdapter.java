package cn.coderss.map.adapter;

import java.util.ArrayList;

import cn.coderss.bean.UserBean;
import cn.coderss.edu.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MapAdapter extends BaseAdapter {
	ArrayList<UserBean> datalist;
	Context mcontext;
	View mview;

	public MapAdapter(ArrayList datalist, Context mcontext) {
		super();
		this.datalist = datalist;
		this.mcontext = mcontext;
	}

	@Override
	public int getCount() {
		return datalist.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		LayoutInflater li = LayoutInflater.from(mcontext);
		mview = li.inflate(R.layout.adapter_map, null);

		UserBean bean = datalist.get(arg0);

		TextView username = (TextView) mview.findViewById(R.id.username);
		TextView address = (TextView) mview.findViewById(R.id.address);

		username.setText(bean.username);
		address.setText("经度:" + bean.address_X + ",纬度:" + bean.address_Y);

		return mview;
	}
}
