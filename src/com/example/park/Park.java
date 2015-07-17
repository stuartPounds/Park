package com.example.park;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOvelray;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class Park extends Activity {

	
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private RoutePlanSearch mSearch;
	LatLng point1 = new LatLng(39.963175, 116.400244); 
	LatLng point2 = new LatLng(39.961175, 116.300244);
	
	OverlayManager routeOverlay = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());  
        setContentView(R.layout.main);  
        //获取地图控件引用  
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap(); 
       
        go();
       set();
     
    }  
	 public  void  go()
	 {
		 mSearch=RoutePlanSearch.newInstance();

     PlanNode start=PlanNode .withLocation(point1);
     PlanNode end=PlanNode .withLocation(point2);
		mSearch.drivingSearch((new DrivingRoutePlanOption())  
			    .from(start)  
		    .to(end));
     OnGetRoutePlanResultListener listener = new mysrearchlistener() ;
     mSearch.setOnGetRoutePlanResultListener(listener);
     
	 }
	 
	 
		public class mysrearchlistener implements  OnGetRoutePlanResultListener
		{

			@Override
			public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
				// TODO Auto-generated method stub
				DrivingRouteOvelray overlay = new DrivingRouteOvelray(mBaiduMap);
	            routeOverlay = overlay;
	            mBaiduMap.setOnMarkerClickListener(overlay);
	           overlay.setData( arg0.getRouteLines().get(0));
	            overlay.addToMap();
	            overlay.zoomToSpan();
			}

			@Override
			public void onGetTransitRouteResult(TransitRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}

		

	public void set()
	{
	
		//构建Marker图标  
		BitmapDescriptor bitmap1 = BitmapDescriptorFactory  
		    .fromResource(R.drawable.icon_marka);  
		BitmapDescriptor bitmap2 = BitmapDescriptorFactory  
			    .fromResource(R.drawable.icon_markb); 
		//构建MarkerOption，用于在地图上添加Marker  
		OverlayOptions option1 = new MarkerOptions()  
		    .position(point1)  
		    .icon(bitmap1);  
		OverlayOptions option2 = new MarkerOptions()  
	    .position(point2)  
	    .icon(bitmap2);  
		//在地图上添加Marker，并显示  
		mBaiduMap.addOverlay(option1);
		mBaiduMap.addOverlay(option2);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// activity 销毁时同时销毁地图控件
		mMapView.onDestroy();
	}
	
	
}
	
	






