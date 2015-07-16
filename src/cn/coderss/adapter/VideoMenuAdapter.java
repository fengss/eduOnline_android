package cn.coderss.adapter;

import java.util.ArrayList;

import cn.coderss.adapter.TestAdapter.ViewHolder;
import cn.coderss.api.FssApi;
import cn.coderss.bean.VideoBean;
import cn.coderss.edu.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoMenuAdapter extends BaseAdapter {

	ArrayList<VideoBean> datalist;
	Context mcontext;
	View mview;
	

	public VideoMenuAdapter(ArrayList datalist, Context mcontext) {
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
	public View getView(int position, View v, ViewGroup viewgroup) {
		LayoutInflater li = LayoutInflater.from(mcontext);
		mview = li.inflate(R.layout.adapter_video_menu, null);
		VideoBean bean=datalist.get(position);
		
		ViewHolder viewholder=new ViewHolder();
		viewholder.title=(cn.coderss.ui.BootstrapButton) mview.findViewById(R.id.title);
		viewholder.addtime=(TextView) mview.findViewById(R.id.addtime);
		viewholder.desc=(TextView) mview.findViewById(R.id.desc);
		viewholder.icon=(ImageView) mview.findViewById(R.id.icon);
		
		viewholder.title.setText(bean.videoname);
		viewholder.desc.setText(bean.descr);
		FssApi.IMAGECACHE.get(FssApi.IMAGE+bean.picname, viewholder.icon);
		viewholder.addtime.setText(bean.addtime);
		
		return mview;
	}
	
	class ViewHolder{
		cn.coderss.ui.BootstrapButton title;
		TextView addtime;
		TextView desc;
		ImageView icon;
	}
}
