package cn.coderss.adapter;

import java.util.ArrayList;

import cn.coderss.bean.NoteReplyBean;
import cn.coderss.edu.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoteReplyAdapter extends BaseAdapter {
	ArrayList<NoteReplyBean> datalist=new ArrayList<NoteReplyBean>();
	Context mcontext;
	View mview;

	public NoteReplyAdapter(ArrayList datalist, Context mcontext) {
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
		mview = li.inflate(R.layout.adapter_note_reply, null);

		TextView username = (TextView) mview.findViewById(R.id.username);
		TextView time = (TextView) mview.findViewById(R.id.time);
		TextView content = (TextView) mview.findViewById(R.id.content);

		NoteReplyBean bean = datalist.get(position);

		username.setText(bean.username);
		time.setText(bean.addtime);
		content.setText(bean.content);

		return mview;
	}

}
