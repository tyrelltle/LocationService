/*
 * encapsulates a list of location informations
 * 
 * */

package shaotian.android.iamsingle.netsdk.model;

import java.util.Date;

public class Location {
	public int userid;
	public double altitude,latitude,longtitude;
	public Date lastupdate;
	
	public Location(int uid,double alt,double lat, double lon)
	{
		
		this.userid=uid;
		this.altitude=alt;
		this.latitude=lat;
		this.longtitude=lon;
		
	}
	
	public Location(double alt,double lat, double lon)
	{
		this.altitude=alt;
		this.latitude=lat;
		this.longtitude=lon;		
	}
	
	public static Location getFromServerRetStr(String str) throws Exception
	{//expect input:  userid_altitude_latitude_longtitude 
		
		try{
			String []arr=str.split("_");
			int uid=Integer.valueOf(arr[0]);
			double alt=Double.valueOf(arr[1]);
			double lat=Double.valueOf(arr[2]);
			double lon=Double.valueOf(arr[3]);
			return new Location(uid,alt,lat,lon);
		}catch(Exception e)
		{
			
			throw new Exception("input to getFromServerRetStr is invalid. Valid formate: userid_altitude_latitude_longtitude");
			
		}
		
		
	}
}