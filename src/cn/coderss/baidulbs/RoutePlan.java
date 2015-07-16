package cn.coderss.baidulbs;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.http.AjaxCallBack;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.coderss.api.*;
import cn.coderss.bean.UserBean;
import cn.coderss.edu.R;
import cn.coderss.map.MyMapActivity;
import cn.coderss.util.L;
import cn.coderss.util.PreferenceConstants;
import cn.coderss.util.PreferenceUtils;
import cn.coderss.util.T;

import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.ArcOptions;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.nplatform.comapi.map.OverlayItem;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 此activity用来展示如何进行驾车、步行、公交路线搜索并在地图使用RouteOverlay、TransitOverlay绘制
 * 同时展示如何进行节点浏览并弹出泡泡
 */
public class RoutePlan extends Activity implements BaiduMap.OnMapClickListener,
		OnGetRoutePlanResultListener, LocationListener {
	private LocationManager mLocationManager = null;
	// 浏览路线节点相关
	Button mBtnPre = null;// 上一个节点
	Button mBtnNext = null;// 下一个节点
	Button Dingwei;
	int nodeIndex = -1;// 节点索引,供浏览节点时使用
	RouteLine route = null;
	OverlayManager routeOverlay = null;
	boolean useDefaultIcon = false;
	private TextView popupText = null;// 泡泡view
	private float x, y;
	// 地图相关，使用继承MapView的MyRouteMapView目的是重写touch事件实现泡泡处理
	// 如果不处理touch事件，则无需继承，直接使用MapView即可
	MapView mMapView = null; // 地图View
	BaiduMap mBaidumap = null;
	// 搜索相关
	RoutePlanSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
	private static final String LTAG = FssApi.class.getSimpleName();
	private com.baidu.location.LocationClient api_LocationClient;// 定位之用
	private SDKReceiver mReceiver;
	/**
	 * 定位相关
	 */
	public MyLocationListener mMyLocationListener;
	private LocationClient mLocationClient = null;
	public boolean flag = true;
	// 坐标标注点
	int i = 0;
	private boolean serach_flag = true;
	ArrayList<UserBean> users;

	/**
	 * 构造广播监听类，监听 SDK key 验证以及网络异常广播
	 */
	public class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			Log.d(LTAG, "action: " + s);
			// TextView text = (TextView) findViewById(R.id.text_Info);
			// text.setTextColor(Color.RED);
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				// text.setText("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				// text.setText("网络出错");
			}
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.routeplan);
		setActionBar();

		// 初始化地图
		mMapView = (MapView) findViewById(R.id.map);
		mBaidumap = mMapView.getMap();
		mBtnPre = (Button) findViewById(R.id.pre);
		mBtnNext = (Button) findViewById(R.id.next);
		mBtnPre.setVisibility(View.INVISIBLE);
		mBtnNext.setVisibility(View.INVISIBLE);
		// 地图点击事件处理
		mBaidumap.setOnMapClickListener(this);
		// 初始化搜索模块，注册事件监听
		mSearch = RoutePlanSearch.newInstance();
		mSearch.setOnGetRoutePlanResultListener(this);
		// 定位现在位置
		Dingwei = (Button) findViewById(R.id.Dingwei);

		// 定位初始化 默认位置中心点
		/**
		 * 初始化我的坐标
		 */
		mLocationClient = new LocationClient(this.getApplicationContext());
		api_LocationClient = mLocationClient;
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		InitLocation();
		api_LocationClient.start();

	}

	/**
	 * 发起路线规划搜索示例
	 * 
	 * @param v
	 */
	public void SearchButtonProcess(View v) {
		// 重置浏览节点的路线数据
		route = null;
		mBtnPre.setVisibility(View.INVISIBLE);
		mBtnNext.setVisibility(View.INVISIBLE);
		mBaidumap.clear();
		// 处理搜索按钮响应
		EditText editSt = (EditText) findViewById(R.id.start);
		EditText editEn = (EditText) findViewById(R.id.end);
		// 设置起终点信息，对于tranist search 来说，城市名无意义
		PlanNode stNode = PlanNode.withCityNameAndPlaceName("北京", editSt
				.getText().toString());
		PlanNode enNode = PlanNode.withCityNameAndPlaceName("北京", editEn
				.getText().toString());

		// 实际使用中请对起点终点城市进行正确的设定
		if (v.getId() == R.id.drive) {
			mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode)
					.to(enNode));
		} else if (v.getId() == R.id.transit) {
			mSearch.transitSearch((new TransitRoutePlanOption()).from(stNode)
					.city("北京").to(enNode));
		} else if (v.getId() == R.id.walk) {
			mSearch.walkingSearch((new WalkingRoutePlanOption()).from(stNode)
					.to(enNode));
		}

	}

	/**
	 * 节点浏览示例
	 * 
	 * @param v
	 */
	public void nodeClick(View v) {
		if (route == null || route.getAllStep() == null) {
			return;
		}
		if (nodeIndex == -1 && v.getId() == R.id.pre) {
			return;
		}
		// 设置节点索引
		if (v.getId() == R.id.next) {
			if (nodeIndex < route.getAllStep().size() - 1) {
				nodeIndex++;
			} else {
				return;
			}
		} else if (v.getId() == R.id.pre) {
			if (nodeIndex > 0) {
				nodeIndex--;
			} else {
				return;
			}
		}
		// 获取节结果信息
		LatLng nodeLocation = null;
		String nodeTitle = null;
		Object step = route.getAllStep().get(nodeIndex);
		if (step instanceof DrivingRouteLine.DrivingStep) {
			nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrace()
					.getLocation();
			nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
		} else if (step instanceof WalkingRouteLine.WalkingStep) {
			nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrace()
					.getLocation();
			nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
		} else if (step instanceof TransitRouteLine.TransitStep) {
			nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrace()
					.getLocation();
			nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
		}

		if (nodeLocation == null || nodeTitle == null) {
			return;
		}
		// 移动节点至中心
		mBaidumap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
		// show popup
		popupText = new TextView(RoutePlan.this);
		popupText.setTextColor(0xFF000000);
		popupText.setText(nodeTitle);
		mBaidumap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));

	}

	/**
	 * 切换路线图标，刷新地图使其生效 注意： 起终点图标使用中心对齐.
	 */
	public void changeRouteIcon(View v) {
		if (routeOverlay == null) {
			return;
		}
		if (useDefaultIcon) {
			((Button) v).setText("自定义起终点图标");
			Toast.makeText(this, "将使用系统起终点图标", Toast.LENGTH_SHORT).show();

		} else {
			((Button) v).setText("系统起终点图标");
			Toast.makeText(this, "将使用自定义起终点图标", Toast.LENGTH_SHORT).show();

		}
		useDefaultIcon = !useDefaultIcon;
		routeOverlay.removeFromMap();
		routeOverlay.addToMap();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(RoutePlan.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			mBtnPre.setVisibility(View.VISIBLE);
			mBtnNext.setVisibility(View.VISIBLE);
			route = result.getRouteLines().get(0);
			WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaidumap);
			mBaidumap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult result) {

		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(RoutePlan.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			mBtnPre.setVisibility(View.VISIBLE);
			mBtnNext.setVisibility(View.VISIBLE);
			route = result.getRouteLines().get(0);
			TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaidumap);
			mBaidumap.setOnMarkerClickListener(overlay);
			routeOverlay = overlay;
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	// 设置actionBar
	@SuppressLint("NewApi")
	private void setActionBar() {
		ActionBar actionbar = getActionBar();
		actionbar.setDisplayUseLogoEnabled(false);
		actionbar.setDisplayShowHomeEnabled(false);
		actionbar.setTitle("返回  附近学友导航");
		actionbar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult result) {
		if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(RoutePlan.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			// 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
			// result.getSuggestAddrInfo()
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			nodeIndex = -1;
			mBtnPre.setVisibility(View.VISIBLE);
			mBtnNext.setVisibility(View.VISIBLE);
			route = result.getRouteLines().get(0);
			DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
			routeOverlay = overlay;
			mBaidumap.setOnMarkerClickListener(overlay);
			overlay.setData(result.getRouteLines().get(0));
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	// 定制RouteOverly
	private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		public MyDrivingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

		public MyWalkingRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	private class MyTransitRouteOverlay extends TransitRouteOverlay {

		public MyTransitRouteOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
			}
			return null;
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			if (useDefaultIcon) {
				return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
			}
			return null;
		}
	}

	@Override
	public void onMapClick(LatLng point) {
		mBaidumap.hideInfoWindow();
	}

	@Override
	public boolean onMapPoiClick(MapPoi poi) {
		return false;
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		unregisterReceiver(mReceiver);
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mSearch.destroy();
		mMapView.onDestroy();
		mLocationClient.unRegisterLocationListener(mMyLocationListener);
		super.onDestroy();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	// 定位初始化
	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);
		option.setCoorType("bd09ll");
		int span = 1000;
		try {
			span = 3000;// 3秒更新一次
		} catch (Exception e) {
		}
		option.setScanSpan(span);
		option.setNeedDeviceDirect(true);// 设置设备指南方向
		option.setIsNeedAddress(false); // 反地址编码
		api_LocationClient.setLocOption(option);
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
			y = (float) location.getLatitude();// 纬度
			x = (float) location.getLongitude();// 经度

			// 设置全局经纬度坐标
			((FssApi) getApplication()).api_x = x;
			((FssApi) getApplication()).api_y = y;

			if (flag) {
				new MyHandler().sendEmptyMessage(1);
				flag = !flag;
				FssApi.getHttp().get(
						FssApi.UPGPS
								+ "&x="
								+ x
								+ "&y="
								+ y
								+ "&uid="
								+ PreferenceUtils.getPrefString(
										getApplicationContext(),
										PreferenceConstants.ID, "97"),
						new AjaxCallBack<Object>() {
							@Override
							public void onSuccess(Object t) {
								super.onSuccess(t);
								if (t.toString().equals("1")) {
									T.showShort(getApplicationContext(),
											"GPS信息上传成功");
								} else {
									T.showShort(getApplicationContext(),
											"GPS信息上传失败");
								}
							}

						});
			}

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

	/**
	 * 更新ui
	 */
	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:

				L.i("经度：" + x + ",纬度:" + y);

				if (x < 10) {
					x = (float) 121.48;
					y = (float) 31.22;
					T.showLong(getApplicationContext(), "您的gps未打开或网络延迟，请重新定位一次");
				}

				// 设定中心点坐标
				LatLng cenpt = new LatLng(y, x);
				// 定义地图状态
				MapStatus mMapStatus = new MapStatus.Builder().target(cenpt)
						.zoom(14).build();
				// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

				MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
						.newMapStatus(mMapStatus);
				// 改变地图状态
				mBaidumap.setMapStatus(mMapStatusUpdate);

				break;

			default:
				break;
			}
		}
	}

	/**
	 * 定位相关
	 * 
	 * @param v
	 */
	public void Dingwei(View v) {
		if (v.getId() == R.id.Dingwei) {
			new MyHandler().sendEmptyMessage(1);
		}
	}

	/**
	 * 搜索好友
	 */
	public void Friend_Search(View v) {
		if (v.getId() == R.id.Friend_lbs) {
			if (serach_flag) {
				// 添加圆
				if (x < 10) {
					T.showLong(getApplicationContext(),
							"您的gps未打开，或网络原因请重新搜索附近好友");
					return;
				}
				final LatLng ll = new LatLng(y, x);
				OverlayOptions ooCircle = new CircleOptions()
						.fillColor(0x5AFFFF00).center(ll)
						.stroke(new Stroke(5, 0xFFFF00FF)).zIndex(0)
						.radius(4000);
				mBaidumap.addOverlay(ooCircle);

				final BitmapDescriptor ico = new BitmapDescriptorFactory()
						.fromResource(R.drawable.mapaddress);

				final ArrayList<Marker> list = new ArrayList<Marker>();

				/**
				 * 这是自己的坐标点
				 */
				MarkerOptions m = new MarkerOptions()
						.icon(ico)
						.draggable(true)
						.perspective(false)
						.anchor(0.5f, 0.5f)
						.rotate(30)
						.zIndex(1)
						.title(PreferenceUtils.getPrefString(
								getApplicationContext(),
								PreferenceConstants.UserName, "fengss"))
						.position(ll);
				Marker a = (Marker) mBaidumap.addOverlay(m);

				// 第一个添加的是自己的坐标点
				list.add(a);

				// 这里获取你的好友并且标注在地图上
				FssApi.getHttp().get(FssApi.USERADDRESS,
						new AjaxCallBack<Object>() {
							@Override
							public void onSuccess(Object t) {
								super.onSuccess(t);

								// 用户列表
								users = new Gson().fromJson(t.toString(),
										new TypeToken<ArrayList<UserBean>>() {
										}.getType());
								if (users != null) {
									for (UserBean bean : users) {
										// 好友的位置
										LatLng ll = new LatLng(Float
												.valueOf(bean.address_Y), Float
												.valueOf(bean.address_X));
										// 直接地图的mark点标注出来
										MarkerOptions m = new MarkerOptions()
												.icon(ico).draggable(true)
												.perspective(false)
												.anchor(0.5f, 0.5f).rotate(30)
												.zIndex(1).title(bean.username)
												.position(ll);

										// 添加文字
										OverlayOptions ooText = new TextOptions()
												.fontSize(24)
												.fontColor(0xFFFF00FF)
												.zIndex(0).text(bean.name)
												.rotate(-30).position(ll);
										mBaidumap.addOverlay(ooText);

										Marker a = (Marker) mBaidumap
												.addOverlay(m);
										list.add(a);
									}
								}

							}
						});

				// 设置 附近的人 监听事件

				class MyMarket implements OnMarkerClickListener {

					public ArrayList<Marker> list;
					public ArrayList<UserBean> datalist;

					public MyMarket(ArrayList<Marker> list,
							ArrayList<UserBean> datalist) {
						super();
						this.list = list;
						this.datalist = datalist;

					}

					@Override
					public boolean onMarkerClick(Marker marker) {
						Button button = new Button(getApplicationContext());
						button.setBackgroundResource(R.drawable.popup);
						button.setText("查看");
						L.i("现在选中的值");
						button.setOnClickListener(new OnClickListener() {
							public void onClick(View v) {
								startActivity(new Intent(
										getApplicationContext(),
										MyMapActivity.class));
							}
						});
						LatLng ll = marker.getPosition();
						InfoWindow mInfoWindow = new InfoWindow(button, ll, -47);
						mBaidumap.showInfoWindow(mInfoWindow);
						return false;
					}

				}

				mBaidumap.setOnMarkerClickListener(new MyMarket(list, users));

			} else {
				mBaidumap.clear();
			}
			serach_flag = !serach_flag;
		}
	}

	/**
	 * 地图风格切换
	 */
	public void MapToSwitch(View v) {
		if (v.getId() == R.id.map_switch) {
			switch (i) {
			case 0:
				// 最普通的图
				mBaidumap.setBaiduHeatMapEnabled(false);
				mBaidumap.setTrafficEnabled(false);
				mBaidumap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				T.showShort(getApplicationContext(), "正显示最普通的地图");
				i++;
				break;
			case 1:
				// 卫星图
				mBaidumap.setBaiduHeatMapEnabled(false);
				mBaidumap.setTrafficEnabled(false);
				mBaidumap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				T.showShort(getApplicationContext(), "正显示卫星地图");
				i++;
				break;
			case 2:
				// 显示交通状况
				// 普通地图
				mBaidumap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				mBaidumap.setTrafficEnabled(true);
				T.showShort(getApplicationContext(), "正显示普通的交通状况地图");
				i++;
				break;
			case 3:
				// 显示交通状况
				// 卫星图
				mBaidumap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				mBaidumap.setTrafficEnabled(true);
				T.showShort(getApplicationContext(), "正显示卫星的交通状况地图");
				i++;
				break;
			case 4:
				// 热力图
				// 普通地图
				mBaidumap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				mBaidumap.setTrafficEnabled(false);
				mBaidumap.setBaiduHeatMapEnabled(true);
				T.showShort(getApplicationContext(), "正显示普通的热力地图");
				i++;
				break;
			case 5:
				// 热力图
				// 卫星图
				mBaidumap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				mBaidumap.setBaiduHeatMapEnabled(true);
				mBaidumap.setTrafficEnabled(false);
				T.showShort(getApplicationContext(), "正显示卫星的热力地图");
				i = 0;
				break;
			default:
				break;
			}
		}

	}
}
