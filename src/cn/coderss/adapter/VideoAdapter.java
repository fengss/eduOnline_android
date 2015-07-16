package cn.coderss.adapter;

import java.util.ArrayList;

import cn.coderss.api.FssApi;
import cn.coderss.bean.CateBean;
import cn.coderss.edu.R;
import cn.coderss.ui.BootstrapButton;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoAdapter extends BaseAdapter {
	ArrayList<CateBean> datalist;
	Context mcontext;

	public VideoAdapter(ArrayList datalist, Context mcontext) {
		super();
		this.datalist = datalist;
		this.mcontext = mcontext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datalist.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int i, View v, ViewGroup viewGroup) {
		LayoutInflater li = LayoutInflater.from(mcontext);
		View view = li.inflate(R.layout.adapter_video, null);

		CateBean bean = datalist.get(i);

		ViewHolder viewholder = new ViewHolder();
		viewholder.bgImageView = (ImageView) view
				.findViewById(R.id.bgImageView);
		viewholder.content = (TextView) view.findViewById(R.id.content);
		viewholder.content.setText(bean.name);
		viewholder.title = (BootstrapButton) view.findViewById(R.id.title);

		FssApi.IMAGECACHE.get(FssApi.UPLOADS + bean.picture,
				viewholder.bgImageView);
		viewholder.title.setText(bean.name);

		return view;
	}

	class ViewHolder {
		ImageView bgImageView;
		TextView content;
		BootstrapButton title;
	}

}
