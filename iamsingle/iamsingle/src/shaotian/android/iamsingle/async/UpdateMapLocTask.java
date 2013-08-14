package shaotian.android.iamsingle.async;

import android.R;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import shaotian.android.iamsingle.UIShared.MapMarkerManager;
import shaotian.android.iamsingle.UIShared.MapMarkerManager.MapMarkerInvalidStateException;
import shaotian.android.iamsingle.netsdk.util.LocationList;

public class UpdateMapLocTask implements Runnable{
    private LocationList lis=null;
    private GoogleMap mMap=null;
    public UpdateMapLocTask(LocationList list,GoogleMap m)
    {
    	lis=list;
    	mMap=m;
    }
    
    private static void setMarkerGender(String gender, Marker m)
    {
    	if(gender.equals("f"))
    	{	m.setIcon(BitmapDescriptorFactory.fromResource(shaotian.android.iamsingle.R.drawable.marker_f));}
    	else
    	{	m.setIcon(BitmapDescriptorFactory.fromResource(shaotian.android.iamsingle.R.drawable.marker_m));}
    	
    }
    @Override
	public void run() {
		try{
			MapMarkerManager mgn=MapMarkerManager.Instance();
			Log.d("app log", "start update marker loop");

			mgn.start();
			for(int i=0;i<lis.size();i++)
			{
				shaotian.android.iamsingle.netsdk.model.Location loc=lis.lis.get(i);
				
			    int uid=loc.userid;
			    Marker m=null;
			    
			    if(!mgn.contains(uid))
				{	
			    	m=mMap.addMarker(new MarkerOptions()
				        		.position(new LatLng(loc.latitude, loc.longtitude))
				        		.title(loc.uname));
			    	
			    	setMarkerGender(loc.gender,m);
			    	mgn.addMarker(uid, m);
				}
			    else
			    {	//only update changed location
			    	LatLng newlatlng=new LatLng(loc.latitude, loc.longtitude);
			    	mgn.updateMarker(uid, newlatlng);
			    	
			    	
			    }
			}
			mgn.finish();
			Log.d("app log", "end update marker loop");

		}catch(MapMarkerInvalidStateException e)
		{
		}
		
	}

}
