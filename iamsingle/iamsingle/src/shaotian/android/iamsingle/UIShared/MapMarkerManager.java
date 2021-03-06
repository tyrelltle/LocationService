package shaotian.android.iamsingle.UIShared;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import shaotian.android.iamsingle.netsdk.util.Helpers;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapMarkerManager<T extends TimedMarker> {

	static private MapMarkerManager mInstance=null;
	
	
	private static enum STATE{
		START,	//start() -> flip keep and del list pointers
		ITERATE	//keep calling addMarker() updateMarker() to add markers. 
				//call finish()
		
	}
	private HashMap<Integer, T> a=new HashMap<Integer, T>();
	private HashMap<Integer, T> b=new HashMap<Integer, T>();
	private HashMap<Integer, T> keeplist=a;
	private HashMap<Integer, T> dellist=b;
	private STATE state=STATE.START;
	private GoogleMap map=null;
	private Class T_Class;
	

	public MapMarkerManager(Class timedMarkerClass) {
		this.T_Class=timedMarkerClass;
	}
	public static <T extends TimedMarker>MapMarkerManager Initialize(Class TimedMarkerClass)
	{
		if(mInstance==null)
			mInstance=new MapMarkerManager<T>(TimedMarkerClass);
		return mInstance;
		
	}
	
	public static <T extends TimedMarker>MapMarkerManager Instance()
	{
		
		return mInstance;
		
	}
	public int size()
	{return keeplist==null?0:keeplist.size();}
	
	public boolean containsWithoutState(int key) throws SharedUtil.MapMarkerInvalidStateException
	{//this contains is not state sensitive. 
		
		return keeplist.containsKey(key);
	}
//-------------------------below are state sensitive methods-----------
	public boolean contains(int key) throws SharedUtil.MapMarkerInvalidStateException
	{
		if(state!=STATE.ITERATE)
			throw new SharedUtil.MapMarkerInvalidStateException("invalid mapMarker manager state: contains() only allow state of STATE.ITERATE");
		return dellist.containsKey(key);
	}
	
	public void start() throws SharedUtil.MapMarkerInvalidStateException
			
	{
		if(state!=STATE.START)
			throw new SharedUtil.MapMarkerInvalidStateException("invalid mapMarker manager state: start() only allow state of STATE.START");
		
		HashMap<Integer, T> tmp=dellist;
		dellist=keeplist;
		keeplist=tmp;
		
		state=STATE.ITERATE;
	
	}
	
	

	
	public void addMarker(int key,Marker m) throws SharedUtil.MapMarkerInvalidStateException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException	
	{
		if(state!=STATE.ITERATE)
			throw new SharedUtil.MapMarkerInvalidStateException("invalid mapMarker manager state: addMarker() only allow state of STATE.ITERATE");
		
		Constructor ctor=Helpers.getClassConstructor(this.T_Class, new Class[]{Marker.class});
		keeplist.put(key, (T)ctor.newInstance(m));
		

				
	}
	
	public void updateMarker(int key, LatLng loc) throws SharedUtil.MapMarkerInvalidStateException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		if(state!=STATE.ITERATE)
			throw new SharedUtil.MapMarkerInvalidStateException("invalid mapMarker manager state: updateMarker() only allow state of STATE.ITERATE");
		T m=dellist.remove(key);
		
		Marker mkr=m.getMarker();
		if(m!=null && mkr!=null&& mkr.getPosition().equals(loc))
		{
			mkr.setPosition(loc);
			
		}
		m.clearTime();
		keeplist.put(key, m);
	}
	

	public void finish() throws SharedUtil.MapMarkerInvalidStateException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{//in dellist, update time stamp.  if exeeds predefined max idel time, delete!
		if(state!=STATE.ITERATE)
			throw new SharedUtil.MapMarkerInvalidStateException("invalid mapMarker manager state: finish() only allow state of STATE.ITERATE");

		for(int i :dellist.keySet())
		{
			T tmp=dellist.get(i);
			
			if(tmp.timeout()){
				if(tmp.getMarker()!=null)
				{	tmp.getMarker().remove();
					Log.d("****[deleting unvisited marker]", tmp.getMarker().getPosition().toString());
				}
			}
			else{
				tmp.startTime();
				keeplist.put(i, tmp);
			}
		}
		dellist.clear();
		state=STATE.START;
	}
	
	
//---------------end state sensitive methods	
	
	public int getUidByMarker(Marker m)
	{
		for(int i : this.a.keySet())
		{
			if(a.get(i).isSame(m))
				return i;
		}
		for(int j : this.b.keySet())
		{
			if(b.get(j).isSame(m))
				return j;
		}
		return -1;
	}

	
	public T getMarkerByUid(int uid)
	{
		T ret=null;
		if((ret=keeplist.get(uid))==null)
		{
			ret=dellist.get(uid);
		}
		return ret;
	}
	
	
}
