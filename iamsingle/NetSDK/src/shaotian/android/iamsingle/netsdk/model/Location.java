/*
 * encapsulates a list of location informations
 * 
 * */

package shaotian.android.iamsingle.netsdk.model;

import java.util.Date;

public class Location {
	public int userid;
	public double latitude,longtitude;
	public String uname;
	public Date lastupdate;
	
	public Location(int uid,String uname,double lat, double lon)
	{
		
		this.userid=uid;
		this.uname=uname;
		this.latitude=lat;
		this.longtitude=lon;
		
	}
	
	public Location(int uid,double lat, double lon)
	{
		this.userid=uid;
		this.latitude=lat;
		this.longtitude=lon;		
	}
	
	public static Location getFromServerRetStr(String str) throws Exception
	{//expect input:  userid_altitude_latitude_longtitude 
		
		
			String []arr=str.split("_");
			int uid=Integer.valueOf(arr[0]);
			String alt=arr[1];
			double lat=Double.valueOf(arr[2]);
			double lon=Double.valueOf(arr[3]);
			return new Location(uid,alt,lat,lon);
		
		
		
	}
}
