package cn.coderss.fragment;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxCallBack;
import cn.coderss.activity.MainActivity;
import cn.coderss.activity.VideoDetialActivity;
import cn.coderss.adapter.VideoAdapter;
import cn.coderss.api.FssApi;
import cn.coderss.bean.CateBean;
import cn.coderss.edu.R;
import cn.coderss.pulltorefresh.PullToRefreshBase;
import cn.coderss.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import cn.coderss.pulltorefresh.PullToRefreshListView;
import cn.coderss.util.L;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

@SuppressLint({ "ValidFragment", "NewApi" })
public class MainFragment extends Fragment implements OnItemClickListener {
	View mview;
	Context mcontext;
	ListView listview;
	VideoAdapter adapter;
	ArrayList<CateBean> datalist = new ArrayList<CateBean>();
	AlertDialog mydia;
	private PullToRefreshListView mPullListView;
	ProgressDialog dia;

	// 搜索栏目
	SearchView search;

	public MainFragment(Context mcontext) {
		super();
		this.mcontext = mcontext;
		dia = new ProgressDialog(mcontext);
		dia.setTitle("提 示");
		dia.setCancelable(false);
		dia.setMessage("数据正在加载......");

		// 设置搜索栏
		setSearchBar();
	}

	public void setSearchBar() {
		search = (SearchView) ((Activity) mcontext).findViewById(R.id.search);
		search.setOnSearchClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				L.i("我即将搜索");
			}

		});

	}

	public void loadCatData(final boolean refresh) {
		if (refresh) {
			datalist.removeAll(datalist);
		}
		FssApi.getHttp().get(FssApi.VIDEOCAT, new AjaxCallBack<Object>() {
			@Override
			public void onStart() {
				super.onStart();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				dia.dismiss();
				// 获取到分类数据进行json解析
				ArrayList<CateBean> tmp = new Gson().fromJson(t.toString(),
						new TypeToken<ArrayList<CateBean>>() {
						}.getType());

				// 更新
				for (CateBean cateBean : tmp) {
					datalist.add(cateBean);
				}

				// 现在获取到分类数据,准备初始化view
				adapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LayoutInflater li = LayoutInflater.from(mcontext);
		mview = li.inflate(R.layout.fragment_main, null);

		loadCatData(true);
		initView();
		return mview;
	}

	public void initView() {
		mPullListView = (PullToRefreshListView) mview
				.findViewById(R.id.VideoList);
		mPullListView.setPullLoadEnabled(false);
		mPullListView.setScrollLoadEnabled(true);
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				dia.show();
				loadCatData(true);
				mPullListView.onPullDownRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mydia = new AlertDialog.Builder(mcontext).setTitle("提 示")
						.setMessage("已经到底端了\n没有数据了")
						.setNegativeButton("确定", new OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int i) {
								mydia.dismiss();
							}
						}).show();
				mPullListView.onPullUpRefreshComplete();

			}
		});

		mPullListView.doPullRefreshing(true, 500);
		listview = mPullListView.getRefreshableView();
		listview.setOnItemClickListener(this);

		adapter = new VideoAdapter(datalist, mcontext);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent it = new Intent(mcontext, VideoDetialActivity.class);
		CateBean bean = datalist.get(arg2);
		it.putExtra("tid", bean.id);
		startActivity(it);
	}
}
