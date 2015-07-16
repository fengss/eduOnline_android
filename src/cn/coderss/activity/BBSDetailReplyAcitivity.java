package cn.coderss.activity;

import cn.coderss.edu.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class BBSDetailReplyAcitivity extends Activity {
	ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_bbs_detail_reply);

		initView();
	}

	public void initView() {
		listview = (ListView) findViewById(R.id.listview);
		
	}
}
