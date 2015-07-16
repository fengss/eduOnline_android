package cn.coderss.adapter;

import java.util.ArrayList;

import cn.coderss.bean.VideoReplyBean;
import cn.coderss.edu.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReplyAdapter extends BaseAdapter {
	ArrayList<VideoReplyBean> datalist;
	Context mcontext;
	View mview;

	public ReplyAdapter(ArrayList datalist, Context mcontext) {
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
		mview = li.inflate(R.layout.adapter_reply, null);
		
		TextView author=(TextView) mview.findViewById(R.id.author);
		TextView addtime=(TextView) mview.findViewById(R.id.time);
		TextView content=(TextView) mview.findViewById(R.id.reply_content);
		
		VideoReplyBean bean=datalist.get(position);
		
		
		author.setText(bean.username);
		addtime.setText(bean.addtime);
		content.setText(bean.content);
		
		return mview;
	}
	
	
}
