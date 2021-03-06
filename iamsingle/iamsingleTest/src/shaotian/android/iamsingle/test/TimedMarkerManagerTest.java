package shaotian.android.iamsingle.test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import shaotian.android.iamsingle.UIShared.MapMarkerManager;
import shaotian.android.iamsingle.UIShared.SharedUtil.MapMarkerInvalidStateException;
import shaotian.android.iamsingle.UIShared.TimedMarker;

import shaotian.android.iamsingle.mocks.TimedMarkerStub;
import shaotian.android.iamsingle.netsdk.model.Location;
import shaotian.android.iamsingle.netsdk.util.LocationList;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class TimedMarkerManagerTest  extends TestCase{

	private LocationList loclis;
	

	

	
	
	public void init()
	{
		this.loclis=new LocationList();
		for(int i=0;i<4;i++)
		{
			loclis.lis.add(new Location(i, i, i));
			
		}
	}
	
	
	
	public void testMarkerMgrStatesFail_Test() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
		MapMarkerManager mgn=MapMarkerManager.Initialize(TimedMarkerStub.class);
		boolean haserror=false;
		try {
			mgn.finish();
			mgn.start();

		} catch (shaotian.android.iamsingle.UIShared.SharedUtil.MapMarkerInvalidStateException e) {
			haserror=true;
		}
		assertTrue(haserror);
		
		
		
		haserror=false;
		try {
			
			mgn.updateMarker(0, null);

		} catch (shaotian.android.iamsingle.UIShared.SharedUtil.MapMarkerInvalidStateException e) {
			haserror=true;
		}
		assertTrue(haserror);
		
		haserror=false;
		
			
		try {
			mgn.addMarker(0, null);
		} catch (shaotian.android.iamsingle.UIShared.SharedUtil.MapMarkerInvalidStateException e) {
			// TODO Auto-generated catch block
			haserror=true;
		}

		assertTrue(haserror);
		
	}
	
	
	
	
	
	
	
	/*
	 * initialize: put 4 markers into mgr
	 * test: update 2 markers. see iff other  markers still exist
	 */

	public void testExpiredMarkerDeletion() throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, MapMarkerInvalidStateException {
		boolean FirstTimeAddMarkerSuccess=true;
		boolean SecondupdateMarkerSuccess=true;
		
			init();
			
			
			//add marker list
			MapMarkerManager<TimedMarkerStub> mgn=MapMarkerManager.Initialize(TimedMarkerStub.class);
			assertTrue(mgn.size()==0);
			mgn.start();
			for(int i=0;i<loclis.size();i++)
			{
				shaotian.android.iamsingle.netsdk.model.Location loc=loclis.lis.get(i);
				
			    int uid=loc.userid;
			    Marker m=null;
			    
			    if(!mgn.contains(uid))
				{	
			    	mgn.addMarker(uid, null);
			    	
				}else{FirstTimeAddMarkerSuccess=false;}

			}
			mgn.finish();
			
			assertTrue(FirstTimeAddMarkerSuccess);
			//makesure all markers are updated
			assertTrue(mgn.size()==loclis.size());
			
			//update marker list
			
			loclis.lis.remove(0);
			loclis.lis.remove(0);
			int tmp=2;
			while(tmp-->0)
			{	mgn.start();
				for(int i=0;i<loclis.size();i++)
				{
					shaotian.android.iamsingle.netsdk.model.Location loc=loclis.lis.get(i);
					
				    int uid=loc.userid;
				    Marker m=null;
				    
				    if(mgn.contains(uid))
					{	
				    	LatLng newlatlng=new LatLng(loc.latitude, loc.longtitude);
				    	mgn.updateMarker(uid, newlatlng);
				    	
					}
				  
				}
				mgn.finish();
			}
			
			//makesure exipred markers are deleted
			assertTrue(mgn.containsWithoutState(2)&&mgn.containsWithoutState(3));
			assertEquals(true,!mgn.containsWithoutState(0));
			assertEquals(true,!mgn.containsWithoutState(1));
			assertEquals(mgn.size(),loclis.size());

	}

}
