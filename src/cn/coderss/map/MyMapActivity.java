package cn.coderss.map;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.tsz.afinal.http.AjaxCallBack;
import cn.coderss.api.FssApi;
import cn.coderss.baidulbs.MyGeoCoder;
import cn.coderss.bean.UserBean;
import cn.coderss.edu.R;
import cn.coderss.map.adapter.MapAdapter;
import cn.coderss.util.L;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyMapActivity extends Activity implements OnItemClickListener {

	ListView mylistview;
	ArrayList<UserBean> datalist = new ArrayList<UserBean>();
	MapAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		initView();

		loadData();

		setActionBar();
	}

	@SuppressLint("NewApi")
	public void setActionBar() {
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowCustomEnabled(true);
		bar.setDisplayShowHomeEnabled(false);
		bar.setTitle("返回");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return true;
	}

	public void initView() {
		mylistview = (ListView) findViewById(R.id.listview);
		adapter = new MapAdapter(datalist, this);
		mylistview.setAdapter(adapter);
		mylistview.setOnItemClickListener(this);
	}

	/**
	 * 加载数据
	 */
	public void loadData() {
		FssApi.getHttp().get(FssApi.USERADDRESS, new AjaxCallBack<Object>() {
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);

				ArrayList<UserBean> tmp = new Gson().fromJson(t.toString(),
						new TypeToken<ArrayList<UserBean>>() {
						}.getType());

				for (UserBean userBean : tmp) {
					datalist.add(userBean);
				}

				adapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		UserBean bean = datalist.get(position);
		MyGeoCoder.MyGeouser = bean;
		L.i("我得出的信息" + bean.toString());
		Intent it = new Intent(this, MyGeoCoder.class);
		startActivity(it);
	}
}
