package cn.coderss.adapter;

import java.util.ArrayList;

import cn.coderss.edu.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TestfliperAdapter extends BaseAdapter {
	ArrayList datalist;
	Context mcontext;
	View mview;
	
	public TestfliperAdapter(ArrayList datalist, Context mcontext) {
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
		mview=li.inflate(R.layout.adapter_test_gallery, null); 
		
		TextView t=(android.widget.TextView) mview.findViewById(R.id.testNew);
		t.setText("这是第 "+position+" の试题");
		
		return mview;
	}
	
	class ViewHolder{
		TextView title;
	}


}
