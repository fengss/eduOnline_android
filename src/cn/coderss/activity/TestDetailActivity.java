package cn.coderss.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import cn.coderss.adapter.TestDetailAdapter;
import cn.coderss.api.FssApi;
import cn.coderss.bean.AnswerBean;
import cn.coderss.bean.QuestionBean;
import cn.coderss.edu.R;
import cn.coderss.util.L;
import cn.coderss.util.PreferenceConstants;
import cn.coderss.util.PreferenceUtils;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class TestDetailActivity extends Activity {
	Context mcontext;
	ArrayList<QuestionBean> datalist = new ArrayList<QuestionBean>();
	TestDetailAdapter myadapter;
	ListView mlistview;
	public String id;
	public HashMap<Integer, String> manswer;
	public String title = "";
	public TextView titleView;
	AlertDialog dia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mcontext = this;

		setContentView(R.layout.activity_test_detail);

		Intent it = getIntent();
		if (it.getStringExtra("id") != null) {
			id = it.getStringExtra("id");
			title = it.getStringExtra("title");
		}

		titleView = (TextView) findViewById(R.id.title);
		titleView.setText(title);

		loadData();

		setActionBar();
	}

	public void loadData() {
		String url = FssApi.QUEST + "&id=" + id;

		FssApi.getHttp().get(url, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				datalist = new Gson().fromJson(t.toString(),
						new TypeToken<ArrayList<QuestionBean>>() {
						}.getType());
				if (datalist == null) {
					dia = new AlertDialog.Builder(mcontext).setTitle("提 示")
							.setMessage("该试卷无试题.....\n^_^,请返回")
							.setNegativeButton("确定", new OnClickListener() {

								@Override
								public void onClick(DialogInterface arg0, int i) {
									dia.dismiss();
									finish();
								}

							}).show();
				}
				initView();
			}
		});
	}

	public void initView() {
		manswer = new HashMap<Integer, String>();
		myadapter = new TestDetailAdapter(datalist, mcontext, manswer);
		mlistview = (ListView) findViewById(R.id.mylistview);
		mlistview.setAdapter(myadapter);
		mlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> vg, View v, int position,
					long arg3) {
				QuestionBean bean = datalist.get(position);
				L.i("我现在选择的题目");
			}
		});

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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.test_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.toanswer:

			AjaxParams params = new AjaxParams();
			params.put("tid", id);
			params.put("test", manswer.toString());
			params.put("uid", PreferenceUtils.getPrefString(mcontext,
					PreferenceConstants.ID, "30"));
			FssApi.getHttp().post(FssApi.SCORE, params,
					new AjaxCallBack<Object>() {
						@Override
						public void onSuccess(Object t) {
							super.onSuccess(t);
							final AlertDialog dia = new AlertDialog.Builder(
									mcontext)
									.setTitle("您的成绩")
									.setMessage("你考试成绩是:" + t.toString())
									.setNegativeButton("确定",
											new OnClickListener() {

												@Override
												public void onClick(
														DialogInterface arg0,
														int arg1) {
													arg0.dismiss();
												}
											}).show();

						}
					});
			break;

		default:
			break;
		}
		return true;
	}
}
