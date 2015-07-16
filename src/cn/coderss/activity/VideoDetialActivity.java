package cn.coderss.activity;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import cn.coderss.adapter.VideoDetailAdapter;
import cn.coderss.api.FssApi;
import cn.coderss.bean.BBSBean;
import cn.coderss.bean.VideoBean;
import cn.coderss.edu.R;
import cn.coderss.edu.R.id;
import cn.coderss.impl.VideoDetailImpl;
import cn.coderss.impl.VideoDetailMenuImpl;
import cn.coderss.impl.ViderReplyImpl;
import cn.coderss.util.L;
import cn.coderss.util.PreferenceConstants;
import cn.coderss.util.PreferenceUtils;
import cn.coderss.util.T;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore.Video;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;

public class VideoDetialActivity extends TabActivity implements
		VideoDetailMenuImpl {
	ListView mlistview;
	ArrayList<VideoBean> datalist;
	VideoDetailAdapter detailAdapter;
	Context mcontext;
	TabHost tab;
	// 该分类的id
	String tid;
	AlertDialog dia = null;
	// 这个是现在的视频id
	String mid;
	// 代理
	public static ViderReplyImpl delegate;
	public static VideoDetailImpl replyDelegate, detaildescDelegate;
	// 当前的视频
	VideoBean myBean;
	// 评论内容
	String content;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mcontext = this;
		Intent it = getIntent();
		tid = it.getStringExtra("tid");

		initActionBar();
		setContentView(R.layout.activity_video_detail);

		loadOnlyData();
	}

	@SuppressLint("NewApi")
	public void initActionBar() {
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowHomeEnabled(false);
		bar.setTitle("返回");
	}

	/*
	 * 访问以下单个数据后进行赋值
	 */
	public void loadOnlyData() {
		final ProgressDialog progressdia = new ProgressDialog(mcontext);
		progressdia.setTitle("提示");
		progressdia.setMessage("正在加载,请稍后......");
		FssApi.getHttp().get(FssApi.VIDEO_DETAIL + "&tid=" + tid,
				new AjaxCallBack<Object>() {
					@Override
					public void onStart() {
						super.onStart();
						L.i("具体视频的地址:" + FssApi.VIDEO_DETAIL + "&tid=" + tid);
						progressdia.show();
					}

					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);
						if (t.toString().equals("null")) {
							progressdia.dismiss();
							AlertDialog dia = new AlertDialog.Builder(mcontext)
									.setTitle("提 示")
									.setMessage("对不起,载入失败,可能断网了\n或者服务器宕机了")
									.setNegativeButton("确定",
											new OnClickListener() {

												@Override
												public void onClick(
														DialogInterface arg0,
														int arg1) {
													finish();

												}
											}).show();
						} else {
							progressdia.dismiss();
						}
						myBean = new Gson().fromJson(t.toString(),
								VideoBean.class);
						L.i("我接受到的数据" + myBean.toString());
						if (myBean != null) {

						}
						// 加载界面
						initView();

					}

					@Override
					public void onFailure(Throwable t, String strMsg) {
						super.onFailure(t, strMsg);
						progressdia.dismiss();
						AlertDialog dia = new AlertDialog.Builder(mcontext)
								.setTitle("提 示")
								.setMessage("对不起,载入失败,可能断网了\n或者服务器宕机了")
								.setNegativeButton("确定", new OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										finish();

									}
								}).show();

					}
				});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.video_reply:
			// 首先获取到评论的界面
			View reply = LayoutInflater.from(mcontext).inflate(
					R.layout.view_video_reply, null);
			ImageView icon = (ImageView) reply.findViewById(R.id.icon);

			// // 默认的头像地址
			String avator = FssApi.AVATOR
					+ PreferenceUtils.getPrefString(mcontext,
							PreferenceConstants.PICTURE, "553c44033ecc3.jpg");
			// L.i("加载头像......" + avator);
			FssApi.IMAGECACHE.get(avator, icon);

			// 用户名
			TextView username = (TextView) reply.findViewById(R.id.username);
			final String uname = PreferenceUtils.getPrefString(mcontext,
					PreferenceConstants.UserName, "fss");
			username.setText(uname);

			// 评论内容
			final EditText c = (EditText) reply
					.findViewById(R.id.myreply_content);

			dia = new AlertDialog.Builder(mcontext).setTitle("评论")
					.setView(reply)
					.setNegativeButton("回复", new OnClickListener() {

						private ProgressDialog pgd;

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							if (mid == null) {
								T.showShort(mcontext, "请先观看某个视频\n才可进行发言");
								return;
							}
							content = c.getText().toString();
							L.i("评论的内容是:" + content);
							AjaxParams p = new AjaxParams();
							p.put("uid", PreferenceUtils.getPrefString(
									mcontext, PreferenceConstants.ID, "11"));
							p.put("vid", mid);
							p.put("content", content);
							FssApi.getHttp().post(FssApi.REPLYTO, p,
									new AjaxCallBack<Object>() {
										@Override
										public void onStart() {
											super.onStart();
											pgd = ProgressDialog.show(mcontext,
													"提 示", "正在评论中.......");
										}

										@Override
										public void onSuccess(Object t) {
											super.onSuccess(t);
											L.i(t.toString());
											if (!t.equals("ERROR")) {
												T.showShort(mcontext, "评论成功");
											}

											pgd.dismiss();
											// 刷新评论表
											delegate.updateReply(true);

										}

										@Override
										public void onFailure(Throwable t,
												String strMsg) {
											super.onFailure(t, strMsg);
											T.showLong(mcontext, "评论失败");
											pgd.dismiss();
										}
									});

						}
					}).setPositiveButton("取消", new OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							dia.dismiss();
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
		getMenuInflater().inflate(R.menu.videodetail, menu);
		return true;
	}

	@SuppressLint("NewApi")
	public void initView() {
		tab = getTabHost();
		Intent Reply = new Intent(this, ReplyActivity.class);
		Reply.putExtra("id", myBean.id);
		tab.addTab(tab.newTabSpec("tab1").setIndicator("评论").setContent(Reply));

		Intent menu = new Intent(this, VideoMenuActivity.class);
		VideoMenuActivity.impl = this;
		menu.putExtra("id", tid);
		tab.addTab(tab.newTabSpec("tab2").setIndicator("目录").setContent(menu));

		Intent Desc = new Intent(this, VideoDetailDesc.class);
		Desc.putExtra("descStr", myBean.descr);
		tab.addTab(tab.newTabSpec("tab3").setIndicator("详情").setContent(Desc));

	}

	@Override
	public void UpdateVideoUrl(String url, String other) {
		if (replyDelegate == null) {
			T.showShort(mcontext, "请先查看评论");
			return;
		}
		if (detaildescDelegate == null) {
			T.showShort(mcontext, "请先查看详情");
			return;
		}
		this.mid = url;
		// 评论更新一下
		replyDelegate.updateVideoId(url, other);
		// 详情更新一下
		detaildescDelegate.updateVideoId(url, other);
	}

}
