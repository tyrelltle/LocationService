package shaotian.android.iamsingle.UIShared;

import java.util.HashMap;

import com.google.android.gms.maps.model.Marker;

public class MapMarkerList {

	static private MapMarkerList mInstance=null;
	
	private HashMap<Integer, Marker> markers=new HashMap<Integer, Marker>();
	//key as user id
	public static MapMarkerList Instance()
	{
		if(mInstance==null)
			mInstance=new MapMarkerList();
		return mInstance;
		
	}
	
	public void addMarker(int key,Marker m)	
	{
			markers.put(key, m);		
	}
	
	public boolean containsKey(int key)
	{
		return markers.containsKey(key);
	}
	
	public Marker getMarker(int key)
	{
		return markers.get(key);
		
	}
	
}
