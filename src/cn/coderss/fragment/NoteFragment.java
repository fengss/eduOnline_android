package cn.coderss.fragment;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import cn.coderss.activity.NoteDetailActivity;
import cn.coderss.adapter.BBSAdapter;
import cn.coderss.adapter.NoteAdapter;
import cn.coderss.api.FssApi;
import cn.coderss.bean.NoteBean;
import cn.coderss.edu.R;
import cn.coderss.pulltorefresh.PullToRefreshBase;
import cn.coderss.pulltorefresh.PullToRefreshListView;
import cn.coderss.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import cn.coderss.ui.BootstrapButton;
import cn.coderss.util.L;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class NoteFragment extends Fragment implements OnItemClickListener {
	View mview;
	Context mcontext;
	ListView listview;
	NoteAdapter adapter;
	ArrayList<NoteBean> datalist = new ArrayList<NoteBean>();
	private PullToRefreshListView mPullListView;
	// 页码和数量
	public String num = "10";
	public int page = 0;
	ProgressDialog dia;

	public NoteFragment(Context mcontext) {
		super();
		this.mcontext = mcontext;
		dia = new ProgressDialog(mcontext);
		dia.setTitle("提 示");
		dia.setMessage("数据正在加载......");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LayoutInflater li = LayoutInflater.from(mcontext);
		mview = li.inflate(R.layout.fragment_note, null);
		initView();
		loadData(true);
		return mview;
	}

	public void loadData(final boolean refresh) {
		FssApi.getHttp().get(FssApi.NOTE + "&num=" + num + "&=page" + page,
				new AjaxCallBack<Object>() {
					@Override
					public void onStart() {
						super.onStart();
						if (refresh) {
							page = 0;
							datalist.removeAll(datalist);
							adapter.notifyDataSetChanged();
							dia.show();
						}

					}

					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);
						L.i(t.toString());
						dia.dismiss();
						ArrayList<NoteBean> tmp = new Gson().fromJson(
								t.toString(),
								new TypeToken<ArrayList<NoteBean>>() {
								}.getType());

						for (NoteBean noteBean : tmp) {
							datalist.add(noteBean);
						}
						adapter.notifyDataSetChanged();

					}
				});
	}

	public void initView() {
		mPullListView = (PullToRefreshListView) mview
				.findViewById(R.id.mylistview);
		mPullListView.setPullLoadEnabled(false);
		mPullListView.setScrollLoadEnabled(true);
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadData(true);
				mPullListView.onPullDownRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				page++;
				loadData(false);
				mPullListView.onPullUpRefreshComplete();

			}
		});

		mPullListView.doPullRefreshing(true, 500);
		listview = mPullListView.getRefreshableView();

		adapter = new NoteAdapter(datalist, mcontext);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		LinearLayout ll = (LinearLayout) ((Activity) mcontext)
				.findViewById(R.id.titleView);
		LinearLayout ll2 = (LinearLayout) ((Activity) mcontext)
				.findViewById(R.id.titleView2);
		ll.removeAllViews();
		ll2.removeAllViews();

	}

	@Override
	public void onResume() {
		super.onResume();
		LinearLayout ll = (LinearLayout) ((Activity) mcontext)
				.findViewById(R.id.titleView);
		// 个人中心按钮
		BootstrapButton note = new BootstrapButton(mcontext);
		note.mytext = "我的手记";
		note.bootstrapType = "success";
		note.iconLeft = "fa-android";
		note.layoutWidth = 200;
		note.initialise(null);

		ll.addView(note);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent it = new Intent(mcontext, NoteDetailActivity.class);
		NoteBean bean = datalist.get(arg2);
		it.putExtra("id", bean.id);
		startActivity(it);
	}
}
