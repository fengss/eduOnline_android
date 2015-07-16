package cn.coderss.activity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import cn.coderss.api.FssApi;
import cn.coderss.bean.UserBean;
import cn.coderss.edu.R;
import cn.coderss.ui.BootstrapButton;
import cn.coderss.util.L;
import cn.coderss.util.PreferenceConstants;
import cn.coderss.util.PreferenceUtils;
import cn.coderss.util.T;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	public String email;
	public String password;
	public Context mcontext;

	BootstrapButton login, register;
	ImageView iconImageView;
	EditText username, pwd;
	CheckBox jzmm;

	ProgressDialog dia;
	MyHandler mhandler = new MyHandler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mcontext = this;

		setContentView(R.layout.activity_login);

		initView();
		initConfig();
		initData();
	}

	public void initData() {
		// 默认的头像地址
		String avator = FssApi.AVATOR
				+ PreferenceUtils.getPrefString(mcontext,
						PreferenceConstants.PICTURE, "553c44033ecc3.jpg");
		L.i("加载头像......" + avator);
		FssApi.IMAGECACHE.get(avator, iconImageView);

	}

	// 初始化配置信息
	public void initConfig() {
		boolean flag = PreferenceUtils.getPrefBoolean(mcontext,
				PreferenceConstants.jzmm, false);
		if (flag) {
			username.setText(PreferenceUtils.getPrefString(mcontext,
					PreferenceConstants.UserName, "fengss"));
			pwd.setText(PreferenceUtils.getPrefString(mcontext,
					PreferenceConstants.PassWord, "lala"));
			jzmm.setChecked(flag);
		}
	}

	public void initView() {
		login = (BootstrapButton) findViewById(R.id.login);
		register = (BootstrapButton) findViewById(R.id.register);
		username = (EditText) findViewById(R.id.email);
		pwd = (EditText) findViewById(R.id.password);
		jzmm = (CheckBox) findViewById(R.id.jzmm);
		iconImageView = (ImageView) findViewById(R.id.iconImageview);
		jzmm.setOnCheckedChangeListener(this);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		email = username.getText().toString();
		password = pwd.getText().toString();

		if (v.getId() == R.id.login) {
			if (email == null || email.equals("")) {
				T.showShort(this, "请输入用户名再执行登录");
			}
			AjaxParams p = new AjaxParams();
			p.put("email", email);
			p.put("password", password);
			FssApi.getHttp().post(FssApi.LOGIN, p, new AjaxCallBack<Object>() {
				@Override
				public void onStart() {
					super.onStart();
					dia = ProgressDialog.show(mcontext, "提 示",
							"正在登陆中..........");
				}

				@SuppressWarnings("deprecation")
				@Override
				public void onFailure(Throwable t, String strMsg) {
					super.onFailure(t, strMsg);
					dia.setMessage("对不起,信息发送失败,可能服务器又宕机了");
					new Thread() {
						public void run() {
							try {
								Thread.sleep(3000);
								mhandler.sendEmptyMessage(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}

						};
					}.start();

				}

				@Override
				public void onSuccess(Object t) {
					super.onSuccess(t);
					String result = t.toString();
					// 撤掉登陆框
					dia.dismiss();
					if (result.equals("ERROR1")) {
						T.showShort(mcontext, "该用户密码错误");
					} else if (result.equals("ERROR2")) {
						T.showShort(mcontext, "该用户不存在");
					} else {
						UserBean bean = new Gson().fromJson(t.toString(),
								UserBean.class);
						// 顺便跳转的时候存储用户临时信息
						PreferenceUtils.setPrefString(mcontext,
								PreferenceConstants.UserName, email);
						PreferenceUtils.setPrefString(mcontext,
								PreferenceConstants.PassWord, password);
						PreferenceUtils.setPrefString(mcontext,
								PreferenceConstants.SESSION_KEY, t.toString());
						PreferenceUtils.setPrefString(mcontext,
								PreferenceConstants.ID, bean.id);
						PreferenceUtils.setPrefString(mcontext,
								PreferenceConstants.SEX, bean.sex);
						PreferenceUtils.setPrefString(mcontext,
								PreferenceConstants.AGE, bean.age);
						PreferenceUtils.setPrefString(mcontext,
								PreferenceConstants.EMAIL, bean.email);
						PreferenceUtils.setPrefString(mcontext,
								PreferenceConstants.SESSION_KEY, t.toString());
						PreferenceUtils.setPrefString(mcontext,
								PreferenceConstants.PICTURE, bean.picture);
						L.i("我的头像" + bean.picture);
						PreferenceUtils.setPrefString(mcontext,
								PreferenceConstants.LEVEL, bean.level);
						PreferenceUtils
								.setPrefString(mcontext,
										PreferenceConstants.LOGOUTTIME,
										bean.logouttime);
						PreferenceUtils.setPrefString(mcontext,
								PreferenceConstants.LOGINTIME, bean.loginnum);
						Intent it = new Intent(mcontext, MainActivity.class);
						startActivityForResult(it, 200);
					}

				}
			});

		} else if (v.getId() == R.id.register) {

		}
	}

	@Override
	public void onCheckedChanged(CompoundButton v, boolean f) {
		if (v.getId() == R.id.jzmm && f == true) {
			// 记住密码功能
			if (username.getText().equals("")
					|| pwd.getText().toString().equals("")) {
				T.showShort(mcontext, "请先填写你的用户名和密码才能记住密码");
				v.setChecked(false);
			} else {
				PreferenceUtils.setPrefBoolean(mcontext,
						PreferenceConstants.jzmm, true);
				PreferenceUtils.setPrefString(mcontext,
						PreferenceConstants.UserName, username.getText()
								.toString());
				PreferenceUtils.setPrefString(mcontext,
						PreferenceConstants.PassWord, pwd.getText().toString());
			}
		} else {
			PreferenceUtils.setPrefBoolean(mcontext, PreferenceConstants.jzmm,
					false);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 200) {
			finish();
		}
	}

	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			dia.dismiss();
		}
	}
}
