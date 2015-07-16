package cn.coderss.adapter;

import java.util.ArrayList;

import cn.coderss.api.FssApi;
import cn.coderss.bean.ProblemBean;
import cn.coderss.edu.R;
import cn.coderss.util.L;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProblemAdapter extends BaseAdapter {

	ArrayList datalist;
	Context mcontext;
	View mview;

	public ProblemAdapter(ArrayList datalist, Context mcontext) {
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
	public View getView(int position, View view, ViewGroup viewGroup) {
		LayoutInflater li = LayoutInflater.from(mcontext);
		mview = li.inflate(R.layout.timelist_item, null);
		ProblemBean bean = (ProblemBean) datalist.get(position);

		TextView time = (TextView) mview.findViewById(R.id.show_time);
		TextView title = (TextView) mview.findViewById(R.id.title);
		ImageView image = (ImageView) mview.findViewById(R.id.image);
		ImageView image1 = (ImageView) mview.findViewById(R.id.image_1);
		FssApi.IMAGECACHE.get(FssApi.AVATOR + bean.username + "/"
				+ bean.picture, image);
		FssApi.IMAGECACHE.get(FssApi.AVATOR + bean.username + "/"
				+ bean.picture, image1);
		L.i("我的头像地址" + FssApi.AVATOR + bean.username + "/" + bean.picture);
		time.setText(bean.addtime);
		title.setText(bean.content);

		return mview;
	}
}
