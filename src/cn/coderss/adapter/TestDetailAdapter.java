package cn.coderss.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import cn.coderss.bean.AnswerBean;
import cn.coderss.bean.QuestionBean;
import cn.coderss.edu.R;
import cn.coderss.util.L;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TestDetailAdapter extends BaseAdapter {
	ArrayList<QuestionBean> datalist;
	Context mcontext;
	public HashMap<Integer, String> answer;
	View mview;

	public TestDetailAdapter(ArrayList datalist, Context mcontext,HashMap<Integer, String> answer) {
		super();
		this.datalist = datalist;
		this.mcontext = mcontext;
		this.answer = answer;
		
		if (datalist!=null) {
			for (QuestionBean bean : this.datalist) {
				answer.put(Integer.valueOf(bean.id), "A");
			}
		}
		
	}

	@Override
	public int getCount() {
		if (datalist == null) {
			return 0;
		}
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

	@SuppressLint({ "UseValueOf", "NewApi" })
	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		LayoutInflater li = LayoutInflater.from(mcontext);
		mview = li.inflate(R.layout.adapter_test_detail, null);

		QuestionBean bean = datalist.get(position);

		ViewHolder viewholder = new ViewHolder();
		viewholder.title = (TextView) mview.findViewById(R.id.title);
		viewholder.A = (RadioButton) mview.findViewById(R.id.A);
		viewholder.B = (RadioButton) mview.findViewById(R.id.B);
		viewholder.C = (RadioButton) mview.findViewById(R.id.C);
		viewholder.D = (RadioButton) mview.findViewById(R.id.D);
		viewholder.sel = (RadioGroup) mview.findViewById(R.id.sel);

		
		
		/**
		 * 判断之前的cell选择了什么答案
		 */
		String ans;
		if (!answer.get(Integer.valueOf(bean.getId())).isEmpty()) {
			ans=answer.get(Integer.valueOf(bean.getId()));
			L.i("我的答案:"+answer);
			if (ans.contentEquals("A")) {
				viewholder.A.setChecked(true);
			}
			else if (ans.contentEquals("B")) {
				viewholder.B.setChecked(true);
			}
			else if (ans.contentEquals("C")) {
				viewholder.C.setChecked(true);
			}
			else if (ans.contentEquals("D")) {
				viewholder.D.setChecked(true);
			}
		}
		
		int id = Integer.parseInt(datalist.get(position).id);
		viewholder.title.setText(bean.content);
		viewholder.A.setText(bean.aA);
		viewholder.A.setTag(id);
		viewholder.B.setText(bean.aB);
		viewholder.B.setTag(id);
		viewholder.C.setText(bean.aC);
		viewholder.C.setTag(id);
		viewholder.D.setText(bean.aD);
		viewholder.D.setTag(id);

		viewholder.A.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton radio, boolean arg1) {
				if (arg1) {
					answer.put(new Integer((Integer) radio.getTag()), "A");
					L.i("我的点击后答案:"+answer);
				}
			}
		});
		viewholder.B.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton radio, boolean arg1) {
				if (arg1) {
					answer.put(new Integer((Integer) radio.getTag()), "B");
					L.i("我的点击后答案:"+answer);
				}
			}
		});
		viewholder.C.setOnCheckedChangeListener(new OnCheckedChangeListener()  {

			@Override
			public void onCheckedChanged(CompoundButton radio, boolean arg1) {
				if (arg1) {
					answer.put(new Integer((Integer) radio.getTag()), "C");
					L.i("我的点击后答案:"+answer);
				}
			}
		});
		viewholder.D.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton radio, boolean arg1) {
				if (arg1) {
					answer.put(new Integer((Integer) radio.getTag()), "D");
					L.i("我的点击后答案:"+answer);
				}
			}
		});

		return mview;
	}

	class ViewHolder {
		TextView title;
		RadioButton A;
		RadioButton B;
		RadioButton C;
		RadioButton D;
		RadioGroup sel;
	}

}
