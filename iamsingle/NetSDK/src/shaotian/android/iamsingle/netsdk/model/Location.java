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
	public String gender;
	public Date lastupdate;
	
	public Location(int uid,String gender,String uname,double lat, double lon)
	{
		
		this.userid=uid;
		this.gender=gender;
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
			String gender=String.valueOf(arr[1]);
			String uname=arr[2];
			double lat=Double.valueOf(arr[3]);
			double lon=Double.valueOf(arr[4]);
			return new Location(uid,gender,uname,lat,lon);
		
		
		
	}
}
