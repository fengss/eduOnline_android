package cn.coderss.activity;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxCallBack;
import cn.coderss.adapter.ReplyAdapter;
import cn.coderss.api.FssApi;
import cn.coderss.bean.VideoBean;
import cn.coderss.bean.VideoReplyBean;
import cn.coderss.edu.R;
import cn.coderss.impl.VideoDetailImpl;
import cn.coderss.impl.VideoDetailMenuImpl;
import cn.coderss.impl.ViderReplyImpl;
import cn.coderss.util.L;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class ReplyActivity extends Activity implements ViderReplyImpl,
		VideoDetailImpl {
	public ListView listview;
	public ReplyAdapter adapter;
	public Context mcontext;
	public String id;
	public ArrayList<VideoReplyBean> replylist = new ArrayList<VideoReplyBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mcontext = this;
		VideoDetialActivity.delegate = this;
		VideoDetialActivity.replyDelegate = this;
		id = getIntent().getStringExtra("id");
		setContentView(R.layout.activity_reply);
		loadData(true);
		initView();
	}

	public void loadData(boolean refresh) {
		if (id == null) {
			return;
		}
		FssApi.getHttp().get(FssApi.REPLY + "&id=" + id,
				new AjaxCallBack<Object>() {
					@Override
					public void onStart() {
						super.onStart();
						// 移除之前所有的评论
						replylist.removeAll(replylist);
					}

					@Override
					public void onSuccess(Object t1) {
						super.onSuccess(t1);
						// 如果值为空
						if (t1.toString().equals("null")) {
							return;
						}
						ArrayList<VideoReplyBean> tmp = new Gson().fromJson(
								t1.toString(),
								new TypeToken<ArrayList<VideoReplyBean>>() {
								}.getType());
						for (VideoReplyBean videoReplyBean : tmp) {
							replylist.add(videoReplyBean);
						}
						adapter.notifyDataSetChanged();
					}
				});
	}

	public void initView() {
		listview = (ListView) findViewById(R.id.mylistView);
		adapter = new ReplyAdapter(replylist, mcontext);
		listview.setAdapter(adapter);
	}

	@Override
	public void updateReply(boolean refresh) {
		if (refresh) {
			// 获取数据了
			this.loadData(true);
		}
	}

	@Override
	public void updateVideoId(String id, String other) {
		this.id = id;
		loadData(true);
	}

}
