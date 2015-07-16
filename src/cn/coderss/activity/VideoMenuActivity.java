package cn.coderss.activity;

import java.util.ArrayList;

import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.coderss.adapter.VideoMenuAdapter;
import cn.coderss.api.FssApi;
import cn.coderss.bean.VideoBean;
import cn.coderss.edu.R;
import cn.coderss.impl.VideoDetailImpl;
import cn.coderss.impl.VideoDetailMenuImpl;
import cn.coderss.util.L;
import cn.coderss.util.T;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class VideoMenuActivity extends Activity implements OnItemClickListener {
	public ListView listview;
	public ArrayList<VideoBean> datalist = new ArrayList<VideoBean>();
	public VideoMenuAdapter adapter;
	public Context mcontext;
	public String id;
	public static VideoDetailMenuImpl impl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_menu);
		mcontext = this;
		id = getIntent().getStringExtra("id");
		loadData(true);
		initView();
	}

	public void loadData(final boolean refresh) {
		FssApi.getHttp().get(FssApi.VIDEO + "&tid=" + id,
				new AjaxCallBack<Object>() {
					@Override
					public void onStart() {
						super.onStart();
						if (refresh) {
							datalist.removeAll(datalist);
						}
					}

					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);
						ArrayList<VideoBean> temp = new Gson().fromJson(
								t.toString(),
								new TypeToken<ArrayList<VideoBean>>() {
								}.getType());
						for (VideoBean videoBean : temp) {
							datalist.add(videoBean);
						}
						adapter.notifyDataSetChanged();
					}
				});
	}

	public void initView() {
		listview = (ListView) findViewById(R.id.listview);

		adapter = new VideoMenuAdapter(datalist, mcontext);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		VideoBean bean = datalist.get(position);
		L.i("menu的bean:" + bean.toString());
		impl.UpdateVideoUrl(bean.id, bean.descr);
	}

}
