package cn.coderss.fragment;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import cn.coderss.activity.ProblemAddActivity;
import cn.coderss.adapter.NoteAdapter;
import cn.coderss.adapter.ProblemAdapter;
import cn.coderss.api.FssApi;
import cn.coderss.bean.PorblemAddCateBean;
import cn.coderss.bean.ProblemBean;
import cn.coderss.bean.ProblemDetailBean;
import cn.coderss.edu.R;
import cn.coderss.pulltorefresh.PullToRefreshBase;
import cn.coderss.pulltorefresh.PullToRefreshListView;
import cn.coderss.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import cn.coderss.ui.BootstrapButton;
import cn.coderss.util.L;
import cn.coderss.util.PreferenceConstants;
import cn.coderss.util.PreferenceUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressLint({ "NewApi", "ValidFragment" })
public class ProblemFragment extends Fragment implements OnItemClickListener {
	View mview;
	Context mcontext;
	ListView listview;
	ProblemAdapter adapter;
	ArrayList<ProblemBean> datalist = new ArrayList<ProblemBean>();
	ProgressDialog dia;
	private PullToRefreshListView mPullListView;
	int page = 0;

	int select = 0;

	AlertDialog detail_dia;
	View dia_view;
	ProblemDetailBean detail_bean;
	Spinner cateGory;
	Spinner Problem_cate;

	public String pid;

	public String selectId = "";

	boolean flag = false;

	public ProblemFragment(Context mcontext) {
		super();
		this.mcontext = mcontext;
		dia = new ProgressDialog(mcontext);
		dia.setTitle("提 示");
		dia.setMessage("正在加载数据.......");
		dia.setCancelable(false);

		detail_dia = new AlertDialog.Builder(mcontext).create();
		dia_view = LayoutInflater.from(mcontext).inflate(
				R.layout.view_problem_detail, null);
		detail_dia.setView(dia_view);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LayoutInflater li = LayoutInflater.from(mcontext);
		mview = li.inflate(R.layout.fragment_problem, null);
		return mview;
	}

	/**
	 * 载入分类的数据
	 */
	public void loadCate() {
		FssApi.getHttp().get(FssApi.GETTYPE, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);

				final PorblemAddCateBean bean = new Gson().fromJson(
						t.toString(), PorblemAddCateBean.class);
				if (bean != null) {
					ArrayList<String> cates = bean.getCust_names();
					L.i("分类数据:" + cates.toString());
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							mcontext, android.R.layout.simple_spinner_item,
							cates);
					cateGory.setAdapter(adapter);
					cateGory.setSelection(select);
					cateGory.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int arg2, long arg3) {
							String myselectId = bean.getCust_ids().get(arg2);
							if (!selectId.equals(myselectId)) {
								selectId = myselectId;
								select = arg2;
								loadData(true, true);
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});
				}
			}
		});
	}

	public void loadData(final boolean refresh, final boolean search) {
		L.i("我访问的页面" + FssApi.PROBLEMINDEX + "&page=" + page);
		if (refresh) {
			dia.show();
			datalist.removeAll(datalist);
			page = 0;
		}

		String url;
		String params;

		if (search) {
			url = FssApi.PROBLEMINDEX;
			params = url + "&page=" + page + "&pid=" + selectId;
		} else {
			url = FssApi.PROBLEMINDEX;
			params = url + "&page=" + page;
		}

		FssApi.getHttp().get(params, new AjaxCallBack<Object>() {
			@Override
			public void onStart() {
				super.onStart();

			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				// 消除等待框
				dia.dismiss();
				// 开始载入分类
				loadCate();
				ArrayList<ProblemBean> tmp = new Gson().fromJson(t.toString(),
						new TypeToken<ArrayList<ProblemBean>>() {
						}.getType());
				for (ProblemBean problemBean : tmp) {
					datalist.add(problemBean);
				}

				// 刷新页面
				adapter.notifyDataSetChanged();
			}
		});
	}

	public void initView() {
		mPullListView = (PullToRefreshListView) mview
				.findViewById(R.id.listview);
		mPullListView.setPullLoadEnabled(false);
		mPullListView.setScrollLoadEnabled(true);
		mPullListView.setPullRefreshEnabled(false);
		mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				loadData(true, false);
				mPullListView.onPullDownRefreshComplete();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				page++;
				loadData(false, false);
				mPullListView.onPullUpRefreshComplete();

			}
		});

		listview = mPullListView.getRefreshableView();

		adapter = new ProblemAdapter(datalist, mcontext);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		cateGory = (Spinner) mview.findViewById(R.id.tag);
		Problem_cate = (Spinner) mview.findViewById(R.id.category);
	}

	@Override
	public void onResume() {
		super.onResume();
		initView();
		loadData(true, false);

		LinearLayout ll = (LinearLayout) ((Activity) mcontext)
				.findViewById(R.id.titleView);
		// 个人中心按钮
		BootstrapButton person = new BootstrapButton(mcontext);
		person.mytext = "个人中心";
		person.bootstrapType = "success";
		person.iconLeft = "fa-android";
		person.layoutWidth = 310;
		person.initialise(null);

		ll.addView(person);

		// 提问去按钮
		LinearLayout ll2 = (LinearLayout) ((Activity) mcontext)
				.findViewById(R.id.titleView2);
		BootstrapButton toPro = new BootstrapButton(mcontext);
		toPro.mytext = "提问去";
		toPro.iconLeft = "fa-android";
		toPro.layoutWidth = 250;
		toPro.initialise(null);
		toPro.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(mcontext, ProblemAddActivity.class);
				startActivity(it);
			}
		});

		ll2.addView(toPro);
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
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		// 取出当前的模型
		ProblemBean bean = datalist.get(position);

		dia.show();
		FssApi.getHttp().get(FssApi.PROBLEMDETAIL + "&id=" + bean.id,
				new AjaxCallBack<Object>() {
					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);
						dia.dismiss();
						detail_bean = new Gson().fromJson(t.toString(),
								ProblemDetailBean.class);

						// 模型赋值
						TextView username = (TextView) dia_view
								.findViewById(R.id.username);
						TextView addtime = (TextView) dia_view
								.findViewById(R.id.addtime);
						TextView tag = (TextView) dia_view
								.findViewById(R.id.tag);
						TextView problem = (TextView) dia_view
								.findViewById(R.id.problem);
						TextView replyname = (TextView) dia_view
								.findViewById(R.id.replyname);
						TextView replytime = (TextView) dia_view
								.findViewById(R.id.replytime);
						TextView answer = (TextView) dia_view
								.findViewById(R.id.answer);
						final BootstrapButton sc = (BootstrapButton) dia_view
								.findViewById(R.id.sc);
						sc.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								AjaxParams p = new AjaxParams();
								p.put("uid", PreferenceUtils.getPrefString(
										mcontext, PreferenceConstants.ID, "30"));
								p.put("qid", detail_bean.id);
								if (flag) {
									p.put("vv", "n");
								} else {
									p.put("vv", "y");
								}
								flag = !flag;

								FssApi.getHttp().post(FssApi.PROBLEMSC, p,
										new AjaxCallBack<Object>() {
											@Override
											public void onSuccess(Object t) {
												super.onSuccess(t);
												L.i(t.toString());
												if (t.equals("OK")) {
													if (flag) {
														sc.setText("取消收藏");
													} else {
														sc.setText("收藏");
													}
												}
											}
										});

							}
						});

						username.setText("用户名:" + detail_bean.username);
						addtime.setText("提示时间:" + detail_bean.addtime);
						tag.setText("标签:" + detail_bean.cname);
						problem.setText("问题:" + detail_bean.content);
						replytime.setText("回复时间:" + detail_bean.rtime);
						replyname.setText("回复人:" + detail_bean.tname);
						answer.setText("回复:" + detail_bean.replay);

						detail_dia.show();
					}

					@Override
					public void onFailure(Throwable t, String strMsg) {
						super.onFailure(t, strMsg);
						dia.dismiss();
					}
				});

	}

}
