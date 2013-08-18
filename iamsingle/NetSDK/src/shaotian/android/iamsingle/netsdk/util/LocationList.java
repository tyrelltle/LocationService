package shaotian.android.iamsingle.netsdk.util;

import java.util.ArrayList;
import shaotian.android.iamsingle.netsdk.model.*;


public class LocationList implements INetParam{

	public ArrayList<Location> lis=new ArrayList<Location>();
	
	public LocationList ()
	{
	
	}
	
	//add new loc from server returned string
	public void add(String str) throws Exception
	{
		lis.add(Location.getFromServerRetStr(str));
		
	}
	
	public int size()
	{
		return lis.size();
		
	}
}
