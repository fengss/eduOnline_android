package cn.coderss.adapter;

import java.util.ArrayList;

import cn.coderss.api.FssApi;
import cn.coderss.bean.DocumentBean;
import cn.coderss.edu.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DocumentAdapter extends BaseAdapter {
	ArrayList<DocumentBean> datalist;
	Context mcontext;
	View mview;

	public DocumentAdapter(ArrayList datalist, Context mcontext) {
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
		mview = li.inflate(R.layout.adapter_document, null);

		DocumentBean bean = datalist.get(position);

		ImageView image = (ImageView) mview.findViewById(R.id.ImageView);
		TextView title = (TextView) mview.findViewById(R.id.title);
		TextView username = (TextView) mview.findViewById(R.id.username);
		TextView look = (TextView) mview.findViewById(R.id.look);
		TextView down = (TextView) mview.findViewById(R.id.click);

		FssApi.IMAGECACHE.get(FssApi.AVATOR + bean.username + "/"
				+ bean.picture, image);
		username.setText(bean.username);
		title.setText(bean.title);
		look.setText(bean.clicknum + "次查看");
		down.setText(bean.dwloadnum + "次下载");

		return mview;
	}
}
