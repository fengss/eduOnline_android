package cn.coderss.activity;

import java.util.ArrayList;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import cn.coderss.adapter.NoteReplyAdapter;
import cn.coderss.api.FssApi;
import cn.coderss.bean.NoteReplyBean;
import cn.coderss.edu.R;
import cn.coderss.util.L;
import cn.coderss.util.PreferenceConstants;
import cn.coderss.util.PreferenceUtils;
import cn.coderss.util.T;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class NoteReplyActivity extends Activity {
	ArrayList<NoteReplyBean> datalist;
	Context mcontext;
	NoteReplyAdapter adapter;
	ListView listview;
	AlertDialog dia;
	View diaView;
	TextView content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mcontext = this;
		setContentView(R.layout.activity_note_detail_reply);
		diaView = LayoutInflater.from(mcontext).inflate(
				R.layout.view_note_reply, null);
		content = (TextView) diaView.findViewById(R.id.content);
		initView();
		setActionBar();
	}

	public void setActionBar() {
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowHomeEnabled(false);
		bar.setTitle("返回");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.note_reply:

			dia = new AlertDialog.Builder(this).setTitle("提 示")
					.setView(diaView)
					.setNegativeButton("确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// 回复评论
							AjaxParams p = new AjaxParams();
							p.put("uid", PreferenceUtils.getPrefString(
									mcontext, PreferenceConstants.ID, "10"));
							p.put("nid", NoteDetailActivity.id);
							L.i("我的评论内容是:" + content.getText().toString());
							p.put("content", content.getText().toString());
							FssApi.getHttp().post(FssApi.NOTEREPLY, p,
									new AjaxCallBack<Object>() {
										@Override
										public void onSuccess(Object t) {
											super.onSuccess(t);
											L.i(t.toString());
											if (!t.equals("ERROR")) {
												T.showShort(mcontext, "评论成功");
											}
										}
									});
						}
					}).setPositiveButton("取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {

						}
					}).show();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.note_reply, menu);
		return true;
	}

	public void initView() {
		listview = (ListView) findViewById(R.id.mylistview);
		datalist = NoteDetailActivity.replyArray;
		if (datalist == null) {
			datalist = new ArrayList<NoteReplyBean>();
		}
		adapter = new NoteReplyAdapter(datalist, mcontext);
		listview.setAdapter(adapter);
	}
}
