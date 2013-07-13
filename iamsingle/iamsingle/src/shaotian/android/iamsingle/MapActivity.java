package shaotian.android.iamsingle;

import java.util.Timer;
import java.util.TimerTask;

import shaotian.android.iamsingle.UIShared.CustomDialogFragment;
import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.async.AsyncGetGlobalLocMap;
import shaotian.android.iamsingle.async.AsyncRegister;
import shaotian.android.iamsingle.async.AsyncUpdateLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.location.Location;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.Toast;

public class MapActivity extends Activity implements  
											GooglePlayServicesClient.ConnectionCallbacks,
											GooglePlayServicesClient.OnConnectionFailedListener,
											LocationListener {
	private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private boolean mUpdatesRequested=false;
    // Stores the current instantiation of the location client in this object
    private LocationClient mLocationClient; 
    private Timer getMapTimer;
    private Context context;
    // Handle to SharedPreferences for this app
    SharedPreferences mPrefs;

    // Handle to a SharedPreferences editor
    SharedPreferences.Editor mEditor;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMap();
        context=this;
        
        
        mLocationClient = new LocationClient(this, this, this);
        mLocationRequest=LocationRequest.create();
        mLocationRequest.setPriority(
                LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(SharedUtil.UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(SharedUtil.FASTEST_INTERVAL);
        // Open Shared Preferences
        mPrefs = getSharedPreferences(SharedUtil.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        // Get an editor
        mEditor = mPrefs.edit();
        
    }

	private boolean servicesConnected()
    {
    	int resultCode=GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    	
    	if(ConnectionResult.SUCCESS==resultCode)
    		return true;
    	Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
    			resultCode,
                this,
                SharedUtil.CONNECTION_FAILURE_RESOLUTION_REQUEST);
        CustomDialogFragment dialogf=new CustomDialogFragment();
        
        dialogf.setDialog(errorDialog);
        dialogf.show(getFragmentManager(), "Location Updates");
        return false;
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
				throw new Exception("map service not installed, thus can not get map");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
        
        mMap.setMyLocationEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		 if (connectionResult.hasResolution()) {
	            try {
	                // Start an Activity that tries to resolve the error
	                connectionResult.startResolutionForResult(
	                        this,
	                        SharedUtil.CONNECTION_FAILURE_RESOLUTION_REQUEST);
	                /*
	                 * Thrown if Google Play services canceled the original
	                 * PendingIntent
	                 */
	            } catch (IntentSender.SendIntentException e) {
	                // Log the error
	                e.printStackTrace();
	            }
	        } else {
	            /*
	             * If no resolution is available, display a dialog to the
	             * user with the error.
	             */
	        	Toast.makeText(this, "connection error", Toast.LENGTH_SHORT).show();
	        }
		
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();	
		Location loc=mLocationClient.getLastLocation();
	    startPeriodicUpdates();
	    		
	    
	    //update map within given peroid
	    final Handler handler = new Handler();
	    getMapTimer = new Timer();
	    
	    TimerTask doAsynchronousTask = new TimerTask() {       
	        @Override
	        public void run() {
	            handler.post(new Runnable() {
	                public void run() {       
	                    try {
	                    	
	                 			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
	                 				new AsyncGetGlobalLocMap(context,mMap).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	                 			else
	                 				new AsyncGetGlobalLocMap(context,mMap).execute();
	                 	    
	                    } catch (Exception e) {
	                    	 Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();	                    }
	                }
	            });
	        }
	    };
	    getMapTimer.schedule(doAsynchronousTask, 0, (Integer)SharedUtil.getConfig(Integer.class, "getLocFrequency", context)); //execute in every 50000 ms
	    
	    
	    
	   
	}



	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();		
		getMapTimer.cancel();
	}
    	
	
	@Override
	protected void onStart() {
		mLocationClient.connect();
		super.onStart();
	}

	@Override
	protected void onStop() {
		
        if (mLocationClient.isConnected()) {
            stopPeriodicUpdates();
        }
		mLocationClient.disconnect();
		super.onStop();
		getMapTimer.cancel();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getMapTimer.cancel();
	}

	@Override
	public void onLocationChanged(Location location) {
		String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        
        //put location to shared preference
        SharedPreferences settings = getSharedPreferences(SharedUtil.SHARED_PREFERENCES, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putFloat(SharedUtil.SHARED_ALTITUTE, (float) location.getAltitude());
	      editor.putFloat(SharedUtil.SHARED_LATITUDE, (float) location.getLatitude());
	      editor.putFloat(SharedUtil.SHARED_LONGTITUDE, (float) location.getLongitude());
	      editor.commit();
	      
	      
		AsyncUpdateLocation locup=new AsyncUpdateLocation(this, location);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
			 locup.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		else
			 locup.execute();	
		
	
	}

    @Override
	protected void onResume() {

    	super.onResume();
	}

	private void startPeriodicUpdates() {

        mLocationClient.requestLocationUpdates(mLocationRequest, this);
     
    }
    private void stopPeriodicUpdates() {
        mLocationClient.removeLocationUpdates(this);
   
    }
    
    
   
    
}
