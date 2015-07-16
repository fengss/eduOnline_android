package cn.coderss.activity;

import cn.coderss.edu.R;
import cn.coderss.ui.LabelView;
import cn.coderss.ui.LabelView.OnItemClickListener;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class TagActivity extends Activity {
	private EditText mEditText;
	private LabelView mLabelView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag);

		mEditText = (EditText) findViewById(R.id.et_input);
		mLabelView = (LabelView) findViewById(R.id.lv);

		mLabelView.setLabels(new String[] { "文件", "编辑", "Android", "Google",
				"馒头", "大米", "服务" });
		mLabelView.setColorSchema(new int[] { Color.DKGRAY, Color.CYAN,
				Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED });
		mLabelView.setSpeeds(new int[][] { { 1, 2 }, { 1, 1 }, { 2, 1 },
				{ 2, 3 } });
		mLabelView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(int index, String label) {
				Toast.makeText(TagActivity.this,
						"index : " + index + ",label : " + label,
						Toast.LENGTH_SHORT).show();
				mEditText.setText(label);
			}
		});

		setActionBar();
	}

	@SuppressLint("NewApi")
	public void setActionBar() {
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setDisplayShowHomeEnabled(false);
		bar.setTitle("返回");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.note_reply:
			Intent it = new Intent(this, NoteReplyActivity.class);
			startActivity(it);
			break;
		default:
			break;
		}
		return true;
	}

}
