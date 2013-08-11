package shaotian.android.iamsingle.async;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import shaotian.android.iamsingle.LocChangeListener;
import shaotian.android.iamsingle.UIShared.MapMarkerManager;
import shaotian.android.iamsingle.UIShared.MapMarkerManager.MapMarkerInvalidStateException;
import shaotian.android.iamsingle.netsdk.LocationCommunicator;
import shaotian.android.iamsingle.netsdk.model.LocBoundBox;
import shaotian.android.iamsingle.netsdk.util.LocationList;

public class GetMapLocTask implements Runnable{
	private LatLngBounds bound=null;
	private LocationCommunicator wc=null;
	private LocChangeListener act=null;
    public GetMapLocTask(LatLngBounds bound, LocationCommunicator wc, LocChangeListener act)
    {
    	this.bound=new LatLngBounds( new LatLng(bound.southwest.latitude,bound.southwest.longitude), 
    								 new LatLng(bound.northeast.latitude,bound.northeast.longitude));
    	this.wc=wc;
    	this.act=act;
    }
    @Override
	public void run() {
			LatLng ne=bound.northeast;
			LatLng sw=bound.southwest;
			try {
				Log.d("app log", "start loc lis retrieve");

				LocationList lis=(LocationList) this.wc.getMap(new LocBoundBox(ne.latitude,ne.longitude,sw.latitude,sw.longitude));
				act.handleLocMapChange( lis);
				Log.d("app log", "loc lis retrieved with lenth "+lis.size());

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	

}
