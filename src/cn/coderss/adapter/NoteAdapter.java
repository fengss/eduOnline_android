package cn.coderss.adapter;

import java.util.ArrayList;

import cn.coderss.api.FssApi;
import cn.coderss.bean.NoteBean;
import cn.coderss.edu.R;
import cn.coderss.util.L;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter {
	ArrayList datalist;
	Context mcontext;
	View mview;

	public NoteAdapter(ArrayList datalist, Context mcontext) {
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
		mview = li.inflate(R.layout.adapter_note, null);

		TextView title = (TextView) mview.findViewById(R.id.title);
		TextView author = (TextView) mview.findViewById(R.id.author);
		TextView addtime = (TextView) mview.findViewById(R.id.addtime);
		TextView content = (TextView) mview.findViewById(R.id.content);
		TextView sc = (TextView) mview.findViewById(R.id.sc);
		TextView reply = (TextView) mview.findViewById(R.id.reply);
		ImageView icoimageview = (ImageView) mview
				.findViewById(R.id.icoimageview);
		ImageView icon = (ImageView) mview.findViewById(R.id.icon);

		NoteBean bean = (NoteBean) datalist.get(position);
		FssApi.IMAGECACHE.get(FssApi.NOTEPIC + bean.picname, icoimageview);
		FssApi.IMAGECACHE.get(FssApi.AVATOR + bean.username + "/"
				+ bean.picture, icon);
		content.setText(bean.content);
		title.setText(bean.title);
		author.setText(bean.username);
		sc.setText(bean.collnum + "次收藏");
		reply.setText(bean.commnum + "次回复");

		addtime.setText(bean.addtime);

		return mview;
	}
}
