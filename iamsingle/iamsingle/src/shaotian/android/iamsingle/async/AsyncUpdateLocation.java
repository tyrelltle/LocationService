package shaotian.android.iamsingle.async;



import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.wssdk.LocationCommunicator;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;


public class AsyncUpdateLocation extends AsyncTask<Void, Void, Void> {
	 //Async classes 
  
		
    	Context context;
    	Location location;
    	public AsyncUpdateLocation(Context context,Location location)
    	{
    		this.context=context;
    		this.location=location;
    		
    	}
    	
		@Override
		protected Void doInBackground(Void... params) {
			int uid=context.getSharedPreferences(SharedUtil.SHARED_PREFERENCES, 0).getInt(SharedUtil.SHARED_UID,-1);
			ApplicationInfo ai;
			try {
				ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
				Bundle bundle = ai.metaData;

				LocationCommunicator comm=new LocationCommunicator();
				comm.setServer(bundle.getString("serverip"), bundle.getInt("serverport"));
				comm.updateLoc(new shaotian.android.iamsingle.netsdk.model.Location(uid,location.getLatitude(),location.getLongitude()));
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}      

        
  
}
