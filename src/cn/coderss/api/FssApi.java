package cn.coderss.api;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;

import cn.coderss.util.L;
import cn.coderss.util.PreferenceConstants;
import cn.coderss.util.PreferenceUtils;
import cn.coderss.util.T;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.util.CacheManager;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.widget.TextView;

public class FssApi extends Application {
	/**
	 * 定位相关
	 */
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public TextView mLocationResult, logMsg;
	public TextView trigger, exit;
	public Vibrator mVibrator;
	/**
	 * 经度，纬度坐标
	 */
	public float api_x, api_y;
	public MyLocationListener mMyLocationListener;

	private static FinalHttp http;
	// ((FssApi)mcontext.getApplicationContext())
	public static ImageCache IMAGECACHE = CacheManager.getImageCache();

	/**
	 * 默认资源分类获取
	 */
	public static String GETTYPE = "http://edu.coderss.cn/index.php/Cat/typeSelectForIos";

	/**
	 * 图片资料
	 */
	public static String IMAGE = "http://edu.coderss.cn/Public/Uploads/videopic/";

	/**
	 * 资源目录
	 */
	public static String UPLOADS = "http://edu.coderss.cn/Public/Uploads/";

	// ＃＃＃＃＃＃＃＃＃＃＃＃考试的主页＃＃＃＃＃＃＃＃＃＃＃
	// 各类URL地址
	/**
	 * 获取试卷分类的资源
	 */
	public static String GETCAT = "http://edu.coderss.cn/index.php/Test/getCatForAndroid";
	/**
	 * 页面的数量
	 */
	public static int NUM = 20;
	/**
	 * 试卷的获取资源 传入num为数量 page为页数 pid为试卷类型
	 */
	public static String GETTEST = "http://edu.coderss.cn/index.php?m=Test&a=indexForAndroid";
	/**
	 * 获取具体的试卷资源
	 */
	public static String TESTDETAIL = "http://edu.coderss.cn/index.php?m=Test&a=detailForAndroid";
	/**
	 * 获取试卷的具体试题
	 */
	public static String QUEST = "http://edu.coderss.cn/index.php?m=Test&a=detailForAndroid";
	/**
	 * 算出试卷的成绩
	 */
	public static String SCORE = "http://edu.coderss.cn/index.php?m=Test&a=scoreForAndroid";

	// ＃＃＃＃＃＃＃＃＃＃＃＃视频的主页＃＃＃＃＃＃＃＃＃＃＃
	/**
	 * 获取视频的分类
	 */
	public static String VIDEOCAT = "http://edu.coderss.cn/index.php?m=Video&a=getCatForAndroid";
	/**
	 * 获取视频的资源
	 */
	public static String VIDEO = "http://edu.coderss.cn/index.php?m=Video&a=getVideoForIos";
	/**
	 * 获取视频的详细资料
	 */
	public static String VIDEO_DETAIL = "http://edu.coderss.cn/index.php?m=Video&a=getonlyVideo";
	/**
	 * 获取评论
	 */
	public static String REPLY = "http://edu.coderss.cn/index.php?m=Videocom&a=getReplyForIos";

	// ＃＃＃＃＃＃＃＃＃＃＃＃用户的主页＃＃＃＃＃＃＃＃＃＃＃
	/**
	 * 用户地址
	 */
	public static String USERADDRESS = "http://edu.coderss.cn/index.php?m=Users&a=getUserAddress";
	/**
	 * 用户登录
	 */
	public static String LOGIN = "http://edu.coderss.cn/index.php?m=Users&a=dologinForIos";

	/**
	 * 用户头像前地址
	 */
	public static String AVATOR = "http://edu.coderss.cn/Public/Uploads/users/";

	/**
	 * 视频评论
	 */
	public static String REPLYTO = "http://edu.coderss.cn/index.php?m=Videocom&a=addvideocomForIos";

	// ＃＃＃＃＃＃＃＃＃＃＃＃笔记的主页＃＃＃＃＃＃＃＃＃＃＃
	/**
	 * 获得笔记 num数量 page第几页面 传进vid就返回该视频下的笔记 传进uid用户的id就返回用户上传的笔记
	 */
	public static String NOTE = "http://edu.coderss.cn/index.php?m=Note&a=indexForIos";
	/**
	 * 视频的缩略图
	 */
	public static String NOTEPIC = "http://edu.coderss.cn/Public/Uploads/videopic/";

	/**
	 * 获取笔记的单条记录 id 笔记的id
	 */
	public static String NOTEDETAIL = "http://edu.coderss.cn/index.php?m=Note&a=detailForAndroid";

	/**
	 * 评论笔记
	 */
	// uid 用户的id
	// nid 笔记的id
	// content 评论的内容 -- POST
	// 返回为ERROR 说明失败
	public static String NOTEREPLY = "http://edu.coderss.cn/index.php?m=Notecom&a=addCommentForIos";

	// ＃＃＃＃＃＃＃＃＃＃＃＃资料库的主页＃＃＃＃＃＃＃＃＃＃＃
	/**
	 * 资料库首页
	 */
	// get传递
	// num 获取的数量
	// page 获取第几页
	// 搜索条件
	// q 资料的名称
	// pid 根据资源的分类id获取
	// uid 根据用户的id获取
	public static String DOCUMENTINDEX = "http://edu.coderss.cn/index.php?m=Library&a=indexForAndroid";

	// ＃＃＃＃＃＃＃＃＃＃＃＃问吧的主页＃＃＃＃＃＃＃＃＃＃＃
	/**
	 * 问吧首页
	 */
	// get 传递
	// num 数量
	// page 页码
	public static String PROBLEMINDEX = "http://edu.coderss.cn/index.php?m=Question&a=indexForAndroid";

	/**
	 * 问吧详情页 ID为问吧的id
	 */
	public static String PROBLEMDETAIL = "http://edu.coderss.cn/index.php?m=Question&a=showForAndroid";

	/**
	 * 问吧收藏 POST传递 uid 用户id qid 问题id vv y取消为收藏 n为收藏
	 */
	public static String PROBLEMSC = "http://edu.coderss.cn/index.php/Question/likeForAndroid";

	/**
	 * 问吧搜索 post传递
	 */
	// pid 分类的类别id
	// num 数量
	// page 页码
	public static String PROBLEMSEARCH = "http://edu.coderss.cn/index.php/Question/index2ForIos";

	/**
	 * 问吧添加问题 post传递 在添加问题前我先要知道要提问向哪个老师，所以要从以下url获取数据
	 */
	public static String PROBLEMGETTEACHER = "http://edu.coderss.cn/index.php/Question/addForAndroid";

	/**
	 * 然后填充数据提交 post传递
	 */
	// tid 教师的id
	// keyword 分类的id,逗号隔开
	// content 内容
	public static String PROBLEMADD = "http://edu.coderss.cn/index.php/Question/insertForIos";

	// ＃＃＃＃＃＃＃＃＃＃＃＃贴吧的主页＃＃＃＃＃＃＃＃＃＃＃
	/**
	 * 获取贴吧首页内容 get或者post
	 */
	// num 数量
	// page 页码
	public static String BBSINDEX = "http://edu.coderss.cn/index.php?m=Message&a=indexForAndroid";

	/**
	 * 搜索贴吧的内容 get传递
	 */
	// pid 类别的id
	// num 数量
	// page 页码
	public static String BBSSEARCH = "http://edu.coderss.cn/index.php/Message/index2ForIos";

	/**
	 * 贴吧的详情 -- 这里要用afnetworking的session mangerget传递
	 */
	// id 帖子的id
	public static String BBSDETAIL = "http://edu.coderss.cn/index.php/Message/show/id/";

	/**
	 * 贴吧的评论数据 GET 传递 id 为帖子的id
	 */
	public static String BBSREPLY = "http://edu.coderss.cn/index.php?m=Message&a=showCommForIos";

	/**
	 * 增加贴吧数据 增加贴吧的数据 post 传递
	 */
	// uid 用户的id
	// content 贴吧的内容
	// title 贴吧标题
	// keyword 你的标签就是分类的id,用逗号隔开
	// 返回yes为成功no为失败
	public static String BBSADD = "http://edu.coderss.cn/index.php/Message/insertForIos";

	/**
	 * GPS地址上传
	 */
	public static String UPGPS = "http://edu.coderss.cn/index.php?m=Users&a=UpGps";
	public boolean flag = false;

	@Override
	public void onCreate() {
		super.onCreate();
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());

		setHttp(new FinalHttp());
		mVibrator = (Vibrator) getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
	}

	/**
	 * 经纬度计算
	 * 
	 * @author shenwei
	 * 
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {


			// Receive Location
			api_y = (float) location.getLatitude();// 纬度
			api_x = (float) location.getLongitude();// 经度

			StringBuffer sb = new StringBuffer(256);
			sb.append("时间点 : ");
			sb.append(location.getTime());
			sb.append("\n代号 : ");
			sb.append(location.getLocType());
			sb.append("\n纬度 : ");
			sb.append(location.getLatitude());
			sb.append("\n经度 : ");
			sb.append(location.getLongitude());
			sb.append("\n半径 : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			L.i(sb.toString());
			L.i("fss_im lbs:", sb.toString());
		}
	}

	public static FinalHttp getHttp() {
		return http;
	}

	public static void setHttp(FinalHttp http) {
		FssApi.http = http;
	}
}
