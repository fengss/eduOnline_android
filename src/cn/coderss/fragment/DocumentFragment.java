package cn.coderss.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import cn.coderss.activity.DocumentDetailActivity;
import cn.coderss.adapter.DocumentAdapter;
import cn.coderss.api.FssApi;
import cn.coderss.bean.DocumentBean;
import cn.coderss.bean.NoteBean;
import cn.coderss.edu.R;
import cn.coderss.pulltorefresh.PullToRefreshBase;
import cn.coderss.pulltorefresh.PullToRefreshGridView;
import cn.coderss.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import cn.coderss.util.L;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("ValidFragment")
public class DocumentFragment extends Fragment implements OnItemClickListener {
	PullToRefreshGridView pull;
	GridView gridview;
	DocumentAdapter adapter;
	Context mcontext;
	ArrayList<DocumentBean> dataList = new ArrayList<DocumentBean>();
	View mview;
	// 页码
	int page = 0;
	int num = 10;

	public DocumentFragment(Context mcontext) {
		super();
		this.mcontext = mcontext;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LayoutInflater li = LayoutInflater.from(mcontext);
		mview = li.inflate(R.layout.fragment_document, null);
		initView();
		return mview;
	}

	public void loaddata(final boolean refresh) {
		FssApi.getHttp().get(
				FssApi.DOCUMENTINDEX + "&page=" + page + "&num" + num,
				new AjaxCallBack<Object>() {
					@Override
					public void onStart() {
						super.onStart();
						if (refresh) {
							page = 0;
							dataList.removeAll(dataList);
							adapter.notifyDataSetChanged();
						}
					}

					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);
						L.i("我的数据:" + t.toString());
						ArrayList<DocumentBean> tmp = new Gson().fromJson(
								t.toString(),
								new TypeToken<ArrayList<DocumentBean>>() {
								}.getType());
						if (tmp != null) {
							for (DocumentBean documentListBean : tmp) {
								dataList.add(documentListBean);
							}
						}

						adapter.notifyDataSetChanged();
					}
				});
	}

	public void initView() {
		pull = (PullToRefreshGridView) mview.findViewById(R.id.gridview);
		pull.setPullLoadEnabled(false);
		pull.setScrollLoadEnabled(true);
		pull.setOnRefreshListener(new OnRefreshListener<GridView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				loaddata(true);
				pull.onPullDownRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<GridView> refreshView) {
				page++;
				loaddata(false);
				pull.onPullUpRefreshComplete();

			}
		});

		pull.doPullRefreshing(true, 500);
		gridview = pull.getRefreshableView();
		gridview.setNumColumns(2);
		adapter = new DocumentAdapter(dataList, mcontext);
		gridview.setAdapter(adapter);

		gridview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View v, int position,
			long arg3) {
		Intent it = new Intent(mcontext, DocumentDetailActivity.class);
		DocumentBean bean = dataList.get(position);
		DocumentDetailActivity.bean = bean;
		DocumentDetailActivity.id = bean.id;
		startActivity(it);
	}
}
