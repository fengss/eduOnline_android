package cn.coderss.activity;

import cn.coderss.bean.BBSBean;
import cn.coderss.edu.R;
import cn.coderss.util.L;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@SuppressLint("NewApi")
public class BBSDetailActivity extends Activity implements View.OnClickListener {
	public TextView title, author, tag, addtime, looknum, content;
	public Button sc, send, look;
	public static BBSBean bean;
	public EditText sendContent;

	AlertDialog dia;
	ProgressDialog pro_dia;

	View dia_view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bbs_detail);
		initDia();
		setActionBar();
		initView();
	}

	public void initDia() {
		dia = new AlertDialog.Builder(this).create();
		dia_view = LayoutInflater.from(this).inflate(R.layout.view_note_reply,
				null);
		dia.setView(dia_view);
		sendContent = (EditText) dia_view.findViewById(R.id.content);
		dia.setButton("评论", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				L.i("我要评论了:" + sendContent.getText().toString());
			}
		});

		pro_dia = new ProgressDialog(this);
	}

	public void replyData(String content, String bbsid) {

	}

	public void setData() {
		title.setText(bean.title);
		author.setText(bean.username);
		content.setText(bean.content);
	}

	private void initView() {
		send = (Button) findViewById(R.id.send);
		look = (Button) findViewById(R.id.look);
		title = (TextView) findViewById(R.id.title);
		author = (TextView) findViewById(R.id.author);
		tag = (TextView) findViewById(R.id.tag);
		addtime = (TextView) findViewById(R.id.addtime);
		looknum = (TextView) findViewById(R.id.looknum);
		content = (TextView) findViewById(R.id.content);

		content.setText(Html.fromHtml(bean.getContent()));
		addtime.setText(bean.addtime);
		title.setText(bean.title);
		author.setText(bean.username);
		tag.setText(bean.keyword);

		send.setOnClickListener(this);
		look.setOnClickListener(this);
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

		default:
			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send:
			dia.show();
			break;
		case R.id.look:
			
			break;

		default:
			break;
		}
	}
}
