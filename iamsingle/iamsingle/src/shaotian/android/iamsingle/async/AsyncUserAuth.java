package shaotian.android.iamsingle.async;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import shaotian.android.iamsingle.ChatListActivity;
import shaotian.android.iamsingle.MapActivity;
import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.async.ServiceMessageSystem.ServiceMessageSystemBinder;
import shaotian.android.iamsingle.async.ServiceUpdateLocation.ServiceUpdateLocBinder;

import shaotian.android.iamsingle.netsdk.util.LocationList;
import shaotian.android.iamsingle.wssdk.WSFactory;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;


public class AsyncUserAuth extends AsyncTask<Void, Void, JSONObject> {
	 //Async classes 
		//variables for messaging service
		private ServiceMessageSystem mService=null;
	    private boolean mBound = false;

	    private ServiceConnection mConnection = new ServiceConnection() {

	        @Override
	        public void onServiceConnected(ComponentName className,IBinder service) {
	            // We've bound to LocalService, cast the IBinder and get LocalService instance
	        	ServiceMessageSystemBinder binder = (ServiceMessageSystemBinder) service;
	            mService = binder.getService();
	            try {
					mService.startListenMsg();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            mBound = true;
	    	
	            
	        }

			@Override
			public void onServiceDisconnected(ComponentName name) {
				mBound = false;				
			}
	        
	    };

		public  enum ACTION{
			LOGON,REGISTER
		}

	
		private Context context;
		private String email,pwd;
		private ACTION action;
    	public AsyncUserAuth(ACTION action,Context context, String email,String pwd)
    	{
    		this.action=action;
    		this.email=email;
    		this.pwd=pwd;
    		this.context=context;
    	
    		
    	}
    	
		@Override
		protected JSONObject doInBackground(Void... params) {
			ApplicationInfo ai;
			LocationList ret=null;
			try {
				ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
				Bundle bundle = ai.metaData;

				
				switch(action)
				{
				case LOGON:	return WSFactory.Instance().LogonUser(email,pwd);
							
				default:	return WSFactory.Instance().RegisterUser(email,pwd);
				
				}
				
				/*LocationCommunicator comm=new LocationCommunicator();
				comm.setServer(bundle.getString("serverip"), bundle.getInt("serverport"));
				ret=(LocationList) comm.getMap(new shaotian.android.iamsingle.wssdk.model.Location(1,location.getAltitude(),location.getLatitude(),location.getLongitude()));*/
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}      

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result); 
			try {
				if(result==null)
					return;
				int uid=result.getInt("userid");
				if(uid==-1)
					return;
			      bindMessageService();

				//store to sharedpreference
				SharedPreferences settings = context.getSharedPreferences(SharedUtil.SHARED_PREFERENCES, 0);
			      SharedPreferences.Editor editor = settings.edit();
			      editor.putInt(SharedUtil.SHARED_UID, uid);
			      editor.commit();
			      Intent i=new Intent(context, MapActivity.class);
			     // i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			      context.startActivity(i);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		private void bindMessageService()
		{
	    	context.getApplicationContext().bindService(new Intent(context, ServiceMessageSystem.class), mConnection, Context.BIND_AUTO_CREATE);

		}
  
}
