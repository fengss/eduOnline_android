package cn.coderss.activity;

import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import cn.coderss.baidulbs.RoutePlan;
import cn.coderss.edu.R;
import cn.coderss.fragment.BBSFragment;
import cn.coderss.fragment.DocumentFragment;
import cn.coderss.fragment.MainFragment;
import cn.coderss.fragment.NoteFragment;
import cn.coderss.fragment.ProblemFragment;
import cn.coderss.fragment.TestFragment;
import cn.coderss.util.L;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		View.OnClickListener {
	private ResideMenu resideMenu;
	private MainActivity mContext;
	private ResideMenuItem itemNote, itemMain, itemBBs, itemTest, itemProblem,
			itemDocument, itemPerson, itemTag, itemMap;
	private SearchView searchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		// 设置目录
		setUpMenu();

		initView();
	}

	@SuppressLint("NewApi")
	public void initView() {
		searchView = (SearchView) findViewById(R.id.search);
		searchView.setQueryHint("搜索您需要的资源");
	}

	public void setUpMenu() {
		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);
		resideMenu.setScaleValue(0.6f);

		// 建立起item
		itemMain = new ResideMenuItem(this, R.drawable.icon_profile, "视频");
		itemMain.setOnClickListener(this);
		itemNote = new ResideMenuItem(mContext, R.drawable.icon_profile, "笔记");
		itemNote.setOnClickListener(this);
		itemDocument = new ResideMenuItem(mContext, R.drawable.icon_profile,
				"资料库");
		itemDocument.setOnClickListener(this);
		itemBBs = new ResideMenuItem(mContext, R.drawable.icon_profile, "贴吧");
		itemBBs.setOnClickListener(this);
		itemProblem = new ResideMenuItem(mContext, R.drawable.icon_profile,
				"提问");
		itemProblem.setOnClickListener(this);
		itemTest = new ResideMenuItem(mContext, R.drawable.icon_profile, "考试");
		itemTest.setOnClickListener(this);
		itemPerson = new ResideMenuItem(mContext, R.drawable.icon_profile,
				"个人中心");
		itemPerson.setOnClickListener(this);
		itemTag = new ResideMenuItem(mContext, R.drawable.icon_profile, "随机资源");
		itemTag.setOnClickListener(this);
		itemMap = new ResideMenuItem(mContext, R.drawable.icon_profile, "地图导航");
		itemMap.setOnClickListener(this);

		/**
		 * Left
		 */
		resideMenu.addMenuItem(itemMain, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemNote, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemBBs, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemDocument, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemProblem, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemTest, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemPerson, ResideMenu.DIRECTION_LEFT);

		/**
		 * Right
		 */
		resideMenu.addMenuItem(itemTag, ResideMenu.DIRECTION_RIGHT);
		resideMenu.addMenuItem(itemMap, ResideMenu.DIRECTION_RIGHT);

		// 不想滑动的一面
		// resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		// 默认首页加载进去
		changeFragment(new MainFragment(mContext));

		/**
		 * 左右按钮
		 */
		findViewById(R.id.title_bar_left_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
		findViewById(R.id.title_bar_right_menu).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
					}
				});

	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			// Toast.makeText(mContext, "Menu is opened!",
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		public void closeMenu() {
			// Toast.makeText(mContext, "Menu is closed!",
			// Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {

		Button btn = (Button) findViewById(R.id.title_bar_right_menu);
		// 首先关掉menu
		if (view == itemProblem) {
			btn.setVisibility(View.GONE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}

		if (view == itemMain) {
			changeFragment(new MainFragment(mContext));
		} else if (view == itemTest) {
			changeFragment(new TestFragment(mContext, this.resideMenu));
		} else if (view == itemBBs) {
			changeFragment(new BBSFragment(mContext));
		} else if (view == itemNote) {
			changeFragment(new NoteFragment(mContext));
		} else if (view == itemDocument) {
			changeFragment(new DocumentFragment(mContext));
		} else if (view == itemProblem) {
			changeFragment(new ProblemFragment(mContext));
		} else if (view == itemPerson) {
			Intent it = new Intent(this, PersonActivity.class);
			startActivity(it);
		} else if (view == itemTag) {
			Intent it = new Intent(this, TagActivity.class);
			startActivity(it);
		} else if (view == itemMap) {
			// 地图导航
			startActivity(new Intent(this, RoutePlan.class));
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		L.i("Activity dispatchTouchEvent");
		return resideMenu.dispatchTouchEvent(ev);
	}

	private void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

}
