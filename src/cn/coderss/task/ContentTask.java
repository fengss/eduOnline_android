package cn.coderss.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.coderss.bean.BBSBean;
import cn.coderss.bean.TestInfo;
import cn.coderss.bitmap.FinalBitmap;
import cn.coderss.fragment.BBSFragment;
import cn.coderss.util.Helper;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ContentTask extends AsyncTask<String, Integer, List<BBSBean>> {

	private Context mContext;
	public int totalDataCount;
	private FinalBitmap fb;

	public ContentTask(Context context, int totalDataCount, FinalBitmap fb) {
		super();
		mContext = context;
		this.totalDataCount = totalDataCount;
		this.fb = fb;
	}

	@Override
	protected List<BBSBean> doInBackground(String... params) {
		try {
			return parseNewsJSON(params[0]);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(List<BBSBean> result) {
		if (result == null || result.size() <= 0) {// 有可能因为网络或者数据源本身无数据，如果没有此处逻辑会导致下拉刷新bar不被隐藏滨且无法刷新新数据
			totalDataCount = 0;
		}
		totalDataCount = result.size();
		for (BBSBean info : result) {
			fb.display(info);
		}
	}

	@Override
	protected void onPreExecute() {
	}

	public List<BBSBean> parseNewsJSON(String url) throws IOException,
			JSONException {
		List<BBSBean> duitangs = new ArrayList<BBSBean>();
		String json = "";
		if (Helper.checkConnection(mContext)) {
			try {
				json = Helper.getStringFromUrl(url);

			} catch (IOException e) {
				Log.e("IOException is : ", e.toString());
				e.printStackTrace();
				return duitangs;
			}
		}
		Log.d("MainActiivty", "json:" + json);

		ArrayList<BBSBean> temp = new Gson().fromJson(json,
				new TypeToken<ArrayList<BBSBean>>() {
				}.getType());
		for (BBSBean bbsBean : temp) {
			duitangs.add(bbsBean);
		}
		
		return duitangs;
	}
}
