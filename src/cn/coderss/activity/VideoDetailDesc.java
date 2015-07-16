package cn.coderss.activity;

import java.util.ArrayList;

import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.coderss.api.FssApi;
import cn.coderss.bean.VideoBean;
import cn.coderss.edu.R;
import cn.coderss.impl.VideoDetailImpl;
import cn.coderss.util.L;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.TextView;

public class VideoDetailDesc extends Activity implements VideoDetailImpl {
	public String tid;
	public String descStr;
	TextView desc;
	public VideoBean myBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		descStr = getIntent().getStringExtra("descStr");
		setContentView(R.layout.activity_detail_desc);
		VideoDetialActivity.detaildescDelegate = this;
		loadData();
	}

	public void loadData() {
		initView();
	}

	public void initView() {
		desc = (TextView) findViewById(R.id.desc);
		desc.setText(descStr);
	}

	@Override
	public void updateVideoId(String id, String other) {
		desc.setText(other);
	}
}
