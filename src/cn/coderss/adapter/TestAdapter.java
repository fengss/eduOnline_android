package cn.coderss.adapter;

import java.util.ArrayList;

import cn.coderss.bean.TestBean;
import cn.coderss.edu.R;
import cn.coderss.util.L;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TestAdapter extends BaseAdapter {
	ArrayList<TestBean> datalist;
	Context mcontext;
	View mview;
	
	public TestAdapter(ArrayList<TestBean> datalist, Context mcontext) {
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
		LayoutInflater li=LayoutInflater.from(mcontext);
		mview=li.inflate(R.layout.adapter_test, null);
		TestBean bean=datalist.get(position);
		
		
		ViewHolder viewholder=new ViewHolder();
		viewholder.title=(TextView) mview.findViewById(R.id.test_title);
		viewholder.addtime=(TextView) mview.findViewById(R.id.addtime);
		
		viewholder.title.setText(bean.title);
		viewholder.addtime.setText(bean.addtime);
		
		return mview;
	}
	
	class ViewHolder{
		TextView title;
		TextView addtime;
	}

}
