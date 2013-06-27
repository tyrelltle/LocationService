package shaotian.android.iamsingle.async;



import shaotian.android.iamsingle.netsdk.WorldModeCommunicator;

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
			ApplicationInfo ai;
			try {
				ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
				Bundle bundle = ai.metaData;

				WorldModeCommunicator comm=new WorldModeCommunicator();
				comm.setServer(bundle.getString("serverip"), bundle.getInt("serverport"));
				comm.updateLoc(new shaotian.android.iamsingle.netsdk.model.Location(1,location.getAltitude(),location.getLatitude(),location.getLongitude()));
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