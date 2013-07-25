package shaotian.android.iamsingle.async;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import shaotian.android.iamsingle.UIShared.MapMarkerManager;
import shaotian.android.iamsingle.UIShared.MapMarkerManager.MapMarkerInvalidStateException;
import shaotian.android.iamsingle.UIShared.SharedUtil;
import shaotian.android.iamsingle.netsdk.WorldModeCommunicator;
import shaotian.android.iamsingle.netsdk.model.LocBoundBox;
import shaotian.android.iamsingle.netsdk.util.LocationList;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;


public class AsyncGetGlobalLocMap extends AsyncTask<Void, Void, LocationList> {
	 //Async classes 
  


		private GoogleMap mMap;
		private Context context;

		private LatLngBounds bound=null;
    	public AsyncGetGlobalLocMap(Context context,GoogleMap map,LatLngBounds b)
    	{
    		
    		mMap=map;
    		this.context=context;
    		
    		this.bound=b;
    	}
    	
		@Override
		protected LocationList doInBackground(Void... params) {
			
			ApplicationInfo ai;
			LocationList ret=null;
			try {
				ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
				Bundle bundle = ai.metaData;

				//get visible region bounding box
				
				LatLng ne=bound.northeast;
				LatLng sw=bound.southwest;
				
				WorldModeCommunicator comm=new WorldModeCommunicator();
				comm.setServer(bundle.getString("serverip"), bundle.getInt("serverport"));
				ret=(LocationList) comm.getMap(new LocBoundBox(ne.latitude,ne.longitude,sw.latitude,sw.longitude));
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
		protected    void onPostExecute(LocationList result) {
			//update map markers
			super.onPostExecute(result);
			//mMap.clear();
			if(result==null)
				return;
			
			MapMarkerManager mgn=MapMarkerManager.Instance();
			synchronized(mgn){
				try{
					mgn.start();
					for(int i=0;i<result.size();i++)
					{
						shaotian.android.iamsingle.netsdk.model.Location loc=result.lis.get(i);
						
					    int uid=loc.userid;
					    Marker m=null;
					    
					    if(!mgn.contains(uid))
						{	
					    	m=mMap.addMarker(new MarkerOptions()
						        		.position(new LatLng(loc.latitude, loc.longtitude))
						        		.title("user at "+loc.latitude+" , "+loc.longtitude));
						
					    	mgn.addMarker(uid, m);
						}
					    else
					    {	//only update changed location
					    	LatLng newlatlng=new LatLng(loc.latitude, loc.longtitude);
					    	mgn.updateMarker(uid, newlatlng);
					    	
					    	
					    }
					}
					mgn.finish();
				}catch(MapMarkerInvalidStateException e)
				{
					Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG ).show();
				}
			}
		}
  
}
