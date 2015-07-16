package cn.coderss.activity;

import java.util.ArrayList;

import com.google.gson.Gson;

import net.tsz.afinal.http.AjaxCallBack;
import cn.coderss.api.FssApi;
import cn.coderss.bean.NoteDetailBean;
import cn.coderss.bean.NoteReplyBean;
import cn.coderss.edu.R;
import cn.coderss.ui.BootstrapButton;
import cn.coderss.util.L;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class NoteDetailActivity extends Activity {
	static String id;
	ProgressDialog pdia;
	NoteDetailBean data;

	TextView username, content;
	BootstrapButton zan, sc;
	ImageView icon;
	static ArrayList<NoteReplyBean> replyArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_detail);
		id = getIntent().getStringExtra("id");
		if (id == null) {
			AlertDialog dia = new AlertDialog.Builder(this).setTitle("提示")
					.setNegativeButton("确定", new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							finish();

						}
					}).setMessage("对不起,未获取到信息").show();
		}
		setActionBar();
		loadData();
	}

	// 获取数据
	public void loadData() {
		pdia = new ProgressDialog(this);
		FssApi.getHttp().get(FssApi.NOTEDETAIL + "&id=" + id,
				new AjaxCallBack<Object>() {
					@Override
					public void onStart() {
						super.onStart();
						pdia.setTitle("等待");
						pdia.setMessage("信息正在载入,请稍后");
						pdia.show();
					}

					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);
						L.i("我报错的得到的数据是:" + t.toString());
						pdia.dismiss();
						data = new Gson().fromJson(t.toString(),
								NoteDetailBean.class);
						initView();
					}
				});
	}

	public void initView() {
		// 初始化数据
		username = (TextView) findViewById(R.id.username);
		content = (TextView) findViewById(R.id.content);
		zan = (BootstrapButton) findViewById(R.id.zan);
		sc = (BootstrapButton) findViewById(R.id.sc);
		icon = (ImageView) findViewById(R.id.iconImageView);

		username.setText(data.note.username);
		content.setText(data.note.content);
		zan.setText("赞:" + data.zannum);
		sc.setText("查看:" + data.collect);
		FssApi.IMAGECACHE.get(FssApi.AVATOR + data.note.username + "/"
				+ data.note.picture, icon);

		// 把评论的信息赋值上去
		replyArray = data.comment;
		L.i("NoteDetail:" + replyArray);
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
			Intent it = new Intent(this, NoteReplyActivity.class);
			startActivity(it);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.note_menu, menu);
		return true;
	}

}
