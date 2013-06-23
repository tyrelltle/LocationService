package shaotian.android.iamsingle;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import shaotian.android.iamsingle.UIShared.CustomDialogFragment;
import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.netsdk.LocParam;
import shaotian.android.iamsingle.netsdk.WorldModeCommunicator;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import android.location.Location;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity implements  
											GooglePlayServicesClient.ConnectionCallbacks,
											GooglePlayServicesClient.OnConnectionFailedListener,
											LocationListener {
	private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private boolean mUpdatesRequested=false;
    // Stores the current instantiation of the location client in this object
    private LocationClient mLocationClient; 
    
    // Handle to SharedPreferences for this app
    SharedPreferences mPrefs;

    // Handle to a SharedPreferences editor
    SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMap();
       
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
	    		
	     
	}



	@Override
	public void onDisconnected() {
		Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();		
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
	}

	@Override
	public void onLocationChanged(Location location) {
		ApplicationInfo ai;
		try {
			ai = getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
			Bundle bundle = ai.metaData;
		    String myApiKey = bundle.getString("my_api_key");
			
	        String msg = "Updated Location: " +
	                Double.toString(location.getLatitude()) + "," +
	                Double.toString(location.getLongitude());
	        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	        
	      
					WorldModeCommunicator comm=new WorldModeCommunicator();
					comm.setServer(bundle.getString("serverip"), bundle.getInt("serverport"));
					comm.updateLoc(new LocParam(1,location.getAltitude(),location.getLatitude(),location.getLongitude()));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
				
		
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
