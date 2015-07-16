package cn.coderss.adapter;

import java.util.ArrayList;
import java.util.Random;

import cn.coderss.api.FssApi;
import cn.coderss.bean.BBSBean;
import cn.coderss.edu.R;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class BBSAdapter extends BaseAdapter {

	ArrayList<BBSBean> datalist;
	Context mcontext;
	View mview;
	GridView mgrid;

	public BBSAdapter(ArrayList datalist, Context mcontext, GridView gd) {
		super();
		this.datalist = datalist;
		this.mcontext = mcontext;
		this.mgrid = gd;
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
	public View getView(int position, View view, ViewGroup viewGroup) {
		LayoutInflater li = LayoutInflater.from(mcontext);
		mview = li.inflate(R.layout.adapter_bbs_2, null);
		BBSBean bean = datalist.get(position);

		ImageView image = (ImageView) mview.findViewById(R.id.image);
		TextView username = (TextView) mview.findViewById(R.id.username);
		TextView content = (TextView) mview.findViewById(R.id.content);

		FssApi.IMAGECACHE.get(FssApi.AVATOR + bean.username + "/"
				+ bean.picture, image);
		username.setText(bean.username);
		content.setText(Html.fromHtml(bean.getContent()));

		return mview;
	}
}
