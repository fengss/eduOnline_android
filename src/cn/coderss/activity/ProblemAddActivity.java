package cn.coderss.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import cn.coderss.api.FssApi;
import cn.coderss.bean.PorblemAddCateBean;
import cn.coderss.bean.TeacherCateBean;
import cn.coderss.edu.R;
import cn.coderss.ui.BootstrapButton;
import cn.coderss.util.L;
import cn.coderss.util.PreferenceConstants;
import cn.coderss.util.PreferenceUtils;
import cn.coderss.util.T;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class ProblemAddActivity extends Activity {

	ListView listview_cate;
	EditText tag, content;
	Spinner teacher;
	BootstrapButton send;
	String techerid;
	ArrayList<String> datalist;
	ArrayList<String> datalistid;
	ProgressDialog progress_dia;
	AlertDialog dia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_problem_add);

		initView();
		// 载入技术分类数据
		loadCateData();
		// 载入老师数据;
		loadTeacherData();
		setActionBar();
	}

	@SuppressLint("NewApi")
	public void setActionBar() {
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowCustomEnabled(true);
		bar.setDisplayShowHomeEnabled(false);
		bar.setTitle("返回");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return true;
	}

	public void loadCateData() {
		FssApi.getHttp().get(FssApi.GETTYPE, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);

				PorblemAddCateBean bean = new Gson().fromJson(t.toString(),
						PorblemAddCateBean.class);

				L.i("我获得的分类数据:" + bean.toString());

				datalist = bean.getCust_names();
				datalistid = bean.getCust_ids();
				listview_cate.setAdapter(new BaseAdapter() {

					@Override
					public View getView(int arg0, View arg1, ViewGroup arg2) {
						View mview = LayoutInflater.from(
								getApplicationContext()).inflate(
								R.layout.adapter_problemadd_cate, null);

						TextView title = (TextView) mview
								.findViewById(R.id.title);

						title.setText(datalist.get(arg0));

						return mview;
					}

					@Override
					public long getItemId(int arg0) {
						// TODO Auto-generated method stub
						return arg0;
					}

					@Override
					public Object getItem(int arg0) {
						// TODO Auto-generated method stub
						return arg0;
					}

					@Override
					public int getCount() {
						// TODO Auto-generated method stub
						return datalist.size();
					}
				});

				// 将点击的标签放进edittext
				listview_cate.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (tag.getText().toString().equals("")) {
							tag.setText(datalistid.get(arg2));
						} else {
							String content = tag.getText().toString();
							String[] cate = content.split(",");
							for (String string : cate) {
								if (datalistid.get(arg2).equals(string)) {
									return;
								}
							}
							tag.setText(tag.getText() + ","
									+ datalistid.get(arg2));

						}
					}
				});
			}
		});
		;
	}

	public void loadTeacherData() {
		FssApi.getHttp().get(FssApi.PROBLEMGETTEACHER,
				new AjaxCallBack<Object>() {
					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);

						final ArrayList<TeacherCateBean> techer_data = new Gson().fromJson(
								t.toString(),
								new TypeToken<ArrayList<TeacherCateBean>>() {
								}.getType());
						L.i("教师数据" + techer_data.toString());

						ArrayList<String> items = new ArrayList<String>();
						final ArrayList<String> items2 = new ArrayList<String>();
						for (TeacherCateBean bean : techer_data) {
							items.add(bean.getName());
							items2.add(bean.getId());
						}

						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								getApplicationContext(),
								android.R.layout.simple_dropdown_item_1line,
								items);
						teacher.setAdapter(adapter);
						teacher.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								techerid = items2.get(arg2);
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}
						});
					}
				});
	}

	public void initView() {
		listview_cate = (ListView) findViewById(R.id.listview_cate);
		tag = (EditText) findViewById(R.id.tag);
		content = (EditText) findViewById(R.id.content);
		content.clearFocus();
		teacher = (Spinner) findViewById(R.id.teacher);
		send = (BootstrapButton) findViewById(R.id.send);

		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String str_content = content.getText().toString();
				String keywords = tag.getText().toString();
				if (str_content.equals("") || keywords.equals("")) {
					T.showShort(getApplicationContext(), "请填写完整后再提问");
				}
				if (techerid.equals("") || teacher == null) {
					T.showShort(getApplicationContext(), "请选择老师");
				}

				L.i("我提出问题的内容:" + str_content);

				AjaxParams p = new AjaxParams();
				p.put("tid", techerid);
				p.put("keyword", keywords);
				p.put("content", str_content);
				p.put("uid", PreferenceUtils.getPrefString(
						getApplicationContext(), PreferenceConstants.ID, "30"));
				L.i(p.toString());
				FssApi.getHttp().post(FssApi.PROBLEMADD, p,
						new AjaxCallBack<Object>() {
							@Override
							public void onSuccess(Object t) {
								L.i(t.toString());
								super.onSuccess(t);
								if (t.equals("no")) {
									T.showShort(getApplicationContext(), "发布失败");
								} else if (t.equals("yes")) {
									T.showShort(getApplicationContext(), "发布成功");
									finish();
								} else {
									T.showShort(getApplicationContext(),
											"问题已经存在");
									finish();
								}
							}
						});
			}
		});
	}
}
