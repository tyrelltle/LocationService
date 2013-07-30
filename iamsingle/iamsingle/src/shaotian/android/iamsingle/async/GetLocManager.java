package shaotian.android.iamsingle.async;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import shaotian.android.iamsingle.LocChangeListener;
import shaotian.android.iamsingle.UIShared.MapMarkerManager;
import shaotian.android.iamsingle.UIShared.MapMarkerManager.MapMarkerInvalidStateException;
import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.netsdk.WorldModeCommunicator;
import shaotian.android.iamsingle.netsdk.model.LocBoundBox;
import shaotian.android.iamsingle.netsdk.util.LocationList;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

//thread pool thread for getting location map
public class GetLocManager extends Thread {
	// Async classes

	LocChangeListener mapActivity = null;
	Handler handler = null;
	WorldModeCommunicator wc = null;
	private static GetLocManager instance = null;
	

	private GetLocManager() {
	}

	public static GetLocManager instance() {
		if (instance == null)
			instance = new GetLocManager();
		return instance;

	}
	
	public void setListener(LocChangeListener act)
	{
		mapActivity=act;
		
	}



	@Override
	public void run() {
		
		ApplicationInfo ai;
		try {
			ai = ((Activity)mapActivity).getPackageManager().getApplicationInfo(((Activity)mapActivity).getPackageName(), PackageManager.GET_META_DATA);			
			Bundle bundle = ai.metaData;			
			wc = new WorldModeCommunicator();
			wc.setServer(bundle.getString("serverip"), bundle.getInt("serverport"));
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Looper.prepare();
		handler = new Handler();
		Looper.loop();
	}

	public synchronized void enqueTask(LatLngBounds bound) {
		GetMapLocTask task=new GetMapLocTask(bound,wc,mapActivity);
		handler.post(task);
		Log.d("app log", "posted new getMapLocTask");

		
	}









}
