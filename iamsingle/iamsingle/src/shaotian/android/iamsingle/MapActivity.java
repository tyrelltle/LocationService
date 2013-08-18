package shaotian.android.iamsingle;

import java.util.Timer;
import java.util.TimerTask;

import shaotian.android.iamsingle.UIShared.MapMarkerManager;
import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.UIShared.TimedMarker;
import shaotian.android.iamsingle.async.GetLocManager;
import shaotian.android.iamsingle.async.ServiceUpdateLocation;
import shaotian.android.iamsingle.async.ServiceUpdateLocation.ServiceUpdateLocBinder;
import shaotian.android.iamsingle.async.UpdateMapLocTask;
import shaotian.android.iamsingle.netsdk.util.LocationList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class MapActivity extends Activity implements LocChangeListener{
	
	private GoogleMap mMap;
	private ServiceUpdateLocation mService=null;
	private Handler handler=null;
    GetLocManager locMgr=null;
    private Timer getMapTimer;
    private Context context;
    // Handle to SharedPreferences for this app
    SharedPreferences mPrefs;

    // Handle to a SharedPreferences editor
    SharedPreferences.Editor mEditor;
    
    LatLngBounds bound;
    private boolean mBound = false;
    
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
        	ServiceUpdateLocBinder binder = (ServiceUpdateLocBinder) service;
            mService = binder.getService();
            mService.StartUpdateLoc(context);
            mBound = true;
    	
            
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
            
        }

	
    };
    
    

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMap();
        context=this;
        handler=new Handler();
        MapMarkerManager.Initialize(TimedMarker.class);
        
        // Open Shared Preferences
        mPrefs = getSharedPreferences(SharedUtil.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        // Get an editor
        mEditor = mPrefs.edit();
        bindService();
        locMgr=GetLocManager.instance();
        locMgr.setListener(this);
        locMgr.start();
        if(mMap!=null){
	        mMap.setOnCameraChangeListener(new OnCameraChangeListener(){
	        	
				@Override
				public void onCameraChange(CameraPosition position) {
					Log.d("app log", "map drag hiten");
					locMgr.enqueTask(GetLocManager.Priority.LOW,mMap.getProjection().getVisibleRegion().latLngBounds);
					
				}});
	        
	        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){

				@Override
				public boolean onMarkerClick(Marker marker) {
					// TODO Auto-generated method stub
					int uid=MapMarkerManager.Instance().getUidByMarker(marker);
					if(uid==-1)
						return false;
					Intent i=new Intent(context, UserInfoActivity.class);
					
					i.putExtra("uid", uid);
				    context.startActivity(i);
					
					return true;
				}});
	        
	        
	        startGetLocTimer();
	    }
    }

    public void startGetLocTimer() {

	    //update map within given peroid
	    final Handler handler = new Handler();
	    getMapTimer = new Timer();

	    TimerTask doAsynchronousTask = new TimerTask() {       
	        @Override
	        public void run() {
	            handler.post(new Runnable() {
	                public void run() {       
						locMgr.enqueTask(GetLocManager.Priority.HIGH,mMap.getProjection().getVisibleRegion().latLngBounds);
     
	                }
	            });
	        }
	    };
	    getMapTimer.schedule(doAsynchronousTask, 0, (Integer)SharedUtil.getConfig(Integer.class, "getLocFrequency", context)); //execute in every 50000 ms
	}
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    
		super.onActivityResult(requestCode, resultCode, data);
	}


	public void initializeMap()
    {
        mMap=((MapFragment)this.getFragmentManager().findFragmentById(R.id.map)).getMap();
        if(mMap==null)
			try {
				Toast.makeText(context, "map service not installed, thus can not get map", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
			
				e.printStackTrace();
				return;
			}
        
        mMap.setMyLocationEnabled(true);
 
    }

	
	@Override
	protected void onStart() {
        
		super.onStart();
	}

	@Override
	protected void onStop() {
		
        
		super.onStop();
		unbindService();
		if(getMapTimer!=null)
			getMapTimer.cancel();
	}

	@Override
	protected void onPause() {
		super.onPause();
		unbindService();
		if(getMapTimer!=null)
			getMapTimer.cancel();
	}



    @Override
	protected void onResume() {

    	bindService();
    	super.onResume();
    	
    }

    private void bindService(){
    	
    	getApplicationContext().bindService(new Intent(this, ServiceUpdateLocation.class), mConnection, Context.BIND_AUTO_CREATE);
   
    }

    private void unbindService(){
    	
		if(mBound)
		{
			getApplicationContext().unbindService(this.mConnection);
			mBound=false;
		}
    }
    @Override
    public void handleLocMapChange(LocationList result)
    {	
		this.handler.post(new UpdateMapLocTask(result,mMap));
    }

   
    
}
