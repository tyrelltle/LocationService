package shaotian.android.iamsingle.UIShared;


import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapMarkerManager {

	static private MapMarkerManager mInstance=null;
	
	
	private static enum STATE{
		START,	//start() -> flip keep and del list pointers
		ITERATE	//keep calling addMarker() updateMarker() to add markers. 
				//call finish()
		
	}
	private HashMap<Integer, TimedMarker> a=new HashMap<Integer, TimedMarker>();
	private HashMap<Integer, TimedMarker> b=new HashMap<Integer, TimedMarker>();
	private HashMap<Integer, TimedMarker> keeplist=a;
	private HashMap<Integer, TimedMarker> dellist=b;
	private STATE state=STATE.START;
	private GoogleMap map=null;
	
	
	public static MapMarkerManager Instance()
	{
		if(mInstance==null)
			mInstance=new MapMarkerManager();
		return mInstance;
		
	}
	
//-------------------------below are state sensitive methods-----------
	public void start() throws MapMarkerInvalidStateException
			
	{
		if(state!=STATE.START)
			throw new MapMarkerInvalidStateException("invalid mapMarker manager state: start() only allow state of STATE.START");
		
		HashMap<Integer, TimedMarker> tmp=dellist;
		dellist=keeplist;
		keeplist=tmp;
		
		state=STATE.ITERATE;
	
	}
	
	
	public boolean contains(int key) throws MapMarkerInvalidStateException
	{
		if(state!=STATE.ITERATE)
			throw new MapMarkerInvalidStateException("invalid mapMarker manager state: contains() only allow state of STATE.ITERATE");
		return dellist.containsKey(key);
	}
	
	public void addMarker(int key,Marker m) throws MapMarkerInvalidStateException	
	{
		if(state!=STATE.ITERATE)
			throw new MapMarkerInvalidStateException("invalid mapMarker manager state: addMarker() only allow state of STATE.ITERATE");
		
		keeplist.put(key, new TimedMarker(m));
		

				
	}
	
	public void updateMarker(int key, LatLng loc) throws MapMarkerInvalidStateException
	{
		if(state!=STATE.ITERATE)
			throw new MapMarkerInvalidStateException("invalid mapMarker manager state: updateMarker() only allow state of STATE.ITERATE");
		TimedMarker m=dellist.remove(key);
		
		Marker mkr=m.getMarker();
		if(m!=null && !mkr.getPosition().equals(loc))
		{
			mkr.setPosition(loc);
			
		}
		m.clearTime();
		keeplist.put(key, m);
	}
	

	public void finish() throws MapMarkerInvalidStateException
	{//in dellist, update time stamp.  if exeeds predefined max idel time, delete!
		if(state!=STATE.ITERATE)
			throw new MapMarkerInvalidStateException("invalid mapMarker manager state: finish() only allow state of STATE.ITERATE");

		for(int i :dellist.keySet())
		{
			TimedMarker tmp=dellist.get(i);
			tmp.startTime();
			if(tmp.timeout()){
				tmp.getMarker().remove();
				Log.d("****[deleting unvisited marker]", tmp.getMarker().getPosition().toString());
			}
			else{
				keeplist.put(i, tmp);
			}
		}
		dellist.clear();
		state=STATE.START;
	}
	
	
//---------------end state sensitive methods	
	
	

	public class MapMarkerInvalidStateException extends Exception
	{
		private static final long serialVersionUID = 1L;
		
		public MapMarkerInvalidStateException(String message)
		{
			super(message);
		}
	}
	
	public class TimedMarker
	{
		public static final int MAX_IDLE_TIME=5000;
		private Marker marker;
		private Timestamp  time=null;
		
		public TimedMarker(Marker m)
		{
			marker=m;
			
		}
		

		
		public void startTime()
		{
			if(time==null)
				time=new Timestamp(new Date().getTime());
		}
		
		public void clearTime()
		{
			time=null;
		}
		public Marker getMarker()
		{
			return marker;
		}
		
		

		public boolean timeout()
		{
			if(time==null)
				return false;
			return (new Date().getTime())-time.getTime()>=this.MAX_IDLE_TIME;
		}
	}
}
