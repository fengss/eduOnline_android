package cn.coderss.fragment;

import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.special.ResideMenu.ResideMenu;

import cn.coderss.activity.TestDetailActivity;
import cn.coderss.adapter.TestAdapter;
import cn.coderss.adapter.TestfliperAdapter;
import cn.coderss.api.FssApi;
import cn.coderss.bean.CateBean;
import cn.coderss.bean.TestBean;
import cn.coderss.edu.R;
import cn.coderss.pulltorefresh.PullToRefreshBase;
import cn.coderss.pulltorefresh.PullToRefreshListView;
import cn.coderss.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import cn.coderss.util.L;
import cn.coderss.util.T;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class TestFragment extends Fragment implements OnItemClickListener {
	View mview;
	private PullToRefreshListView mPullListView;
	ArrayList<CateBean> cateDataList = new ArrayList<CateBean>();
	ArrayList<TestBean> datalist = new ArrayList<TestBean>();
	ListView mlistview;
	TestAdapter myadapter;
	TestfliperAdapter viewflipperAdapter;
	AdapterViewFlipper viewflipper;
	Context mcontext;
	ResideMenu menu;
	FinalHttp http;
	SpinnerAdapter cateAdapter;
	Spinner cate;

	ProgressDialog pro;

	// 分类下的试卷
	String MyCate;
	boolean isCate = false;

	public TestFragment(Context mcontext, ResideMenu menu) {
		super();
		this.mcontext = mcontext;
		this.menu = menu;
		http = ((FssApi) mcontext.getApplicationContext()).getHttp();
	}

	public void createProgress() {
		if (pro == null) {
			pro = new ProgressDialog(mcontext).show(mcontext, "正在加载",
					"您的信息正在加载,请等待.......");
		}
	}

	public void removeProgress() {
		pro.dismiss();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LayoutInflater li = LayoutInflater.from(mcontext);
		mview = li.inflate(R.layout.fragment_test, null);

		// 载入分类的数据
		loadCateData();
		// 初始化界面
		initView();
		// 载入主要的数据
		loadData(false, 0);

		return mview;
	}

	@SuppressLint("NewApi")
	public void initView() {
		mPullListView = (PullToRefreshListView) mview.findViewById(R.id.myview);
		mPullListView.setPullLoadEnabled(false);
		mPullListView.setScrollLoadEnabled(true);
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {

				mPullListView.onPullDownRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				mPullListView.onPullUpRefreshComplete();
			}
		});

		mPullListView.doPullRefreshing(true, 500);
		mlistview = mPullListView.getRefreshableView();
		mlistview.setOnItemClickListener(this);

		myadapter = new TestAdapter(datalist, mcontext);
		mlistview.setAdapter(myadapter);

		viewflipper = (AdapterViewFlipper) mview.findViewById(R.id.fliper);
		viewflipperAdapter = new TestfliperAdapter(datalist, mcontext);
		viewflipper.setAdapter(viewflipperAdapter);
		viewflipper.startFlipping();

	}

	/**
	 * 载入主页的数据
	 */
	public void loadData(final boolean refresh, int page) {
		String url = FssApi.GETTEST + "&page=" + page;
		if (isCate) {
			if (!MyCate.equals("8080")) {
				url += "&pid=" + MyCate;
			}
		}
		L.i("我的URL：" + url);
		// 判断刷新与否
		if (refresh) {
			datalist.removeAll(datalist);
		}

		http.get(url, new AjaxCallBack<Object>() {

			@Override
			public void onStart() {
				super.onStart();

				if (refresh) {
					datalist.removeAll(datalist);
					myadapter.notifyDataSetChanged();
				}

				// 创建等待
				createProgress();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);

				if (t.toString().equals("No Data")) {
					T.showLong(mcontext, "没有数据哦!^_^");
					return;
				} else {
					// 数据载入成功
					ArrayList<TestBean> tmp = new ArrayList<TestBean>();
					tmp = new Gson().fromJson(t.toString(),
							new TypeToken<ArrayList<TestBean>>() {
							}.getType());
					for (TestBean testBean : tmp) {
						datalist.add(testBean);
					}
					myadapter.notifyDataSetChanged();
					// 移除等待
					removeProgress();
				}
			}
		});
	}

	/**
	 * 载入分类的数据
	 */
	public void initCate() {
		cate = (Spinner) mview.findViewById(R.id.test_category);
		cateAdapter = new SpinnerAdapter() {

			@Override
			public void unregisterDataSetObserver(DataSetObserver arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void registerDataSetObserver(DataSetObserver arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public int getViewTypeCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getView(int arg0, View v, ViewGroup arg2) {
				View view = LayoutInflater.from(mcontext).inflate(
						R.layout.adapter_cate, null);
				TextView title = (TextView) view.findViewById(R.id.title);
				title.setText(cateDataList.get(arg0).name.toString());
				return view;
			}

			@Override
			public int getItemViewType(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public long getItemId(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public Object getItem(int arg0) {
				// TODO Auto-generated method stub
				return arg0;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return cateDataList.size();
			}

			@Override
			public View getDropDownView(int arg0, View arg1, ViewGroup arg2) {
				View view = LayoutInflater.from(mcontext).inflate(
						R.layout.adapter_cate, null);
				TextView title = (TextView) view.findViewById(R.id.title);
				L.i(cateDataList.get(arg0).name.toString());
				title.setText(cateDataList.get(arg0).name.toString());
				return view;
			}
		};
		cate.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View v,
					int position, long arg3) {
				String result = cateDataList.get(position).name;
				// 当选中分类后去执行这个分类下面的数据然后加载loadData
				isCate = true;
				MyCate = cateDataList.get(position).id;
				L.i("现在分类的ID:" + MyCate);
				loadData(true, 0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		cate.setAdapter(cateAdapter);
	}

	public void loadCateData() {
		http.get(((FssApi) mcontext.getApplicationContext()).GETCAT,
				new AjaxCallBack<Object>() {
					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);
						cateDataList = new Gson().fromJson(t.toString(),
								new TypeToken<ArrayList<CateBean>>() {
								}.getType());
						cateDataList.add(0, new CateBean("8080", "全部", "0",
								"0", "1"));
						// 初始化Spinner的数据
						initCate();
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		TestBean bean = datalist.get(position);
		L.i("我获得的试卷ID：" + bean.id);
		Intent it = new Intent(mcontext, TestDetailActivity.class);
		it.putExtra("id", bean.id);
		it.putExtra("title", bean.title);
		startActivity(it);
	}

}
