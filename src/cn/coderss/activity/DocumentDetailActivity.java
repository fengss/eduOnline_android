package cn.coderss.activity;

import cn.coderss.api.FssApi;
import cn.coderss.bean.DocumentBean;
import cn.coderss.edu.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DocumentDetailActivity extends Activity {
	Context mcontext;
	WebView web;
	public static String id;
	public static DocumentBean bean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mcontext = this;
		setContentView(R.layout.fragment_document_detail);

		initView();

		setActionBar();
	}

	private void initView() {
		web = (WebView) findViewById(R.id.webview);
		web.loadUrl("http://edu.coderss.cn/index.php/Library/detail/id/" + id);
		TextView title = (TextView) findViewById(R.id.title);
		ImageView image = (ImageView) findViewById(R.id.iconImageView);

		if (bean != null) {
			FssApi.IMAGECACHE.get(FssApi.AVATOR + bean.username + "/"
					+ bean.picture, image);

			title.setText("资源名称:" + bean.name + "   资源大小:" + bean.size);
		}

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
}
