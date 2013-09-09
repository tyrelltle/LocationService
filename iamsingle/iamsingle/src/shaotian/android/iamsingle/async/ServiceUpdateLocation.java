package shaotian.android.iamsingle.async;
import shaotian.android.iamsingle.UIShared.SharedUtil;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
public class ServiceUpdateLocation extends Service 
								   implements 	GooglePlayServicesClient.ConnectionCallbacks,
												GooglePlayServicesClient.OnConnectionFailedListener,
												LocationListener{

	private LocationRequest mLocationRequest;
    private boolean mUpdatesRequested=false;

    // Stores the current instantiation of the location client in this object
    private LocationClient mLocationClient; 
    
	private ServiceUpdateLocBinder mBinder=new ServiceUpdateLocBinder();
	
	private Context context;
	private Activity activity;
	
	public void StartUpdateLoc(Context c)
	{
		this.context=c;
		this.activity=(Activity)c;
		//TODO is the three params correct?
		mLocationClient = new LocationClient(this, this, this);
        mLocationRequest=LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        // Set the update interval to 5 seconds
        mLocationRequest.setInterval(SharedUtil.UPDATE_INTERVAL);
        // Set the fastest update interval to 1 second
        mLocationRequest.setFastestInterval(SharedUtil.FASTEST_INTERVAL);
		
		mLocationClient.connect();
		
	}
	
	
    public class ServiceUpdateLocBinder extends Binder {
    	public ServiceUpdateLocation getService() {
            return ServiceUpdateLocation.this;
        }
    }
	
	
	
	@Override
	public IBinder onBind(Intent arg0) {
	
		return mBinder;
	}
	@Override
    public void onCreate() {

        
    }
 
    @Override
    public void onDestroy() {
        if (mLocationClient.isConnected()) {
            stopPeriodicUpdates();
        }
		mLocationClient.disconnect();
     
    }
     
    @Override
    public void onStart(Intent intent, int startid) {
         
       
    }
	@Override
	public void onLocationChanged(Location location) {
		String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        
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
	public void onConnectionFailed(ConnectionResult connectionResult) {
		 if (connectionResult.hasResolution()) {
	            try {
	                // Start an Activity that tries to resolve the error
	                connectionResult.startResolutionForResult(
	                		activity,
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
	        }
		
	}
	private void startPeriodicUpdates() {

        mLocationClient.requestLocationUpdates(mLocationRequest, this);
     
    }
	
    private void stopPeriodicUpdates() {
        mLocationClient.removeLocationUpdates(this);
   
    }
    
	@Override
	public void onConnected(Bundle arg0) {
		Location loc=mLocationClient.getLastLocation();
	    startPeriodicUpdates();
		
	}
	@Override
	public void onDisconnected() {
		// 
		if (mLocationClient.isConnected()) {
            stopPeriodicUpdates();
        }
		if(mLocationClient!=null&& mLocationClient.isConnected())
			mLocationClient.disconnect();
	}
	
	
}
