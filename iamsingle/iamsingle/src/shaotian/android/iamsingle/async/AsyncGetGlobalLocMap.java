package shaotian.android.iamsingle.async;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.netsdk.WorldModeCommunicator;
import shaotian.android.iamsingle.netsdk.util.LocationList;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;


public class AsyncGetGlobalLocMap extends AsyncTask<Void, Void, LocationList> {
	 //Async classes 
  


		private GoogleMap mMap;
		private Context context;
		private float alti,lati,longti=0;
    	public AsyncGetGlobalLocMap(Context context,GoogleMap map)
    	{
    		
    		mMap=map;
    		this.context=context;
    		
    	    this.lati=context.getSharedPreferences(SharedUtil.SHARED_PREFERENCES, 0).getFloat(SharedUtil.SHARED_LATITUDE,-1);
    	    this.longti=context.getSharedPreferences(SharedUtil.SHARED_PREFERENCES, 0).getFloat(SharedUtil.SHARED_LONGTITUDE,-1);
    	    this.alti=context.getSharedPreferences(SharedUtil.SHARED_PREFERENCES, 0).getFloat(SharedUtil.SHARED_ALTITUTE,-1);

    		
    	}
    	
		@Override
		protected LocationList doInBackground(Void... params) {
			
			ApplicationInfo ai;
			LocationList ret=null;
			try {
				ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
				Bundle bundle = ai.metaData;

				WorldModeCommunicator comm=new WorldModeCommunicator();
				comm.setServer(bundle.getString("serverip"), bundle.getInt("serverport"));
				ret=(LocationList) comm.getMap(new shaotian.android.iamsingle.netsdk.model.Location(1,alti,lati,longti));
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ret;
		}      

		@Override
		protected void onPostExecute(LocationList result) {
			super.onPostExecute(result);
			mMap.clear();
			for(int i=0;i<result.size();i++)
			{
				shaotian.android.iamsingle.netsdk.model.Location loc=result.lis.get(i);
			
				mMap.addMarker(new MarkerOptions()
				        .position(new LatLng(loc.latitude, loc.longtitude))
				        .title("user at "+loc.latitude+" , "+loc.longtitude));
				
			}
		}
  
}
